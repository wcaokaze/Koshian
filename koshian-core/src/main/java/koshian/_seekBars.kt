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
 * creates a new SeekBar with name, and adds it into this ViewGroup.
 *
 * The name can be referenced in [applyKoshian]
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Creator>.SeekBar(
      name: String,
      buildAction: ViewBuilder<SeekBar, L, KoshianMode.Creator>.() -> Unit
): SeekBar {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return create(name, SeekBarConstructor, buildAction)
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

/**
 * Applies Koshian to all SeekBars that are named the specified in this ViewGroup.
 * If there are no SeekBars named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Applier>.SeekBar(
      name: String,
      buildAction: ViewBuilder<SeekBar, L, KoshianMode.Applier>.() -> Unit
) {
   apply(name, buildAction)
}
