package koshian

import android.content.Context
import android.widget.ImageView
import kotlin.contracts.*

object ImageViewConstructor : KoshianViewConstructor<ImageView> {
   override fun instantiate(context: Context?) = ImageView(context)
}

/**
 * creates a new ImageView and adds it into this ViewGroup.
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Creator>.ImageView(
      buildAction: ViewBuilder<ImageView, L, KoshianMode.Creator>.() -> Unit
): ImageView {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return create(ImageViewConstructor, buildAction)
}

/**
 * If the next View is a ImageView, applies Koshian to it.
 *
 * Otherwise, creates a new ImageView and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Applier>.ImageView(
      buildAction: ViewBuilder<ImageView, L, KoshianMode.Applier>.() -> Unit
) {
   apply(ImageViewConstructor, buildAction)
}
