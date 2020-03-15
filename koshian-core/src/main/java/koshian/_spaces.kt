@file:Suppress("UNUSED")
package koshian

import android.content.Context
import android.widget.Space
import kotlin.contracts.*

object SpaceConstructor : KoshianViewConstructor<Space> {
   override fun instantiate(context: Context?) = Space(context)
}

/**
 * creates a new Space and adds it into this ViewGroup.
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Creator>.Space(
      buildAction: ViewBuilder<Space, L, KoshianMode.Creator>.() -> Unit
): Space {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return create(SpaceConstructor, buildAction)
}

/**
 * creates a new Space with name, and adds it into this ViewGroup.
 *
 * The name can be referenced in [applyKoshian]
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Creator>.Space(
      name: String,
      buildAction: ViewBuilder<Space, L, KoshianMode.Creator>.() -> Unit
): Space {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return create(name, SpaceConstructor, buildAction)
}

/**
 * If the next View is a Space, applies Koshian to it.
 *
 * Otherwise, creates a new Space and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Applier>.Space(
      buildAction: ViewBuilder<Space, L, KoshianMode.Applier>.() -> Unit
) {
   apply(SpaceConstructor, buildAction)
}

/**
 * Applies Koshian to all Spaces that are named the specified in this ViewGroup.
 * If there are no Spaces named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Applier>.Space(
      name: String,
      buildAction: ViewBuilder<Space, L, KoshianMode.Applier>.() -> Unit
) {
   apply(name, buildAction)
}
