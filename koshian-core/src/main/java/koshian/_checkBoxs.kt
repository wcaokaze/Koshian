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
