/*
 * Copyright 2020 wcaokaze
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class koshiangen {
   private static final int OPTION_VIEW       = 0b001;
   private static final int OPTION_VIEW_GROUP = 0b010;
   private static final int OPTION_HELP       = 0b100;

   public static void main(final String... args) {
      try {
         final var options = parseOptions(args);

         final var views = Arrays.stream(args)
               .filter(s -> !s.startsWith("-"))
               .collect(Collectors.toList());

         if ((options & OPTION_HELP) != 0) {
            printUsage();
            return;
         }

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

   private static void printUsage() {
      System.out.println(
            "Usage: java koshiangen [options] viewname\n" +
            "Options:\n" +
            "    -v  generate Koshian functions for a View\n" +
            "    -vg generate Koshian functions for a ViewGroup\n" +
            "    -h  show this message");
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
                  case "h":
                  case "help":
                  case "-help":
                     return Stream.of(OPTION_HELP);
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
            "@file:Suppress(\"UNUSED\")\n" +
            "\n" +
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
            "@file:Suppress(\"UNUSED\")\n" +
            "\n" +
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
            "inline fun <L> CreatorParent<L>."+view+"(\n" +
            "      creatorAction: ViewCreator<"+view+", L>.() -> Unit\n" +
            "): "+view+" {\n" +
            "   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }\n" +
            "   return create("+viewConstructor+", creatorAction)\n" +
            "}\n" +
            "\n" +
            "/**\n" +
            " * creates a new "+view+" with name, and adds it into this ViewGroup.\n" +
            " *\n" +
            " * The name can be referenced in [applyKoshian]\n" +
            " */\n" +
            "@ExperimentalContracts\n" +
            "@Suppress(\"FunctionName\")\n" +
            "inline fun <L> CreatorParent<L>."+view+"(\n" +
            "      name: String,\n" +
            "      creatorAction: ViewCreator<"+view+", L>.() -> Unit\n" +
            "): "+view+" {\n" +
            "   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }\n" +
            "   return create(name, "+viewConstructor+", creatorAction)\n" +
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
            "inline fun <L, S : KoshianStyle>\n" +
            "      ApplierParent<L, S>."+view+"(\n" +
            "            applierAction: ViewApplier<"+view+", L, S>.() -> Unit\n" +
            "      )\n" +
            "{\n" +
            "   apply("+viewConstructor+", applierAction)\n" +
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
            "inline fun <L, S : KoshianStyle>\n" +
            "      ApplierParent<L, S>."+view+"(\n" +
            "            styleElement: KoshianStyle.StyleElement<"+view+">,\n" +
            "            applierAction: ViewApplier<"+view+", L, S>.() -> Unit\n" +
            "      )\n" +
            "{\n" +
            "   apply("+viewConstructor+", styleElement, applierAction)\n" +
            "}\n" +
            "\n" +
            "/**\n" +
            " * Applies Koshian to all "+view+"s that are named the specified in this ViewGroup.\n" +
            " * If there are no "+view+"s named the specified, do nothing.\n" +
            " *\n" +
            " * @see applyKoshian\n" +
            " */\n" +
            "@Suppress(\"FunctionName\")\n" +
            "inline fun <L, S : KoshianStyle>\n" +
            "      ApplierParent<L, S>."+view+"(\n" +
            "            name: String,\n" +
            "            applierAction: ViewApplier<"+view+", L, S>.() -> Unit\n" +
            "      )\n" +
            "{\n" +
            "   apply(name, applierAction)\n" +
            "}\n" +
            "\n" +
            "/**\n" +
            " * Applies Koshian to all "+view+"s that are named the specified in this ViewGroup.\n" +
            " * If there are no "+view+"s named the specified, do nothing.\n" +
            " *\n" +
            " * @see applyKoshian\n" +
            " */\n" +
            "@Suppress(\"FunctionName\")\n" +
            "inline fun <L, S : KoshianStyle>\n" +
            "      ApplierParent<L, S>."+view+"(\n" +
            "            name: String,\n" +
            "            styleElement: KoshianStyle.StyleElement<"+view+">,\n" +
            "            applierAction: ViewApplier<"+view+", L, S>.() -> Unit\n" +
            "      )\n" +
            "{\n" +
            "   apply(name, styleElement, applierAction)\n" +
            "}\n" +
            "\n" +
            "/**\n" +
            " * registers a style applier function into this [KoshianStyle].\n" +
            " *\n" +
            " * Styles can be applied via [applyKoshian]\n" +
            " */\n" +
            "@Suppress(\"FunctionName\")\n" +
            "inline fun KoshianStyle."+view+"(\n" +
            "      crossinline styleAction: ViewStyle<"+view+">.() -> Unit\n" +
            "): KoshianStyle.StyleElement<"+view+"> {\n" +
            "   return createStyleElement(styleAction)\n" +
            "}";
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
         "      creatorAction: ViewGroupCreator<"+view+", Nothing, "+layoutParams+">.() -> R\n" +
         "): R {\n" +
         "   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }\n" +
         "   return addView("+viewConstructor+", creatorAction)\n" +
         "}\n" +
         "\n" +
         "/**\n" +
         " * creates a new "+view+" and adds it into this ViewGroup.\n" +
         " */\n" +
         "@ExperimentalContracts\n" +
         "@Suppress(\"FunctionName\")\n" +
         "inline fun <L> CreatorParent<L>."+view+"(\n" +
         "      creatorAction: ViewGroupCreator<"+view+", L, "+layoutParams+">.() -> Unit\n" +
         "): "+view+" {\n" +
         "   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }\n" +
         "   return create("+viewConstructor+", creatorAction)\n" +
         "}\n" +
         "\n" +
         "/**\n" +
         " * creates a new "+view+" with name, and adds it into this ViewGroup.\n" +
         " *\n" +
         " * The name can be referenced in [applyKoshian]\n" +
         " */\n" +
         "@ExperimentalContracts\n" +
         "@Suppress(\"FunctionName\")\n" +
         "inline fun <L> CreatorParent<L>."+view+"(\n" +
         "      name: String,\n" +
         "      creatorAction: ViewGroupCreator<"+view+", L, "+layoutParams+">.() -> Unit\n" +
         "): "+view+" {\n" +
         "   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }\n" +
         "   return create(name, "+viewConstructor+", creatorAction)\n" +
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
         " *\n" +
         " * Also, naming View is a good way.\n" +
         " *\n" +
         " * ![](https://raw.github.com/wcaokaze/Koshian/master/imgs/applier_named.svg?sanitize=true)\n" +
         " *\n" +
         " * Koshian specifying a name doesn't affect the cursor.\n" +
         " * Koshian not specifying a name ignores named Views.\n" +
         " * Named Views and non-named Views are simply in other worlds.\n" +
         " *\n" +
         " * ![](https://raw.github.com/wcaokaze/Koshian/master/imgs/applier_mixing_named_and_non_named.svg?sanitize=true)\n" +
         " *\n" +
         " * For readability, it is recommended to put named Views\n" +
         " * as synchronized with the cursor.\n" +
         " *\n" +
         " * ![](https://raw.github.com/wcaokaze/Koshian/master/imgs/applier_readable_mixing.svg?sanitize=true)\n" +
         " */\n" +
         "inline fun "+view+".applyKoshian(\n" +
         "      applierAction: ViewGroupApplier<"+view+", ViewGroup.LayoutParams, "+layoutParams+", Nothing>.() -> Unit\n" +
         ") {\n" +
         "   applyKoshian("+viewConstructor+", applierAction)\n" +
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
         " *\n" +
         " * Also, naming View is a good way.\n" +
         " *\n" +
         " * ![](https://raw.github.com/wcaokaze/Koshian/master/imgs/applier_named.svg?sanitize=true)\n" +
         " *\n" +
         " * Koshian specifying a name doesn't affect the cursor.\n" +
         " * Koshian not specifying a name ignores named Views.\n" +
         " * Named Views and non-named Views are simply in other worlds.\n" +
         " *\n" +
         " * ![](https://raw.github.com/wcaokaze/Koshian/master/imgs/applier_mixing_named_and_non_named.svg?sanitize=true)\n" +
         " *\n" +
         " * For readability, it is recommended to put named Views\n" +
         " * as synchronized with the cursor.\n" +
         " *\n" +
         " * ![](https://raw.github.com/wcaokaze/Koshian/master/imgs/applier_readable_mixing.svg?sanitize=true)\n" +
         " */\n" +
         "inline fun <S : KoshianStyle> "+view+".applyKoshian(\n" +
         "      style: S,\n" +
         "      applierAction: ViewGroupApplier<"+view+", ViewGroup.LayoutParams, "+layoutParams+", S>.() -> Unit\n" +
         ") {\n" +
         "   applyKoshian(style, "+viewConstructor+", applierAction)\n" +
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
         "inline fun <L, S : KoshianStyle>\n" +
         "      ApplierParent<L, S>."+view+"(\n" +
         "            applierAction: ViewGroupApplier<"+view+", L, "+layoutParams+", S>.() -> Unit\n" +
         "      )\n" +
         "{\n" +
         "   apply("+viewConstructor+", applierAction)\n" +
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
         "inline fun <L, S : KoshianStyle>\n" +
         "      ApplierParent<L, S>."+view+"(\n" +
         "            styleElement: KoshianStyle.StyleElement<"+view+">,\n" +
         "            applierAction: ViewGroupApplier<"+view+", L, "+layoutParams+", S>.() -> Unit\n" +
         "      )\n" +
         "{\n" +
         "   apply("+viewConstructor+", styleElement, applierAction)\n" +
         "}\n" +
         "\n" +
         "/**\n" +
         " * Applies Koshian to all "+view+"s that are named the specified in this ViewGroup.\n" +
         " * If there are no "+view+"s named the specified, do nothing.\n" +
         " *\n" +
         " * @see applyKoshian\n" +
         " */\n" +
         "@Suppress(\"FunctionName\")\n" +
         "inline fun <L, S : KoshianStyle>\n" +
         "      ApplierParent<L, S>."+view+"(\n" +
         "            name: String,\n" +
         "            applierAction: ViewGroupApplier<"+view+", L, "+layoutParams+", S>.() -> Unit\n" +
         "      )\n" +
         "{\n" +
         "   apply(name, "+viewConstructor+", applierAction)\n" +
         "}\n" +
         "\n" +
         "/**\n" +
         " * Applies Koshian to all "+view+"s that are named the specified in this ViewGroup.\n" +
         " * If there are no "+view+"s named the specified, do nothing.\n" +
         " *\n" +
         " * @see applyKoshian\n" +
         " */\n" +
         "@Suppress(\"FunctionName\")\n" +
         "inline fun <L, S : KoshianStyle>\n" +
         "      ApplierParent<L, S>."+view+"(\n" +
         "            name: String,\n" +
         "            styleElement: KoshianStyle.StyleElement<"+view+">,\n" +
         "            applierAction: ViewGroupApplier<"+view+", L, "+layoutParams+", S>.() -> Unit\n" +
         "      )\n" +
         "{\n" +
         "   apply(name, "+viewConstructor+", styleElement, applierAction)\n" +
         "}\n" +
         "\n" +
         "/**\n" +
         " * registers a style applier function into this [KoshianStyle].\n" +
         " *\n" +
         " * Styles can be applied via [applyKoshian]\n" +
         " */\n" +
         "@Suppress(\"FunctionName\")\n" +
         "inline fun KoshianStyle."+view+"(\n" +
         "      crossinline styleAction: ViewStyle<"+view+">.() -> Unit\n" +
         "): KoshianStyle.StyleElement<"+view+"> {\n" +
         "   return createStyleElement(styleAction)\n" +
         "}";
   }
}

