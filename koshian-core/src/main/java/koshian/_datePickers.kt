@file:Suppress("UNUSED")
package koshian

import android.content.Context
import android.widget.DatePicker
import kotlin.contracts.*

object DatePickerConstructor : KoshianViewConstructor<DatePicker> {
   override fun instantiate(context: Context?) = DatePicker(context)
}

/**
 * creates a new DatePicker and adds it into this ViewGroup.
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Creator>.DatePicker(
      buildAction: ViewBuilder<DatePicker, L, KoshianMode.Creator>.() -> Unit
): DatePicker {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return create(DatePickerConstructor, buildAction)
}

/**
 * creates a new DatePicker with name, and adds it into this ViewGroup.
 *
 * The name can be referenced in [applyKoshian]
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Creator>.DatePicker(
      name: String,
      buildAction: ViewBuilder<DatePicker, L, KoshianMode.Creator>.() -> Unit
): DatePicker {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return create(name, DatePickerConstructor, buildAction)
}

/**
 * If the next View is a DatePicker, applies Koshian to it.
 *
 * Otherwise, creates a new DatePicker and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Applier>.DatePicker(
      buildAction: ViewBuilder<DatePicker, L, KoshianMode.Applier>.() -> Unit
) {
   apply(DatePickerConstructor, buildAction)
}

/**
 * Applies Koshian to all DatePickers that are named the specified in this ViewGroup.
 * If there are no DatePickers named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Applier>.DatePicker(
      name: String,
      buildAction: ViewBuilder<DatePicker, L, KoshianMode.Applier>.() -> Unit
) {
   apply(name, buildAction)
}
