@file:Suppress("UNUSED")
package koshian

import android.content.Context
import android.widget.CheckBox
import kotlin.contracts.*

object CheckBoxConstructor : KoshianViewConstructor<CheckBox> {
   override fun instantiate(context: Context?) = CheckBox(context)
}

/**
 * creates a new CheckBox and adds it into this ViewGroup.
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Creator>.CheckBox(
      buildAction: ViewBuilder<CheckBox, L, KoshianMode.Creator>.() -> Unit
): CheckBox {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return create(CheckBoxConstructor, buildAction)
}

/**
 * creates a new CheckBox with name, and adds it into this ViewGroup.
 *
 * The name can be referenced in [applyKoshian]
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Creator>.CheckBox(
      name: String,
      buildAction: ViewBuilder<CheckBox, L, KoshianMode.Creator>.() -> Unit
): CheckBox {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return create(name, CheckBoxConstructor, buildAction)
}

/**
 * If the next View is a CheckBox, applies Koshian to it.
 *
 * Otherwise, creates a new CheckBox and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Applier>.CheckBox(
      buildAction: ViewBuilder<CheckBox, L, KoshianMode.Applier>.() -> Unit
) {
   apply(CheckBoxConstructor, buildAction)
}

/**
 * Applies Koshian to all CheckBoxs that are named the specified in this ViewGroup.
 * If there are no CheckBoxs named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Applier>.CheckBox(
      name: String,
      buildAction: ViewBuilder<CheckBox, L, KoshianMode.Applier>.() -> Unit
) {
   apply(name, buildAction)
}
