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
import androidx.drawerlayout.widget.DrawerLayout
import koshian.*
import kotlin.contracts.*

object DrawerLayoutConstructor : KoshianViewGroupConstructor<DrawerLayout, DrawerLayout.LayoutParams> {
   override fun instantiate(context: Context) = DrawerLayout(context)
   override fun instantiateLayoutParams() = DrawerLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
}

/**
 * adds Views into this DrawerLayout.
 */
@KoshianMarker
@ExperimentalContracts
inline fun <R> DrawerLayout.addView(
      creatorAction: ViewGroupCreator<DrawerLayout, Nothing, DrawerLayout.LayoutParams>.() -> R
): R {
   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }
   return addView(DrawerLayoutConstructor, creatorAction)
}

/**
 * creates a new DrawerLayout and adds it into this ViewGroup.
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> CreatorParent<L>.DrawerLayout(
      creatorAction: ViewGroupCreator<DrawerLayout, L, DrawerLayout.LayoutParams>.() -> Unit
): DrawerLayout {
   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }
   return create(DrawerLayoutConstructor, creatorAction)
}

/**
 * creates a new DrawerLayout with name, and adds it into this ViewGroup.
 *
 * The name can be referenced in [applyKoshian]
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> CreatorParent<L>.DrawerLayout(
      name: String,
      creatorAction: ViewGroupCreator<DrawerLayout, L, DrawerLayout.LayoutParams>.() -> Unit
): DrawerLayout {
   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }
   return create(name, DrawerLayoutConstructor, creatorAction)
}

/**
 * finds Views that are already added in this DrawerLayout,
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
inline fun DrawerLayout.applyKoshian(
      applierAction: ViewGroupApplier<DrawerLayout, ViewGroup.LayoutParams, DrawerLayout.LayoutParams, Nothing>.() -> Unit
) {
   applyKoshian(DrawerLayoutConstructor, applierAction)
}

/**
 * finds Views that are already added in this DrawerLayout,
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
inline fun <S : KoshianStyle> DrawerLayout.applyKoshian(
      style: S,
      applierAction: ViewGroupApplier<DrawerLayout, ViewGroup.LayoutParams, DrawerLayout.LayoutParams, S>.() -> Unit
) {
   applyKoshian(style, DrawerLayoutConstructor, applierAction)
}

/**
 * If the next View is a DrawerLayout, applies Koshian to it.
 *
 * Otherwise, creates a new DrawerLayout and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L, S : KoshianStyle>
      ApplierParent<L, S>.DrawerLayout(
            applierAction: ViewGroupApplier<DrawerLayout, L, DrawerLayout.LayoutParams, S>.() -> Unit
      )
{
   apply(DrawerLayoutConstructor, applierAction)
}

/**
 * If the next View is a DrawerLayout, applies Koshian to it.
 *
 * Otherwise, creates a new DrawerLayout and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L, S : KoshianStyle>
      ApplierParent<L, S>.DrawerLayout(
            styleElement: KoshianStyle.StyleElement<DrawerLayout>,
            applierAction: ViewGroupApplier<DrawerLayout, L, DrawerLayout.LayoutParams, S>.() -> Unit
      )
{
   apply(DrawerLayoutConstructor, styleElement, applierAction)
}

/**
 * Applies Koshian to all DrawerLayouts that are named the specified in this ViewGroup.
 * If there are no DrawerLayouts named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L, S : KoshianStyle>
      ApplierParent<L, S>.DrawerLayout(
            name: String,
            applierAction: ViewGroupApplier<DrawerLayout, L, DrawerLayout.LayoutParams, S>.() -> Unit
      )
{
   apply(name, DrawerLayoutConstructor, applierAction)
}

/**
 * Applies Koshian to all DrawerLayouts that are named the specified in this ViewGroup.
 * If there are no DrawerLayouts named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L, S : KoshianStyle>
      ApplierParent<L, S>.DrawerLayout(
            name: String,
            styleElement: KoshianStyle.StyleElement<DrawerLayout>,
            applierAction: ViewGroupApplier<DrawerLayout, L, DrawerLayout.LayoutParams, S>.() -> Unit
      )
{
   apply(name, DrawerLayoutConstructor, styleElement, applierAction)
}

/**
 * registers a style applier function into this [KoshianStyle].
 *
 * Styles can be applied via [applyKoshian]
 */
@Suppress("FunctionName")
inline fun KoshianStyle.DrawerLayout(
      crossinline styleAction: ViewStyle<DrawerLayout>.() -> Unit
): KoshianStyle.StyleElement<DrawerLayout> {
   return createStyleElement(styleAction)
}
