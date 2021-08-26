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
import androidx.coordinatorlayout.widget.CoordinatorLayout
import koshian.*
import kotlin.contracts.*

object CoordinatorLayoutConstructor : KoshianViewConstructor<CoordinatorLayout, CoordinatorLayout.LayoutParams> {
   override fun instantiate(context: Context) = CoordinatorLayout(context)
   override fun instantiateLayoutParams() = CoordinatorLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
}

/**
 * adds Views into this CoordinatorLayout.
 */
@KoshianMarker
@ExperimentalContracts
inline fun <R> CoordinatorLayout.addView(
      creatorAction: ViewGroupCreator<CoordinatorLayout, Nothing, CoordinatorLayout.LayoutParams>.() -> R
): R {
   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }
   return addView(CoordinatorLayoutConstructor, creatorAction)
}

/**
 * creates a new CoordinatorLayout and adds it into this ViewGroup.
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams> CreatorParent<L>.CoordinatorLayout(
      creatorAction: ViewGroupCreator<CoordinatorLayout, L, CoordinatorLayout.LayoutParams>.() -> Unit
): CoordinatorLayout {
   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }
   return create(CoordinatorLayoutConstructor, creatorAction)
}

/**
 * creates a new CoordinatorLayout with name, and adds it into this ViewGroup.
 *
 * The name can be referenced in [applyKoshian]
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams> CreatorParent<L>.CoordinatorLayout(
      name: String,
      creatorAction: ViewGroupCreator<CoordinatorLayout, L, CoordinatorLayout.LayoutParams>.() -> Unit
): CoordinatorLayout {
   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }
   return create(name, CoordinatorLayoutConstructor, creatorAction)
}

/**
 * finds Views that are already added in this CoordinatorLayout,
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
inline fun CoordinatorLayout.applyKoshian(
      applierAction: ViewGroupApplier<CoordinatorLayout, ViewGroup.LayoutParams, CoordinatorLayout.LayoutParams, Nothing>.() -> Unit
) {
   applyKoshian(CoordinatorLayoutConstructor, applierAction)
}

/**
 * finds Views that are already added in this CoordinatorLayout,
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
inline fun <S : KoshianStyle> CoordinatorLayout.applyKoshian(
      style: S,
      applierAction: ViewGroupApplier<CoordinatorLayout, ViewGroup.LayoutParams, CoordinatorLayout.LayoutParams, S>.() -> Unit
) {
   applyKoshian(style, CoordinatorLayoutConstructor, applierAction)
}

/**
 * If the next View is a CoordinatorLayout, applies Koshian to it.
 *
 * Otherwise, creates a new CoordinatorLayout and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams, S : KoshianStyle>
      ApplierParent<L, S>.CoordinatorLayout(
            applierAction: ViewGroupApplier<CoordinatorLayout, L, CoordinatorLayout.LayoutParams, S>.() -> Unit
      )
{
   applyViewGroup(CoordinatorLayoutConstructor, applierAction)
}

/**
 * If the next View is a CoordinatorLayout, applies Koshian to it.
 *
 * Otherwise, creates a new CoordinatorLayout and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams, S : KoshianStyle>
      ApplierParent<L, S>.CoordinatorLayout(
            styleElement: KoshianStyle.StyleElement<CoordinatorLayout>,
            applierAction: ViewGroupApplier<CoordinatorLayout, L, CoordinatorLayout.LayoutParams, S>.() -> Unit
      )
{
   applyViewGroup(CoordinatorLayoutConstructor, styleElement, applierAction)
}

/**
 * Applies Koshian to all CoordinatorLayouts that are named the specified in this ViewGroup.
 * If there are no CoordinatorLayouts named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams, S : KoshianStyle>
      ApplierParent<L, S>.CoordinatorLayout(
            name: String,
            applierAction: ViewGroupApplier<CoordinatorLayout, L, CoordinatorLayout.LayoutParams, S>.() -> Unit
      )
{
   apply(name, CoordinatorLayoutConstructor, applierAction)
}

/**
 * Applies Koshian to all CoordinatorLayouts that are named the specified in this ViewGroup.
 * If there are no CoordinatorLayouts named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams, S : KoshianStyle>
      ApplierParent<L, S>.CoordinatorLayout(
            name: String,
            styleElement: KoshianStyle.StyleElement<CoordinatorLayout>,
            applierAction: ViewGroupApplier<CoordinatorLayout, L, CoordinatorLayout.LayoutParams, S>.() -> Unit
      )
{
   apply(name, CoordinatorLayoutConstructor, styleElement, applierAction)
}

/**
 * registers a style applier function into this [KoshianStyle].
 *
 * Styles can be applied via [applyKoshian]
 */
@Suppress("FunctionName")
inline fun KoshianStyle.CoordinatorLayout(
      crossinline styleAction: ViewStyle<CoordinatorLayout>.() -> Unit
): KoshianStyle.StyleElement<CoordinatorLayout> {
   return createStyleElement(styleAction)
}
