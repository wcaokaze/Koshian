@file:Suppress("UNUSED")
package koshian

import android.content.Context
import android.widget.TextView
import kotlin.contracts.*

object TextViewConstructor : KoshianViewConstructor<TextView> {
   override fun instantiate(context: Context?) = TextView(context)
}

/**
 * creates a new TextView and adds it into this ViewGroup.
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Creator>.TextView(
      buildAction: ViewBuilder<TextView, L, KoshianMode.Creator>.() -> Unit
): TextView {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return create(TextViewConstructor, buildAction)
}

/**
 * If the next View is a TextView, applies Koshian to it.
 *
 * Otherwise, creates a new TextView and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Applier>.TextView(
      buildAction: ViewBuilder<TextView, L, KoshianMode.Applier>.() -> Unit
) {
   apply(TextViewConstructor, buildAction)
}
