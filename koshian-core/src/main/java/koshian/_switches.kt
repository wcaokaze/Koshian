@file:Suppress("UNUSED")
package koshian

import android.content.Context
import android.widget.Switch
import kotlin.contracts.*

object SwitchConstructor : KoshianViewConstructor<Switch> {
   override fun instantiate(context: Context?) = Switch(context)
}

/**
 * creates a new Switch and adds it into this ViewGroup.
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Creator>.Switch(
      buildAction: ViewBuilder<Switch, L, KoshianMode.Creator>.() -> Unit
): Switch {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return create(SwitchConstructor, buildAction)
}

/**
 * If the next View is a Switch, applies Koshian to it.
 *
 * Otherwise, creates a new Switch and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Applier>.Switch(
      buildAction: ViewBuilder<Switch, L, KoshianMode.Applier>.() -> Unit
) {
   apply(SwitchConstructor, buildAction)
}
