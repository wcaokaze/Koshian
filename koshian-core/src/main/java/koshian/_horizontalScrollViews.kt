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
import android.widget.HorizontalScrollView
import kotlin.contracts.*

object HorizontalScrollViewConstructor : KoshianViewGroupConstructor<HorizontalScrollView, FrameLayout.LayoutParams> {
   override fun instantiate(context: Context?) = HorizontalScrollView(context)
   override fun instantiateLayoutParams() = FrameLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
}

/**
 * adds Views into this HorizontalScrollView.
 */
@KoshianMarker
@ExperimentalContracts
inline fun <R> HorizontalScrollView.addView(
      creatorAction: ViewGroupCreator<HorizontalScrollView, Nothing, FrameLayout.LayoutParams>.() -> R
): R {
   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }
   return addView(HorizontalScrollViewConstructor, creatorAction)
}

/**
 * creates a new HorizontalScrollView and adds it into this ViewGroup.
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> CreatorParent<L>.HorizontalScrollView(
      creatorAction: ViewGroupCreator<HorizontalScrollView, L, FrameLayout.LayoutParams>.() -> Unit
): HorizontalScrollView {
   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }
   return create(HorizontalScrollViewConstructor, creatorAction)
}

/**
 * creates a new HorizontalScrollView with name, and adds it into this ViewGroup.
 *
 * The name can be referenced in [applyKoshian]
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> CreatorParent<L>.HorizontalScrollView(
      name: String,
      creatorAction: ViewGroupCreator<HorizontalScrollView, L, FrameLayout.LayoutParams>.() -> Unit
): HorizontalScrollView {
   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }
   return create(name, HorizontalScrollViewConstructor, creatorAction)
}

/**
 * finds Views that are already added in this HorizontalScrollView,
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
inline fun HorizontalScrollView.applyKoshian(
      applierAction: ViewGroupApplier<HorizontalScrollView, ViewGroup.LayoutParams, FrameLayout.LayoutParams, Nothing>.() -> Unit
) {
   applyKoshian(HorizontalScrollViewConstructor, applierAction)
}

/**
 * finds Views that are already added in this HorizontalScrollView,
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
inline fun <S : KoshianStyle> HorizontalScrollView.applyKoshian(
      style: S,
      applierAction: ViewGroupApplier<HorizontalScrollView, ViewGroup.LayoutParams, FrameLayout.LayoutParams, S>.() -> Unit
) {
   applyKoshian(style, HorizontalScrollViewConstructor, applierAction)
}

/**
 * If the next View is a HorizontalScrollView, applies Koshian to it.
 *
 * Otherwise, creates a new HorizontalScrollView and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L, S : KoshianStyle>
      ApplierParent<L, S>.HorizontalScrollView(
            applierAction: ViewGroupApplier<HorizontalScrollView, L, FrameLayout.LayoutParams, S>.() -> Unit
      )
{
   apply(HorizontalScrollViewConstructor, applierAction)
}

/**
 * If the next View is a HorizontalScrollView, applies Koshian to it.
 *
 * Otherwise, creates a new HorizontalScrollView and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L, S : KoshianStyle>
      ApplierParent<L, S>.HorizontalScrollView(
            styleElement: KoshianStyle.StyleElement<HorizontalScrollView>,
            applierAction: ViewGroupApplier<HorizontalScrollView, L, FrameLayout.LayoutParams, S>.() -> Unit
      )
{
   apply(HorizontalScrollViewConstructor, styleElement, applierAction)
}

/**
 * Applies Koshian to all HorizontalScrollViews that are named the specified in this ViewGroup.
 * If there are no HorizontalScrollViews named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L, S : KoshianStyle>
      ApplierParent<L, S>.HorizontalScrollView(
            name: String,
            applierAction: ViewGroupApplier<HorizontalScrollView, L, FrameLayout.LayoutParams, S>.() -> Unit
      )
{
   apply(name, HorizontalScrollViewConstructor, applierAction)
}

/**
 * Applies Koshian to all HorizontalScrollViews that are named the specified in this ViewGroup.
 * If there are no HorizontalScrollViews named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L, S : KoshianStyle>
      ApplierParent<L, S>.HorizontalScrollView(
            name: String,
            styleElement: KoshianStyle.StyleElement<HorizontalScrollView>,
            applierAction: ViewGroupApplier<HorizontalScrollView, L, FrameLayout.LayoutParams, S>.() -> Unit
      )
{
   apply(name, HorizontalScrollViewConstructor, styleElement, applierAction)
}

/**
 * registers a style applier function into this [KoshianStyle].
 *
 * Styles can be applied via [applyKoshian]
 */
@Suppress("FunctionName")
inline fun KoshianStyle.HorizontalScrollView(
      crossinline styleAction: ViewStyle<HorizontalScrollView>.() -> Unit
): KoshianStyle.StyleElement<HorizontalScrollView> {
   return createStyleElement(styleAction)
}
