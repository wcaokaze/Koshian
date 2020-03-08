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
