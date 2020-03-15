@file:Suppress("UNUSED")
package koshian

import android.content.Context
import android.widget.RadioButton
import kotlin.contracts.*

object RadioButtonConstructor : KoshianViewConstructor<RadioButton> {
   override fun instantiate(context: Context?) = RadioButton(context)
}

/**
 * creates a new RadioButton and adds it into this ViewGroup.
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Creator>.RadioButton(
      buildAction: ViewBuilder<RadioButton, L, KoshianMode.Creator>.() -> Unit
): RadioButton {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return create(RadioButtonConstructor, buildAction)
}

/**
 * creates a new RadioButton with name, and adds it into this ViewGroup.
 *
 * The name can be referenced in [applyKoshian]
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Creator>.RadioButton(
      name: String,
      buildAction: ViewBuilder<RadioButton, L, KoshianMode.Creator>.() -> Unit
): RadioButton {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return create(name, RadioButtonConstructor, buildAction)
}

/**
 * If the next View is a RadioButton, applies Koshian to it.
 *
 * Otherwise, creates a new RadioButton and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Applier>.RadioButton(
      buildAction: ViewBuilder<RadioButton, L, KoshianMode.Applier>.() -> Unit
) {
   apply(RadioButtonConstructor, buildAction)
}

/**
 * Applies Koshian to all RadioButtons that are named the specified in this ViewGroup.
 * If there are no RadioButtons named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Applier>.RadioButton(
      name: String,
      buildAction: ViewBuilder<RadioButton, L, KoshianMode.Applier>.() -> Unit
) {
   apply(name, buildAction)
}
