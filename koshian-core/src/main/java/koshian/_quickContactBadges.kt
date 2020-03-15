@file:Suppress("UNUSED")
package koshian

import android.content.Context
import android.widget.QuickContactBadge
import kotlin.contracts.*

object QuickContactBadgeConstructor : KoshianViewConstructor<QuickContactBadge> {
   override fun instantiate(context: Context?) = QuickContactBadge(context)
}

/**
 * creates a new QuickContactBadge and adds it into this ViewGroup.
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Creator>.QuickContactBadge(
      buildAction: ViewBuilder<QuickContactBadge, L, KoshianMode.Creator>.() -> Unit
): QuickContactBadge {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return create(QuickContactBadgeConstructor, buildAction)
}

/**
 * creates a new QuickContactBadge with name, and adds it into this ViewGroup.
 *
 * The name can be referenced in [applyKoshian]
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Creator>.QuickContactBadge(
      name: String,
      buildAction: ViewBuilder<QuickContactBadge, L, KoshianMode.Creator>.() -> Unit
): QuickContactBadge {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return create(name, QuickContactBadgeConstructor, buildAction)
}

/**
 * If the next View is a QuickContactBadge, applies Koshian to it.
 *
 * Otherwise, creates a new QuickContactBadge and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Applier>.QuickContactBadge(
      buildAction: ViewBuilder<QuickContactBadge, L, KoshianMode.Applier>.() -> Unit
) {
   apply(QuickContactBadgeConstructor, buildAction)
}

/**
 * Applies Koshian to all QuickContactBadges that are named the specified in this ViewGroup.
 * If there are no QuickContactBadges named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Applier>.QuickContactBadge(
      name: String,
      buildAction: ViewBuilder<QuickContactBadge, L, KoshianMode.Applier>.() -> Unit
) {
   apply(name, buildAction)
}
