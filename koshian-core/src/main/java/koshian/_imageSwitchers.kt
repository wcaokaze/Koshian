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
import android.widget.ImageSwitcher
import kotlin.contracts.*

object ImageSwitcherConstructor : KoshianViewGroupConstructor<ImageSwitcher, FrameLayout.LayoutParams> {
   override fun instantiate(context: Context?) = ImageSwitcher(context)
   override fun instantiateLayoutParams() = FrameLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
}

/**
 * adds Views into this ImageSwitcher.
 */
@KoshianMarker
@ExperimentalContracts
inline fun <R> ImageSwitcher.addView(
      creatorAction: ViewGroupCreator<ImageSwitcher, Nothing, FrameLayout.LayoutParams>.() -> R
): R {
   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }
   return addView(ImageSwitcherConstructor, creatorAction)
}

/**
 * creates a new ImageSwitcher and adds it into this ViewGroup.
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> CreatorParent<L>.ImageSwitcher(
      creatorAction: ViewGroupCreator<ImageSwitcher, L, FrameLayout.LayoutParams>.() -> Unit
): ImageSwitcher {
   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }
   return create(ImageSwitcherConstructor, creatorAction)
}

/**
 * creates a new ImageSwitcher with name, and adds it into this ViewGroup.
 *
 * The name can be referenced in [applyKoshian]
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> CreatorParent<L>.ImageSwitcher(
      name: String,
      creatorAction: ViewGroupCreator<ImageSwitcher, L, FrameLayout.LayoutParams>.() -> Unit
): ImageSwitcher {
   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }
   return create(name, ImageSwitcherConstructor, creatorAction)
}

/**
 * finds Views that are already added in this ImageSwitcher,
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
inline fun ImageSwitcher.applyKoshian(
      applierAction: ViewGroupApplier<ImageSwitcher, ViewGroup.LayoutParams, FrameLayout.LayoutParams, Nothing>.() -> Unit
) {
   applyKoshian(ImageSwitcherConstructor, applierAction)
}

/**
 * finds Views that are already added in this ImageSwitcher,
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
inline fun <S : KoshianStyle> ImageSwitcher.applyKoshian(
      style: S,
      applierAction: ViewGroupApplier<ImageSwitcher, ViewGroup.LayoutParams, FrameLayout.LayoutParams, S>.() -> Unit
) {
   applyKoshian(style, ImageSwitcherConstructor, applierAction)
}

/**
 * If the next View is a ImageSwitcher, applies Koshian to it.
 *
 * Otherwise, creates a new ImageSwitcher and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L, S : KoshianStyle>
      ApplierParent<L, S>.ImageSwitcher(
            applierAction: ViewGroupApplier<ImageSwitcher, L, FrameLayout.LayoutParams, S>.() -> Unit
      )
{
   apply(ImageSwitcherConstructor, applierAction)
}

/**
 * If the next View is a ImageSwitcher, applies Koshian to it.
 *
 * Otherwise, creates a new ImageSwitcher and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L, S : KoshianStyle>
      ApplierParent<L, S>.ImageSwitcher(
            styleElement: KoshianStyle.StyleElement<ImageSwitcher>,
            applierAction: ViewGroupApplier<ImageSwitcher, L, FrameLayout.LayoutParams, S>.() -> Unit
      )
{
   apply(ImageSwitcherConstructor, styleElement, applierAction)
}

/**
 * Applies Koshian to all ImageSwitchers that are named the specified in this ViewGroup.
 * If there are no ImageSwitchers named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L, S : KoshianStyle>
      ApplierParent<L, S>.ImageSwitcher(
            name: String,
            applierAction: ViewGroupApplier<ImageSwitcher, L, FrameLayout.LayoutParams, S>.() -> Unit
      )
{
   apply(name, ImageSwitcherConstructor, applierAction)
}

/**
 * Applies Koshian to all ImageSwitchers that are named the specified in this ViewGroup.
 * If there are no ImageSwitchers named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L, S : KoshianStyle>
      ApplierParent<L, S>.ImageSwitcher(
            name: String,
            styleElement: KoshianStyle.StyleElement<ImageSwitcher>,
            applierAction: ViewGroupApplier<ImageSwitcher, L, FrameLayout.LayoutParams, S>.() -> Unit
      )
{
   apply(name, ImageSwitcherConstructor, styleElement, applierAction)
}

/**
 * registers a style applier function into this [KoshianStyle].
 *
 * Styles can be applied via [applyKoshian]
 */
@Suppress("FunctionName")
inline fun KoshianStyle.ImageSwitcher(
      crossinline styleAction: ViewStyle<ImageSwitcher>.() -> Unit
): KoshianStyle.StyleElement<ImageSwitcher> {
   return createStyleElement(styleAction)
}
