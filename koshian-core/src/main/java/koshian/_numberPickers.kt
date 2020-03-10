@file:Suppress("UNUSED")
package koshian

import android.content.Context
import android.widget.NumberPicker
import kotlin.contracts.*

object NumberPickerConstructor : KoshianViewConstructor<NumberPicker> {
   override fun instantiate(context: Context?) = NumberPicker(context)
}

/**
 * creates a new NumberPicker and adds it into this ViewGroup.
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Creator>.NumberPicker(
      buildAction: ViewBuilder<NumberPicker, L, KoshianMode.Creator>.() -> Unit
): NumberPicker {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return create(NumberPickerConstructor, buildAction)
}

/**
 * If the next View is a NumberPicker, applies Koshian to it.
 *
 * Otherwise, creates a new NumberPicker and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Applier>.NumberPicker(
      buildAction: ViewBuilder<NumberPicker, L, KoshianMode.Applier>.() -> Unit
) {
   apply(NumberPickerConstructor, buildAction)
}
