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
import android.widget.TableRow
import kotlin.contracts.*

object TableRowConstructor : KoshianViewGroupConstructor<TableRow, TableRow.LayoutParams> {
   override fun instantiate(context: Context?) = TableRow(context)
   override fun instantiateLayoutParams() = TableRow.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
}

/**
 * adds Views into this TableRow.
 */
@KoshianMarker
@ExperimentalContracts
inline fun <R> TableRow.addView(
      creatorAction: ViewGroupCreator<TableRow, Nothing, TableRow.LayoutParams>.() -> R
): R {
   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }
   return addView(TableRowConstructor, creatorAction)
}

/**
 * creates a new TableRow and adds it into this ViewGroup.
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> CreatorParent<L>.TableRow(
      creatorAction: ViewGroupCreator<TableRow, L, TableRow.LayoutParams>.() -> Unit
): TableRow {
   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }
   return create(TableRowConstructor, creatorAction)
}

/**
 * creates a new TableRow with name, and adds it into this ViewGroup.
 *
 * The name can be referenced in [applyKoshian]
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> CreatorParent<L>.TableRow(
      name: String,
      creatorAction: ViewGroupCreator<TableRow, L, TableRow.LayoutParams>.() -> Unit
): TableRow {
   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }
   return create(name, TableRowConstructor, creatorAction)
}

/**
 * finds Views that are already added in this TableRow,
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
inline fun TableRow.applyKoshian(
      applierAction: ViewGroupApplier<TableRow, ViewGroup.LayoutParams, TableRow.LayoutParams, Nothing>.() -> Unit
) {
   applyKoshian(TableRowConstructor, applierAction)
}

/**
 * finds Views that are already added in this TableRow,
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
inline fun <S : KoshianStyle> TableRow.applyKoshian(
      style: S,
      applierAction: ViewGroupApplier<TableRow, ViewGroup.LayoutParams, TableRow.LayoutParams, S>.() -> Unit
) {
   applyKoshian(style, TableRowConstructor, applierAction)
}

/**
 * If the next View is a TableRow, applies Koshian to it.
 *
 * Otherwise, creates a new TableRow and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L, S : KoshianStyle>
      ApplierParent<L, S>.TableRow(
            applierAction: ViewGroupApplier<TableRow, L, TableRow.LayoutParams, S>.() -> Unit
      )
{
   apply(TableRowConstructor, applierAction)
}

/**
 * If the next View is a TableRow, applies Koshian to it.
 *
 * Otherwise, creates a new TableRow and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L, S : KoshianStyle>
      ApplierParent<L, S>.TableRow(
            styleElement: KoshianStyle.StyleElement<TableRow>,
            applierAction: ViewGroupApplier<TableRow, L, TableRow.LayoutParams, S>.() -> Unit
      )
{
   apply(TableRowConstructor, styleElement, applierAction)
}

/**
 * Applies Koshian to all TableRows that are named the specified in this ViewGroup.
 * If there are no TableRows named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L, S : KoshianStyle>
      ApplierParent<L, S>.TableRow(
            name: String,
            applierAction: ViewGroupApplier<TableRow, L, TableRow.LayoutParams, S>.() -> Unit
      )
{
   apply(name, TableRowConstructor, applierAction)
}

/**
 * Applies Koshian to all TableRows that are named the specified in this ViewGroup.
 * If there are no TableRows named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L, S : KoshianStyle>
      ApplierParent<L, S>.TableRow(
            name: String,
            styleElement: KoshianStyle.StyleElement<TableRow>,
            applierAction: ViewGroupApplier<TableRow, L, TableRow.LayoutParams, S>.() -> Unit
      )
{
   apply(name, TableRowConstructor, styleElement, applierAction)
}

/**
 * registers a style applier function into this [KoshianStyle].
 *
 * Styles can be applied via [applyKoshian]
 */
@Suppress("FunctionName")
inline fun KoshianStyle.TableRow(
      crossinline styleAction: ViewStyle<TableRow>.() -> Unit
): KoshianStyle.StyleElement<TableRow> {
   return createStyleElement(styleAction)
}
