@file:Suppress("UNUSED")
package koshian

import android.content.Context
import android.widget.RatingBar
import kotlin.contracts.*

object RatingBarConstructor : KoshianViewConstructor<RatingBar> {
   override fun instantiate(context: Context?) = RatingBar(context)
}

/**
 * creates a new RatingBar and adds it into this ViewGroup.
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Creator>.RatingBar(
      buildAction: ViewBuilder<RatingBar, L, KoshianMode.Creator>.() -> Unit
): RatingBar {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return create(RatingBarConstructor, buildAction)
}

/**
 * creates a new RatingBar with name, and adds it into this ViewGroup.
 *
 * The name can be referenced in [applyKoshian]
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Creator>.RatingBar(
      name: String,
      buildAction: ViewBuilder<RatingBar, L, KoshianMode.Creator>.() -> Unit
): RatingBar {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return create(name, RatingBarConstructor, buildAction)
}

/**
 * If the next View is a RatingBar, applies Koshian to it.
 *
 * Otherwise, creates a new RatingBar and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Applier>.RatingBar(
      buildAction: ViewBuilder<RatingBar, L, KoshianMode.Applier>.() -> Unit
) {
   apply(RatingBarConstructor, buildAction)
}

/**
 * Applies Koshian to all RatingBars that are named the specified in this ViewGroup.
 * If there are no RatingBars named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Applier>.RatingBar(
      name: String,
      buildAction: ViewBuilder<RatingBar, L, KoshianMode.Applier>.() -> Unit
) {
   apply(name, buildAction)
}