@file:Suppress("UNUSED")
package koshian

import android.content.Context
import android.widget.ImageButton
import kotlin.contracts.*

object ImageButtonConstructor : KoshianViewConstructor<ImageButton> {
   override fun instantiate(context: Context?) = ImageButton(context)
}

/**
 * creates a new ImageButton and adds it into this ViewGroup.
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Creator>.ImageButton(
      buildAction: ViewBuilder<ImageButton, L, KoshianMode.Creator>.() -> Unit
): ImageButton {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return create(ImageButtonConstructor, buildAction)
}

/**
 * creates a new ImageButton with name, and adds it into this ViewGroup.
 *
 * The name can be referenced in [applyKoshian]
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Creator>.ImageButton(
      name: String,
      buildAction: ViewBuilder<ImageButton, L, KoshianMode.Creator>.() -> Unit
): ImageButton {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return create(name, ImageButtonConstructor, buildAction)
}

/**
 * If the next View is a ImageButton, applies Koshian to it.
 *
 * Otherwise, creates a new ImageButton and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Applier>.ImageButton(
      buildAction: ViewBuilder<ImageButton, L, KoshianMode.Applier>.() -> Unit
) {
   apply(ImageButtonConstructor, buildAction)
}

/**
 * Applies Koshian to all ImageButtons that are named the specified in this ViewGroup.
 * If there are no ImageButtons named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Applier>.ImageButton(
      name: String,
      buildAction: ViewBuilder<ImageButton, L, KoshianMode.Applier>.() -> Unit
) {
   apply(name, buildAction)
}
