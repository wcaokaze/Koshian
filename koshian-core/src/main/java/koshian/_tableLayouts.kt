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
import android.widget.TableLayout
import kotlin.contracts.*

object TableLayoutConstructor : KoshianViewGroupConstructor<TableLayout, TableLayout.LayoutParams> {
   override fun instantiate(context: Context?) = TableLayout(context)
   override fun instantiateLayoutParams() = TableLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
}

/**
 * adds Views into this TableLayout.
 */
@ExperimentalContracts
inline fun <R> TableLayout.addView(
      creatorAction: ViewGroupCreator<TableLayout, Nothing, TableLayout.LayoutParams>.() -> R
): R {
   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }
   return addView(TableLayoutConstructor, creatorAction)
}

/**
 * creates a new TableLayout and adds it into this ViewGroup.
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> CreatorParent<L>.TableLayout(
      creatorAction: ViewGroupCreator<TableLayout, L, TableLayout.LayoutParams>.() -> Unit
): TableLayout {
   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }
   return create(TableLayoutConstructor, creatorAction)
}

/**
 * creates a new TableLayout with name, and adds it into this ViewGroup.
 *
 * The name can be referenced in [applyKoshian]
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> CreatorParent<L>.TableLayout(
      name: String,
      creatorAction: ViewGroupCreator<TableLayout, L, TableLayout.LayoutParams>.() -> Unit
): TableLayout {
   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }
   return create(name, TableLayoutConstructor, creatorAction)
}

/**
 * finds Views that are already added in this TableLayout,
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
inline fun TableLayout.applyKoshian(
      applierAction: ViewGroupApplier<TableLayout, ViewGroup.LayoutParams, TableLayout.LayoutParams, Nothing>.() -> Unit
) {
   applyKoshian(TableLayoutConstructor, applierAction)
}

/**
 * finds Views that are already added in this TableLayout,
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
inline fun <S : KoshianStyle> TableLayout.applyKoshian(
      style: S,
      applierAction: ViewGroupApplier<TableLayout, ViewGroup.LayoutParams, TableLayout.LayoutParams, S>.() -> Unit
) {
   applyKoshian(style, TableLayoutConstructor, applierAction)
}

/**
 * If the next View is a TableLayout, applies Koshian to it.
 *
 * Otherwise, creates a new TableLayout and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L, S : KoshianStyle>
      ApplierParent<L, S>.TableLayout(
            applierAction: ViewGroupApplier<TableLayout, L, TableLayout.LayoutParams, S>.() -> Unit
      )
{
   apply(TableLayoutConstructor, applierAction)
}

/**
 * If the next View is a TableLayout, applies Koshian to it.
 *
 * Otherwise, creates a new TableLayout and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L, S : KoshianStyle>
      ApplierParent<L, S>.TableLayout(
            styleElement: KoshianStyle.StyleElement<TableLayout>,
            applierAction: ViewGroupApplier<TableLayout, L, TableLayout.LayoutParams, S>.() -> Unit
      )
{
   apply(TableLayoutConstructor, styleElement, applierAction)
}

/**
 * Applies Koshian to all TableLayouts that are named the specified in this ViewGroup.
 * If there are no TableLayouts named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L, S : KoshianStyle>
      ApplierParent<L, S>.TableLayout(
            name: String,
            applierAction: ViewGroupApplier<TableLayout, L, TableLayout.LayoutParams, S>.() -> Unit
      )
{
   apply(name, TableLayoutConstructor, applierAction)
}

/**
 * Applies Koshian to all TableLayouts that are named the specified in this ViewGroup.
 * If there are no TableLayouts named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L, S : KoshianStyle>
      ApplierParent<L, S>.TableLayout(
            name: String,
            styleElement: KoshianStyle.StyleElement<TableLayout>,
            applierAction: ViewGroupApplier<TableLayout, L, TableLayout.LayoutParams, S>.() -> Unit
      )
{
   apply(name, TableLayoutConstructor, styleElement, applierAction)
}

/**
 * registers a style applier function into this [KoshianStyle].
 *
 * Styles can be applied via [applyKoshian]
 */
@Suppress("FunctionName")
inline fun KoshianStyle.TableLayout(
      crossinline styleAction: ViewStyle<TableLayout>.() -> Unit
): KoshianStyle.StyleElement<TableLayout> {
   return createStyleElement(styleAction)
}
