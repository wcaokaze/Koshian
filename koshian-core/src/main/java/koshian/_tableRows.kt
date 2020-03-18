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
import android.widget.TableRow
import kotlin.contracts.*

object TableRowConstructor : KoshianViewConstructor<TableRow> {
   override fun instantiate(context: Context?) = TableRow(context)
}

/**
 * creates a new TableRow and adds it into this ViewGroup.
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Creator>.TableRow(
      buildAction: ViewBuilder<TableRow, L, KoshianMode.Creator>.() -> Unit
): TableRow {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return create(TableRowConstructor, buildAction)
}

/**
 * creates a new TableRow with name, and adds it into this ViewGroup.
 *
 * The name can be referenced in [applyKoshian]
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Creator>.TableRow(
      name: String,
      buildAction: ViewBuilder<TableRow, L, KoshianMode.Creator>.() -> Unit
): TableRow {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return create(name, TableRowConstructor, buildAction)
}

/**
 * If the next View is a TableRow, applies Koshian to it.
 *
 * Otherwise, creates a new TableRow and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Applier>.TableRow(
      buildAction: ViewBuilder<TableRow, L, KoshianMode.Applier>.() -> Unit
) {
   apply(TableRowConstructor, buildAction)
}

/**
 * Applies Koshian to all TableRows that are named the specified in this ViewGroup.
 * If there are no TableRows named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Applier>.TableRow(
      name: String,
      buildAction: ViewBuilder<TableRow, L, KoshianMode.Applier>.() -> Unit
) {
   apply(name, buildAction)
}
