@file:Suppress("UNUSED")
package koshian

import android.content.Context
import android.widget.ImageSwitcher
import kotlin.contracts.*

object ImageSwitcherConstructor : KoshianViewConstructor<ImageSwitcher> {
   override fun instantiate(context: Context?) = ImageSwitcher(context)
}

/**
 * creates a new ImageSwitcher and adds it into this ViewGroup.
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Creator>.ImageSwitcher(
      buildAction: ViewBuilder<ImageSwitcher, L, KoshianMode.Creator>.() -> Unit
): ImageSwitcher {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return create(ImageSwitcherConstructor, buildAction)
}

/**
 * creates a new ImageSwitcher with name, and adds it into this ViewGroup.
 *
 * The name can be referenced in [applyKoshian]
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Creator>.ImageSwitcher(
      name: String,
      buildAction: ViewBuilder<ImageSwitcher, L, KoshianMode.Creator>.() -> Unit
): ImageSwitcher {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return create(name, ImageSwitcherConstructor, buildAction)
}

/**
 * If the next View is a ImageSwitcher, applies Koshian to it.
 *
 * Otherwise, creates a new ImageSwitcher and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Applier>.ImageSwitcher(
      buildAction: ViewBuilder<ImageSwitcher, L, KoshianMode.Applier>.() -> Unit
) {
   apply(ImageSwitcherConstructor, buildAction)
}

/**
 * Applies Koshian to all ImageSwitchers that are named the specified in this ViewGroup.
 * If there are no ImageSwitchers named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Applier>.ImageSwitcher(
      name: String,
      buildAction: ViewBuilder<ImageSwitcher, L, KoshianMode.Applier>.() -> Unit
) {
   apply(name, buildAction)
}