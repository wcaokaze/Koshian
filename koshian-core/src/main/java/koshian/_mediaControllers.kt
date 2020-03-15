@file:Suppress("UNUSED")
package koshian

import android.content.Context
import android.widget.MediaController
import kotlin.contracts.*

object MediaControllerConstructor : KoshianViewConstructor<MediaController> {
   override fun instantiate(context: Context?) = MediaController(context)
}

/**
 * creates a new MediaController and adds it into this ViewGroup.
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Creator>.MediaController(
      buildAction: ViewBuilder<MediaController, L, KoshianMode.Creator>.() -> Unit
): MediaController {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return create(MediaControllerConstructor, buildAction)
}

/**
 * creates a new MediaController with name, and adds it into this ViewGroup.
 *
 * The name can be referenced in [applyKoshian]
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Creator>.MediaController(
      name: String,
      buildAction: ViewBuilder<MediaController, L, KoshianMode.Creator>.() -> Unit
): MediaController {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return create(name, MediaControllerConstructor, buildAction)
}

/**
 * If the next View is a MediaController, applies Koshian to it.
 *
 * Otherwise, creates a new MediaController and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Applier>.MediaController(
      buildAction: ViewBuilder<MediaController, L, KoshianMode.Applier>.() -> Unit
) {
   apply(MediaControllerConstructor, buildAction)
}

/**
 * Applies Koshian to all MediaControllers that are named the specified in this ViewGroup.
 * If there are no MediaControllers named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Applier>.MediaController(
      name: String,
      buildAction: ViewBuilder<MediaController, L, KoshianMode.Applier>.() -> Unit
) {
   apply(name, buildAction)
}