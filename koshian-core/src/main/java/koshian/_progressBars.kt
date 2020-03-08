package koshian

import android.content.Context
import android.widget.ProgressBar
import kotlin.contracts.*

object ProgressBarConstructor : KoshianViewConstructor<ProgressBar> {
   override fun instantiate(context: Context?) = ProgressBar(context)
}

/**
 * creates a new ProgressBar and adds it into this ViewGroup.
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Creator>.ProgressBar(
      buildAction: ViewBuilder<ProgressBar, L, KoshianMode.Creator>.() -> Unit
): ProgressBar {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return create(ProgressBarConstructor, buildAction)
}

/**
 * If the next View is a ProgressBar, applies Koshian to it.
 *
 * Otherwise, creates a new ProgressBar and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Applier>.ProgressBar(
      buildAction: ViewBuilder<ProgressBar, L, KoshianMode.Applier>.() -> Unit
) {
   apply(ProgressBarConstructor, buildAction)
}
