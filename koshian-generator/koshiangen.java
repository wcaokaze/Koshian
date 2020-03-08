import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class koshiangen {
   private static final int OPTION_VIEW       = 0b01;
   private static final int OPTION_VIEW_GROUP = 0b10;

   public static void main(final String... args) {
      try {
         final var options = parseOptions(args);

         final var views = Arrays.stream(args)
               .filter(s -> !s.startsWith("-"))
               .collect(Collectors.toList());

         int functionType = options & (OPTION_VIEW | OPTION_VIEW_GROUP);

         if (functionType != OPTION_VIEW && functionType != OPTION_VIEW_GROUP) {
            functionType = promptViewOrViewGroup();
         }

         final String koshianFunctions;

         if (functionType == OPTION_VIEW) {
            koshianFunctions = generateKoshianViewBuilderFunctions(views);
         } else if (functionType == OPTION_VIEW_GROUP) {
            koshianFunctions = generateKoshianViewGroupBuilderFunctions(views);
         } else {
            return;
         }

         System.out.println(koshianFunctions);
      } catch (final Exception e) {
         System.out.println(e.getMessage());
      }
   }

   private static int parseOptions(final String... args) {
      return Arrays.stream(args)
            .filter(s -> s.startsWith("-"))
            .map(s -> s.substring(1))
            .flatMap(s -> {
               switch (s) {
                  case "v":
                     return Stream.of(OPTION_VIEW);
                  case "vg":
                     return Stream.of(OPTION_VIEW_GROUP);
                  default:
                     throw new IllegalArgumentException("Unknown option: " + s);
               }
            })
            .reduce(0, (a, b) -> a | b);
   }

   private static int promptViewOrViewGroup() {
      System.out.println("Select View or ViewGroup to generate Koshian functions (v/vg):");

      try (final var in = new BufferedReader(new InputStreamReader(System.in))) {
         while (true) {
            final var line = in.readLine();

            if (line == null) { return 0; }

            switch (line) {
               case "v":
                  System.out.println();
                  return OPTION_VIEW;
               case "vg":
                  System.out.println();
                  return OPTION_VIEW_GROUP;
               default:
                  System.out.println("Input \"v\" (View) or \"vg\" (ViewGroup):");
            }
         }
      } catch (final IOException e) {
         return 0;
      }
   }

   private static String generateKoshianViewBuilderFunctions(final List<String> views) {
      final var builder = new StringBuilder();

      builder.append(
            "import android.content.Context\n" +
            "import kotlin.contracts.*\n" +
            "\n");

      for (int i = 0; i < views.size(); i++) {
         builder.append(generateKoshianViewBuilderFunction(views.get(i)));

         if (i == views.size() - 2) {
            builder.append("\n// =====================================================================================================================\n\n");
         }
      }

      return builder.toString();
   }

   private static String generateKoshianViewGroupBuilderFunctions(final List<String> views) {
      final var builder = new StringBuilder();

      builder.append(
            "import android.content.Context\n" +
            "import android.view.ViewGroup\n" +
            "import kotlin.contracts.*\n" +
            "\n");

      for (int i = 0; i < views.size(); i++) {
         builder.append(generateKoshianViewGroupBuilderFunction(views.get(i)));

         if (i == views.size() - 2) {
            builder.append("\n// =====================================================================================================================\n\n");
         }
      }

      return builder.toString();
   }

   private static String generateKoshianViewBuilderFunction(final String view) {
      final var viewConstructor = view + "Constructor";

      return
            "object "+viewConstructor+" : KoshianViewConstructor<"+view+"> {\n" +
            "   override fun instantiate(context: Context?) = "+view+"(context)\n" +
            "}\n" +
            "\n" +
            "/**\n" +
            " * creates a new "+view+" and adds it into this ViewGroup.\n" +
            " */\n" +
            "@ExperimentalContracts\n" +
            "@Suppress(\"FunctionName\")\n" +
            "inline fun <L> KoshianParent<L, KoshianMode.Creator>."+view+"(\n" +
            "      buildAction: ViewBuilder<"+view+", L, KoshianMode.Creator>.() -> Unit\n" +
            "): "+view+" {\n" +
            "   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }\n" +
            "   return create("+viewConstructor+", buildAction)\n" +
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
            "      buildAction: ViewBuilder<"+view+", L, KoshianMode.Applier>.() -> Unit\n" +
            ") {\n" +
            "   apply("+viewConstructor+", buildAction)\n" +
            "}\n";
   }

   private static String generateKoshianViewGroupBuilderFunction(final String view) {
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

