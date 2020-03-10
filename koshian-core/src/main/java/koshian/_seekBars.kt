@file:Suppress("UNUSED")
package koshian

import android.content.Context
import android.widget.SeekBar
import kotlin.contracts.*

object SeekBarConstructor : KoshianViewConstructor<SeekBar> {
   override fun instantiate(context: Context?) = SeekBar(context)
}

/**
 * creates a new SeekBar and adds it into this ViewGroup.
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Creator>.SeekBar(
      buildAction: ViewBuilder<SeekBar, L, KoshianMode.Creator>.() -> Unit
): SeekBar {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return create(SeekBarConstructor, buildAction)
}

/**
 * If the next View is a SeekBar, applies Koshian to it.
 *
 * Otherwise, creates a new SeekBar and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Applier>.SeekBar(
      buildAction: ViewBuilder<SeekBar, L, KoshianMode.Applier>.() -> Unit
) {
   apply(SeekBarConstructor, buildAction)
}
