@file:Suppress("UNUSED")
package koshian

import android.content.Context
import android.widget.Button
import kotlin.contracts.*

object ButtonConstructor : KoshianViewConstructor<Button> {
   override fun instantiate(context: Context?) = Button(context)
}

/**
 * creates a new Button and adds it into this ViewGroup.
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Creator>.Button(
      buildAction: ViewBuilder<Button, L, KoshianMode.Creator>.() -> Unit
): Button {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return create(ButtonConstructor, buildAction)
}

/**
 * If the next View is a Button, applies Koshian to it.
 *
 * Otherwise, creates a new Button and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Applier>.Button(
      buildAction: ViewBuilder<Button, L, KoshianMode.Applier>.() -> Unit
) {
   apply(ButtonConstructor, buildAction)
}
