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

@file:Suppress("UNUSED")
package koshian

import android.content.Context
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextSwitcher
import kotlin.contracts.*

object TextSwitcherConstructor : KoshianViewGroupConstructor<TextSwitcher, FrameLayout.LayoutParams> {
   override fun instantiate(context: Context?) = TextSwitcher(context)
   override fun instantiateLayoutParams() = FrameLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
}

/**
 * adds Views into this TextSwitcher.
 */
@ExperimentalContracts
inline fun <R> TextSwitcher.addView(
      creatorAction: ViewGroupCreator<TextSwitcher, Nothing, FrameLayout.LayoutParams>.() -> R
): R {
   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }
   return addView(TextSwitcherConstructor, creatorAction)
}

/**
 * creates a new TextSwitcher and adds it into this ViewGroup.
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> CreatorParent<L>.TextSwitcher(
      creatorAction: ViewGroupCreator<TextSwitcher, L, FrameLayout.LayoutParams>.() -> Unit
): TextSwitcher {
   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }
   return create(TextSwitcherConstructor, creatorAction)
}

/**
 * creates a new TextSwitcher with name, and adds it into this ViewGroup.
 *
 * The name can be referenced in [applyKoshian]
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> CreatorParent<L>.TextSwitcher(
      name: String,
      creatorAction: ViewGroupCreator<TextSwitcher, L, FrameLayout.LayoutParams>.() -> Unit
): TextSwitcher {
   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }
   return create(name, TextSwitcherConstructor, creatorAction)
}

/**
 * finds Views that are already added in this TextSwitcher,
 * and applies Koshian DSL to them.
 *
 * ![](https://raw.github.com/wcaokaze/Koshian/master/imgs/applier.svg?sanitize=true)
 *
 * The following 2 snippets are equivalent.
 *
 * 1.
 *     ```kotlin
 *     val contentView = koshian(context) {
 *        LinearLayout {
 *           TextView {
 *              view.text = "hello"
 *              view.textColor = 0xffffff opacity 0.8
 *           }
 *        }
 *     }
 *     ```
 *
 * 2.
 *     ```kotlin
 *     val contentView = koshian(context) {
 *        LinearLayout {
 *           TextView {
 *              view.text = "hello"
 *           }
 *        }
 *     }
 *
 *     contentView.applyKoshian {
 *        TextView {
 *           view.textColor = 0xffffff opacity 0.8
 *        }
 *     }
 *     ```
 *
 * When mismatched View is specified, Koshian creates a new View and inserts it.
 *
 * ![](https://raw.github.com/wcaokaze/Koshian/master/imgs/applier_insertion.svg?sanitize=true)
 *
 * Also, naming View is a good way.
 *
 * ![](https://raw.github.com/wcaokaze/Koshian/master/imgs/applier_named.svg?sanitize=true)
 *
 * Koshian specifying a name doesn't affect the cursor.
 * Koshian not specifying a name ignores named Views.
 * Named Views and non-named Views are simply in other worlds.
 *
 * ![](https://raw.github.com/wcaokaze/Koshian/master/imgs/applier_mixing_named_and_non_named.svg?sanitize=true)
 *
 * For readability, it is recommended to put named Views
 * as synchronized with the cursor.
 *
 * ![](https://raw.github.com/wcaokaze/Koshian/master/imgs/applier_readable_mixing.svg?sanitize=true)
 */
inline fun TextSwitcher.applyKoshian(
      applierAction: ViewGroupApplier<TextSwitcher, ViewGroup.LayoutParams, FrameLayout.LayoutParams, Nothing>.() -> Unit
) {
   applyKoshian(TextSwitcherConstructor, applierAction)
}

/**
 * finds Views that are already added in this TextSwitcher,
 * and applies Koshian DSL to them.
 *
 * ![](https://raw.github.com/wcaokaze/Koshian/master/imgs/applier.svg?sanitize=true)
 *
 * The following 2 snippets are equivalent.
 *
 * 1.
 *     ```kotlin
 *     val contentView = koshian(context) {
 *        LinearLayout {
 *           TextView {
 *              view.text = "hello"
 *              view.textColor = 0xffffff opacity 0.8
 *           }
 *        }
 *     }
 *     ```
 *
 * 2.
 *     ```kotlin
 *     val contentView = koshian(context) {
 *        LinearLayout {
 *           TextView {
 *              view.text = "hello"
 *           }
 *        }
 *     }
 *
 *     contentView.applyKoshian {
 *        TextView {
 *           view.textColor = 0xffffff opacity 0.8
 *        }
 *     }
 *     ```
 *
 * When mismatched View is specified, Koshian creates a new View and inserts it.
 *
 * ![](https://raw.github.com/wcaokaze/Koshian/master/imgs/applier_insertion.svg?sanitize=true)
 *
 * Also, naming View is a good way.
 *
 * ![](https://raw.github.com/wcaokaze/Koshian/master/imgs/applier_named.svg?sanitize=true)
 *
 * Koshian specifying a name doesn't affect the cursor.
 * Koshian not specifying a name ignores named Views.
 * Named Views and non-named Views are simply in other worlds.
 *
 * ![](https://raw.github.com/wcaokaze/Koshian/master/imgs/applier_mixing_named_and_non_named.svg?sanitize=true)
 *
 * For readability, it is recommended to put named Views
 * as synchronized with the cursor.
 *
 * ![](https://raw.github.com/wcaokaze/Koshian/master/imgs/applier_readable_mixing.svg?sanitize=true)
 */
inline fun <S : KoshianStyle> TextSwitcher.applyKoshian(
      style: S,
      applierAction: ViewGroupApplier<TextSwitcher, ViewGroup.LayoutParams, FrameLayout.LayoutParams, S>.() -> Unit
) {
   applyKoshian(style, TextSwitcherConstructor, applierAction)
}

/**
 * If the next View is a TextSwitcher, applies Koshian to it.
 *
 * Otherwise, creates a new TextSwitcher and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L, S : KoshianStyle>
      ApplierParent<L, S>.TextSwitcher(
            applierAction: ViewGroupApplier<TextSwitcher, L, FrameLayout.LayoutParams, S>.() -> Unit
      )
{
   apply(TextSwitcherConstructor, applierAction)
}

/**
 * If the next View is a TextSwitcher, applies Koshian to it.
 *
 * Otherwise, creates a new TextSwitcher and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L, S : KoshianStyle>
      ApplierParent<L, S>.TextSwitcher(
            styleElement: KoshianStyle.StyleElement<TextSwitcher>,
            applierAction: ViewGroupApplier<TextSwitcher, L, FrameLayout.LayoutParams, S>.() -> Unit
      )
{
   apply(TextSwitcherConstructor, styleElement, applierAction)
}

/**
 * Applies Koshian to all TextSwitchers that are named the specified in this ViewGroup.
 * If there are no TextSwitchers named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L, S : KoshianStyle>
      ApplierParent<L, S>.TextSwitcher(
            name: String,
            applierAction: ViewGroupApplier<TextSwitcher, L, FrameLayout.LayoutParams, S>.() -> Unit
      )
{
   apply(name, TextSwitcherConstructor, applierAction)
}

/**
 * Applies Koshian to all TextSwitchers that are named the specified in this ViewGroup.
 * If there are no TextSwitchers named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L, S : KoshianStyle>
      ApplierParent<L, S>.TextSwitcher(
            name: String,
            styleElement: KoshianStyle.StyleElement<TextSwitcher>,
            applierAction: ViewGroupApplier<TextSwitcher, L, FrameLayout.LayoutParams, S>.() -> Unit
      )
{
   apply(name, TextSwitcherConstructor, styleElement, applierAction)
}

/**
 * registers a style applier function into this [KoshianStyle].
 *
 * Styles can be applied via [applyKoshian]
 */
@Suppress("FunctionName")
inline fun KoshianStyle.TextSwitcher(
      crossinline styleAction: ViewStyle<TextSwitcher>.() -> Unit
): KoshianStyle.StyleElement<TextSwitcher> {
   return createStyleElement(styleAction)
}
