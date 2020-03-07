
public final class koshiangen {
   public static void main(final String... args) {
      final var koshianFunctions = generate(args);
      System.out.println(koshianFunctions);
   }

   private static String generate(final String[] views) {
      final var builder = new StringBuilder();
      builder.append(generateImports());
      builder.append('\n');

      for (int i = 0; i < views.length; i++) {
         builder.append(generateKoshianViewGroupBuilderFunctions(views[i]));

         if (i == views.length - 2) {
            builder.append("\n// =====================================================================================================================\n\n");
         }
      }

      return builder.toString();
   }

   private static String generateImports() {
      return
         "import android.content.Context\n" +
         "import android.view.ViewGroup\n" +
         "import kotlin.contracts.*\n";
   }

   private static String generateKoshianViewGroupBuilderFunctions(final String view) {
      final var viewConstructor = view + "Constructor";
      final var layoutParams = view + ".LayoutParams";

      return
         "object "+viewConstructor+" : KoshianViewGroupConstructor<"+view+", "+layoutParams+"> {\n" +
         "   override fun instantiate(context: Context?) = "+view+"(context)\n" +
         "   override fun instantiateLayoutParams() = "+layoutParams+"(WRAP_CONTENT, WRAP_CONTENT)\n" +
         "}\n" +
         "\n" +
         "/**\n" +
         " * adds Views into this "+view+".\n" +
         " */\n" +
         "@ExperimentalContracts\n" +
         "inline fun <R> "+view+".addView(\n" +
         "      buildAction: ViewGroupBuilder<"+view+", Nothing, "+layoutParams+", KoshianMode.Creator>.() -> R\n" +
         "): R {\n" +
         "   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }\n" +
         "   return addView("+viewConstructor+", buildAction)\n" +
         "}\n" +
         "\n" +
         "/**\n" +
         " * creates a new "+view+" and adds it into this ViewGroup.\n" +
         " */\n" +
         "@ExperimentalContracts\n" +
         "@Suppress(\"FunctionName\")\n" +
         "inline fun <L> KoshianParent<L, KoshianMode.Creator>."+view+"(\n" +
         "      buildAction: ViewGroupBuilder<"+view+", L, "+layoutParams+", KoshianMode.Creator>.() -> Unit\n" +
         "): "+view+" {\n" +
         "   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }\n" +
         "   return create("+viewConstructor+", buildAction)\n" +
         "}\n" +
         "\n" +
         "/**\n" +
         " * finds Views that are already added in this "+view+",\n" +
         " * and applies Koshian DSL to them.\n" +
         " *\n" +
         " * ![](https://raw.github.com/wcaokaze/Koshian/master/imgs/applier.svg?sanitize=true)\n" +
         " *\n" +
         " * The following 2 snippets are equivalent.\n" +
         " *\n" +
         " * 1.\n" +
         " *     ```kotlin\n" +
         " *     val contentView = koshian(context) {\n" +
         " *        LinearLayout {\n" +
         " *           TextView {\n" +
         " *              view.text = \"hello\"\n" +
         " *              view.textColor = 0xffffff opacity 0.8\n" +
         " *           }\n" +
         " *        }\n" +
         " *     }\n" +
         " *     ```\n" +
         " *\n" +
         " * 2.\n" +
         " *     ```kotlin\n" +
         " *     val contentView = koshian(context) {\n" +
         " *        LinearLayout {\n" +
         " *           TextView {\n" +
         " *              view.text = \"hello\"\n" +
         " *           }\n" +
         " *        }\n" +
         " *     }\n" +
         " *\n" +
         " *     contentView.applyKoshian {\n" +
         " *        TextView {\n" +
         " *           view.textColor = 0xffffff opacity 0.8\n" +
         " *        }\n" +
         " *     }\n" +
         " *     ```\n" +
         " *\n" +
         " * When mismatched View is specified, Koshian creates a new View and inserts it.\n" +
         " *\n" +
         " * ![](https://raw.github.com/wcaokaze/Koshian/master/imgs/applier_insertion.svg?sanitize=true)\n" +
         " */\n" +
         "inline fun "+view+".applyKoshian(\n" +
         "      applyAction: ViewGroupBuilder<"+view+", ViewGroup.LayoutParams, "+layoutParams+", KoshianMode.Applier>.() -> Unit\n" +
         ") {\n" +
         "   applyKoshian("+viewConstructor+", applyAction)\n" +
         "}\n" +
         "\n" +
         "/**\n" +
         " * If the next View is a "+view+", applies Koshian to it.\n" +
         " *\n" +
         " * Otherwise, creates a new "+view+" and inserts it to the current position.\n" +
         " *\n" +
         " * @see applyKoshian\n" +
         " */\n" +
         "@Suppress(\"FunctionName\")\n" +
         "inline fun <L> KoshianParent<L, KoshianMode.Applier>."+view+"(\n" +
         "      buildAction: ViewGroupBuilder<"+view+", L, "+layoutParams+", KoshianMode.Applier>.() -> Unit\n" +
         ") {\n" +
         "   apply("+viewConstructor+", buildAction)\n" +
         "}\n";
   }
}

