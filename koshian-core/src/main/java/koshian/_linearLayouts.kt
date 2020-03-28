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
import android.widget.LinearLayout
import kotlin.contracts.*

object LinearLayoutConstructor : KoshianViewGroupConstructor<LinearLayout, LinearLayout.LayoutParams> {
   override fun instantiate(context: Context?) = LinearLayout(context)
   override fun instantiateLayoutParams() = LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
}

/**
 * adds Views into this LinearLayout.
 */
@ExperimentalContracts
inline fun <R> LinearLayout.addView(
      creatorAction: ViewGroupCreator<LinearLayout, Nothing, LinearLayout.LayoutParams>.() -> R
): R {
   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }
   return addView(LinearLayoutConstructor, creatorAction)
}

/**
 * creates a new LinearLayout and adds it into this ViewGroup.
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> CreatorParent<L>.LinearLayout(
      creatorAction: ViewGroupCreator<LinearLayout, L, LinearLayout.LayoutParams>.() -> Unit
): LinearLayout {
   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }
   return create(LinearLayoutConstructor, creatorAction)
}

/**
 * creates a new LinearLayout with name, and adds it into this ViewGroup.
 *
 * The name can be referenced in [applyKoshian]
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> CreatorParent<L>.LinearLayout(
      name: String,
      creatorAction: ViewGroupCreator<LinearLayout, L, LinearLayout.LayoutParams>.() -> Unit
): LinearLayout {
   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }
   return create(name, LinearLayoutConstructor, creatorAction)
}

/**
 * finds Views that are already added in this LinearLayout,
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
inline fun LinearLayout.applyKoshian(
      applierAction: ViewGroupApplier<LinearLayout, ViewGroup.LayoutParams, LinearLayout.LayoutParams, Nothing>.() -> Unit
) {
   applyKoshian(LinearLayoutConstructor, applierAction)
}

/**
 * finds Views that are already added in this LinearLayout,
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
inline fun <S : KoshianStyle> LinearLayout.applyKoshian(
      style: S,
      applierAction: ViewGroupApplier<LinearLayout, ViewGroup.LayoutParams, LinearLayout.LayoutParams, S>.() -> Unit
) {
   applyKoshian(style, LinearLayoutConstructor, applierAction)
}

/**
 * If the next View is a LinearLayout, applies Koshian to it.
 *
 * Otherwise, creates a new LinearLayout and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L, S : KoshianStyle>
      ApplierParent<L, S>.LinearLayout(
            applierAction: ViewGroupApplier<LinearLayout, L, LinearLayout.LayoutParams, S>.() -> Unit
      )
{
   apply(LinearLayoutConstructor, applierAction)
}

/**
 * If the next View is a LinearLayout, applies Koshian to it.
 *
 * Otherwise, creates a new LinearLayout and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L, S : KoshianStyle>
      ApplierParent<L, S>.LinearLayout(
            styleElement: KoshianStyle.StyleElement<LinearLayout>,
            applierAction: ViewGroupApplier<LinearLayout, L, LinearLayout.LayoutParams, S>.() -> Unit
      )
{
   apply(LinearLayoutConstructor, styleElement, applierAction)
}

/**
 * Applies Koshian to all LinearLayouts that are named the specified in this ViewGroup.
 * If there are no LinearLayouts named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L, S : KoshianStyle>
      ApplierParent<L, S>.LinearLayout(
            name: String,
            applierAction: ViewGroupApplier<LinearLayout, L, LinearLayout.LayoutParams, S>.() -> Unit
      )
{
   apply(name, LinearLayoutConstructor, applierAction)
}

/**
 * Applies Koshian to all LinearLayouts that are named the specified in this ViewGroup.
 * If there are no LinearLayouts named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L, S : KoshianStyle>
      ApplierParent<L, S>.LinearLayout(
            name: String,
            styleElement: KoshianStyle.StyleElement<LinearLayout>,
            applierAction: ViewGroupApplier<LinearLayout, L, LinearLayout.LayoutParams, S>.() -> Unit
      )
{
   apply(name, LinearLayoutConstructor, styleElement, applierAction)
}

/**
 * registers a style applier function into this [KoshianStyle].
 *
 * Styles can be applied via [applyKoshian]
 */
@Suppress("FunctionName")
inline fun KoshianStyle.LinearLayout(
      crossinline styleAction: ViewStyle<LinearLayout>.() -> Unit
): KoshianStyle.StyleElement<LinearLayout> {
   return createStyleElement(styleAction)
}

val KoshianExt<LinearLayout, *>.VERTICAL   inline get() = LinearLayout.VERTICAL
val KoshianExt<LinearLayout, *>.HORIZONTAL inline get() = LinearLayout.HORIZONTAL
