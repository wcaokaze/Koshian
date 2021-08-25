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
import android.widget.ViewFlipper
import kotlin.contracts.*

object ViewFlipperConstructor : KoshianViewConstructor<ViewFlipper, FrameLayout.LayoutParams> {
   override fun instantiate(context: Context?) = ViewFlipper(context)
   override fun instantiateLayoutParams() = FrameLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
}

/**
 * adds Views into this ViewFlipper.
 */
@KoshianMarker
@ExperimentalContracts
inline fun <R> ViewFlipper.addView(
      creatorAction: ViewGroupCreator<ViewFlipper, Nothing, FrameLayout.LayoutParams>.() -> R
): R {
   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }
   return addView(ViewFlipperConstructor, creatorAction)
}

/**
 * creates a new ViewFlipper and adds it into this ViewGroup.
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams> CreatorParent<L>.ViewFlipper(
      creatorAction: ViewGroupCreator<ViewFlipper, L, FrameLayout.LayoutParams>.() -> Unit
): ViewFlipper {
   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }
   return create(ViewFlipperConstructor, creatorAction)
}

/**
 * creates a new ViewFlipper with name, and adds it into this ViewGroup.
 *
 * The name can be referenced in [applyKoshian]
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams> CreatorParent<L>.ViewFlipper(
      name: String,
      creatorAction: ViewGroupCreator<ViewFlipper, L, FrameLayout.LayoutParams>.() -> Unit
): ViewFlipper {
   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }
   return create(name, ViewFlipperConstructor, creatorAction)
}

/**
 * finds Views that are already added in this ViewFlipper,
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
inline fun ViewFlipper.applyKoshian(
      applierAction: ViewGroupApplier<ViewFlipper, ViewGroup.LayoutParams, FrameLayout.LayoutParams, Nothing>.() -> Unit
) {
   applyKoshian(ViewFlipperConstructor, applierAction)
}

/**
 * finds Views that are already added in this ViewFlipper,
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
inline fun <S : KoshianStyle> ViewFlipper.applyKoshian(
      style: S,
      applierAction: ViewGroupApplier<ViewFlipper, ViewGroup.LayoutParams, FrameLayout.LayoutParams, S>.() -> Unit
) {
   applyKoshian(style, ViewFlipperConstructor, applierAction)
}

/**
 * If the next View is a ViewFlipper, applies Koshian to it.
 *
 * Otherwise, creates a new ViewFlipper and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams, S : KoshianStyle>
      ApplierParent<L, S>.ViewFlipper(
            applierAction: ViewGroupApplier<ViewFlipper, L, FrameLayout.LayoutParams, S>.() -> Unit
      )
{
   applyViewGroup(ViewFlipperConstructor, applierAction)
}

/**
 * If the next View is a ViewFlipper, applies Koshian to it.
 *
 * Otherwise, creates a new ViewFlipper and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams, S : KoshianStyle>
      ApplierParent<L, S>.ViewFlipper(
            styleElement: KoshianStyle.StyleElement<ViewFlipper>,
            applierAction: ViewGroupApplier<ViewFlipper, L, FrameLayout.LayoutParams, S>.() -> Unit
      )
{
   applyViewGroup(ViewFlipperConstructor, styleElement, applierAction)
}

/**
 * Applies Koshian to all ViewFlippers that are named the specified in this ViewGroup.
 * If there are no ViewFlippers named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams, S : KoshianStyle>
      ApplierParent<L, S>.ViewFlipper(
            name: String,
            applierAction: ViewGroupApplier<ViewFlipper, L, FrameLayout.LayoutParams, S>.() -> Unit
      )
{
   apply(name, ViewFlipperConstructor, applierAction)
}

/**
 * Applies Koshian to all ViewFlippers that are named the specified in this ViewGroup.
 * If there are no ViewFlippers named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams, S : KoshianStyle>
      ApplierParent<L, S>.ViewFlipper(
            name: String,
            styleElement: KoshianStyle.StyleElement<ViewFlipper>,
            applierAction: ViewGroupApplier<ViewFlipper, L, FrameLayout.LayoutParams, S>.() -> Unit
      )
{
   apply(name, ViewFlipperConstructor, styleElement, applierAction)
}

/**
 * registers a style applier function into this [KoshianStyle].
 *
 * Styles can be applied via [applyKoshian]
 */
@Suppress("FunctionName")
inline fun KoshianStyle.ViewFlipper(
      crossinline styleAction: ViewStyle<ViewFlipper>.() -> Unit
): KoshianStyle.StyleElement<ViewFlipper> {
   return createStyleElement(styleAction)
}
