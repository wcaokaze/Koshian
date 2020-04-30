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
import android.widget.ViewSwitcher
import kotlin.contracts.*

object ViewSwitcherConstructor : KoshianViewGroupConstructor<ViewSwitcher, FrameLayout.LayoutParams> {
   override fun instantiate(context: Context?) = ViewSwitcher(context)
   override fun instantiateLayoutParams() = FrameLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
}

/**
 * adds Views into this ViewSwitcher.
 */
@KoshianMarker
@ExperimentalContracts
inline fun <R> ViewSwitcher.addView(
      creatorAction: ViewGroupCreator<ViewSwitcher, Nothing, FrameLayout.LayoutParams>.() -> R
): R {
   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }
   return addView(ViewSwitcherConstructor, creatorAction)
}

/**
 * creates a new ViewSwitcher and adds it into this ViewGroup.
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> CreatorParent<L>.ViewSwitcher(
      creatorAction: ViewGroupCreator<ViewSwitcher, L, FrameLayout.LayoutParams>.() -> Unit
): ViewSwitcher {
   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }
   return create(ViewSwitcherConstructor, creatorAction)
}

/**
 * creates a new ViewSwitcher with name, and adds it into this ViewGroup.
 *
 * The name can be referenced in [applyKoshian]
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> CreatorParent<L>.ViewSwitcher(
      name: String,
      creatorAction: ViewGroupCreator<ViewSwitcher, L, FrameLayout.LayoutParams>.() -> Unit
): ViewSwitcher {
   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }
   return create(name, ViewSwitcherConstructor, creatorAction)
}

/**
 * finds Views that are already added in this ViewSwitcher,
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
@KoshianMarker
inline fun ViewSwitcher.applyKoshian(
      applierAction: ViewGroupApplier<ViewSwitcher, ViewGroup.LayoutParams, FrameLayout.LayoutParams, Nothing>.() -> Unit
) {
   applyKoshian(ViewSwitcherConstructor, applierAction)
}

/**
 * finds Views that are already added in this ViewSwitcher,
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
@KoshianMarker
inline fun <S : KoshianStyle> ViewSwitcher.applyKoshian(
      style: S,
      applierAction: ViewGroupApplier<ViewSwitcher, ViewGroup.LayoutParams, FrameLayout.LayoutParams, S>.() -> Unit
) {
   applyKoshian(style, ViewSwitcherConstructor, applierAction)
}

/**
 * If the next View is a ViewSwitcher, applies Koshian to it.
 *
 * Otherwise, creates a new ViewSwitcher and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L, S : KoshianStyle>
      ApplierParent<L, S>.ViewSwitcher(
            applierAction: ViewGroupApplier<ViewSwitcher, L, FrameLayout.LayoutParams, S>.() -> Unit
      )
{
   apply(ViewSwitcherConstructor, applierAction)
}

/**
 * If the next View is a ViewSwitcher, applies Koshian to it.
 *
 * Otherwise, creates a new ViewSwitcher and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L, S : KoshianStyle>
      ApplierParent<L, S>.ViewSwitcher(
            styleElement: KoshianStyle.StyleElement<ViewSwitcher>,
            applierAction: ViewGroupApplier<ViewSwitcher, L, FrameLayout.LayoutParams, S>.() -> Unit
      )
{
   apply(ViewSwitcherConstructor, styleElement, applierAction)
}

/**
 * Applies Koshian to all ViewSwitchers that are named the specified in this ViewGroup.
 * If there are no ViewSwitchers named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L, S : KoshianStyle>
      ApplierParent<L, S>.ViewSwitcher(
            name: String,
            applierAction: ViewGroupApplier<ViewSwitcher, L, FrameLayout.LayoutParams, S>.() -> Unit
      )
{
   apply(name, ViewSwitcherConstructor, applierAction)
}

/**
 * Applies Koshian to all ViewSwitchers that are named the specified in this ViewGroup.
 * If there are no ViewSwitchers named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L, S : KoshianStyle>
      ApplierParent<L, S>.ViewSwitcher(
            name: String,
            styleElement: KoshianStyle.StyleElement<ViewSwitcher>,
            applierAction: ViewGroupApplier<ViewSwitcher, L, FrameLayout.LayoutParams, S>.() -> Unit
      )
{
   apply(name, ViewSwitcherConstructor, styleElement, applierAction)
}

/**
 * registers a style applier function into this [KoshianStyle].
 *
 * Styles can be applied via [applyKoshian]
 */
@Suppress("FunctionName")
inline fun KoshianStyle.ViewSwitcher(
      crossinline styleAction: ViewStyle<ViewSwitcher>.() -> Unit
): KoshianStyle.StyleElement<ViewSwitcher> {
   return createStyleElement(styleAction)
}
