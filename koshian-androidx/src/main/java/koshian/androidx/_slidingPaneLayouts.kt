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
package koshian.androidx

import android.content.Context
import android.view.ViewGroup
import androidx.slidingpanelayout.widget.SlidingPaneLayout
import koshian.*
import kotlin.contracts.*

object SlidingPaneLayoutConstructor : KoshianViewConstructor<SlidingPaneLayout, SlidingPaneLayout.LayoutParams> {
   override fun instantiate(context: Context) = SlidingPaneLayout(context)
   override fun instantiateLayoutParams() = SlidingPaneLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
}

/**
 * adds Views into this SlidingPaneLayout.
 */
@KoshianMarker
@ExperimentalContracts
inline fun <R> SlidingPaneLayout.addView(
      creatorAction: ViewGroupCreator<SlidingPaneLayout, Nothing, SlidingPaneLayout.LayoutParams>.() -> R
): R {
   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }
   return addView(SlidingPaneLayoutConstructor, creatorAction)
}

/**
 * creates a new SlidingPaneLayout and adds it into this ViewGroup.
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams> CreatorParent<L>.SlidingPaneLayout(
      creatorAction: ViewGroupCreator<SlidingPaneLayout, L, SlidingPaneLayout.LayoutParams>.() -> Unit
): SlidingPaneLayout {
   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }
   return create(SlidingPaneLayoutConstructor, creatorAction)
}

/**
 * creates a new SlidingPaneLayout with name, and adds it into this ViewGroup.
 *
 * The name can be referenced in [applyKoshian]
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams> CreatorParent<L>.SlidingPaneLayout(
      name: String,
      creatorAction: ViewGroupCreator<SlidingPaneLayout, L, SlidingPaneLayout.LayoutParams>.() -> Unit
): SlidingPaneLayout {
   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }
   return create(name, SlidingPaneLayoutConstructor, creatorAction)
}

/**
 * finds Views that are already added in this SlidingPaneLayout,
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
inline fun SlidingPaneLayout.applyKoshian(
      applierAction: ViewGroupApplier<SlidingPaneLayout, ViewGroup.LayoutParams, SlidingPaneLayout.LayoutParams, Nothing>.() -> Unit
) {
   applyKoshian(SlidingPaneLayoutConstructor, applierAction)
}

/**
 * finds Views that are already added in this SlidingPaneLayout,
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
inline fun <S : KoshianStyle> SlidingPaneLayout.applyKoshian(
      style: S,
      applierAction: ViewGroupApplier<SlidingPaneLayout, ViewGroup.LayoutParams, SlidingPaneLayout.LayoutParams, S>.() -> Unit
) {
   applyKoshian(style, SlidingPaneLayoutConstructor, applierAction)
}

/**
 * If the next View is a SlidingPaneLayout, applies Koshian to it.
 *
 * Otherwise, creates a new SlidingPaneLayout and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams, S : KoshianStyle>
      ApplierParent<L, S>.SlidingPaneLayout(
            applierAction: ViewGroupApplier<SlidingPaneLayout, L, SlidingPaneLayout.LayoutParams, S>.() -> Unit
      )
{
   applyViewGroup(SlidingPaneLayoutConstructor, applierAction)
}

/**
 * If the next View is a SlidingPaneLayout, applies Koshian to it.
 *
 * Otherwise, creates a new SlidingPaneLayout and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams, S : KoshianStyle>
      ApplierParent<L, S>.SlidingPaneLayout(
            styleElement: KoshianStyle.StyleElement<SlidingPaneLayout>,
            applierAction: ViewGroupApplier<SlidingPaneLayout, L, SlidingPaneLayout.LayoutParams, S>.() -> Unit
      )
{
   applyViewGroup(SlidingPaneLayoutConstructor, styleElement, applierAction)
}

/**
 * Applies Koshian to all SlidingPaneLayouts that are named the specified in this ViewGroup.
 * If there are no SlidingPaneLayouts named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams, S : KoshianStyle>
      ApplierParent<L, S>.SlidingPaneLayout(
            name: String,
            applierAction: ViewGroupApplier<SlidingPaneLayout, L, SlidingPaneLayout.LayoutParams, S>.() -> Unit
      )
{
   apply(name, SlidingPaneLayoutConstructor, applierAction)
}

/**
 * Applies Koshian to all SlidingPaneLayouts that are named the specified in this ViewGroup.
 * If there are no SlidingPaneLayouts named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams, S : KoshianStyle>
      ApplierParent<L, S>.SlidingPaneLayout(
            name: String,
            styleElement: KoshianStyle.StyleElement<SlidingPaneLayout>,
            applierAction: ViewGroupApplier<SlidingPaneLayout, L, SlidingPaneLayout.LayoutParams, S>.() -> Unit
      )
{
   apply(name, SlidingPaneLayoutConstructor, styleElement, applierAction)
}

/**
 * registers a style applier function into this [KoshianStyle].
 *
 * Styles can be applied via [applyKoshian]
 */
@Suppress("FunctionName")
inline fun KoshianStyle.SlidingPaneLayout(
      crossinline styleAction: ViewStyle<SlidingPaneLayout>.() -> Unit
): KoshianStyle.StyleElement<SlidingPaneLayout> {
   return createStyleElement(styleAction)
}
