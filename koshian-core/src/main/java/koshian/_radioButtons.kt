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
