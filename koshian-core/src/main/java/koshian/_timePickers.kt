@file:Suppress("UNUSED")
package koshian

import android.content.Context
import android.widget.TimePicker
import kotlin.contracts.*

object TimePickerConstructor : KoshianViewConstructor<TimePicker> {
   override fun instantiate(context: Context?) = TimePicker(context)
}

/**
 * creates a new TimePicker and adds it into this ViewGroup.
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Creator>.TimePicker(
      buildAction: ViewBuilder<TimePicker, L, KoshianMode.Creator>.() -> Unit
): TimePicker {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return create(TimePickerConstructor, buildAction)
}

/**
 * creates a new TimePicker with name, and adds it into this ViewGroup.
 *
 * The name can be referenced in [applyKoshian]
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Creator>.TimePicker(
      name: String,
      buildAction: ViewBuilder<TimePicker, L, KoshianMode.Creator>.() -> Unit
): TimePicker {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return create(name, TimePickerConstructor, buildAction)
}

/**
 * If the next View is a TimePicker, applies Koshian to it.
 *
 * Otherwise, creates a new TimePicker and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Applier>.TimePicker(
      buildAction: ViewBuilder<TimePicker, L, KoshianMode.Applier>.() -> Unit
) {
   apply(TimePickerConstructor, buildAction)
}

/**
 * Applies Koshian to all TimePickers that are named the specified in this ViewGroup.
 * If there are no TimePickers named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Applier>.TimePicker(
      name: String,
      buildAction: ViewBuilder<TimePicker, L, KoshianMode.Applier>.() -> Unit
) {
   apply(name, buildAction)
}