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
