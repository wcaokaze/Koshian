package koshian

import android.content.Context
import android.view.View
import kotlin.contracts.*

object ViewConstructor : KoshianViewConstructor<View> {
   override fun instantiate(context: Context?) = View(context)
}

/**
 * creates a new View and adds it into this ViewGroup.
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Creator>.View(
      buildAction: ViewBuilder<View, L, KoshianMode.Creator>.() -> Unit
): View {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return create(ViewConstructor, buildAction)
}

/**
 * If the next View is a View, applies Koshian to it.
 *
 * Otherwise, creates a new View and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Applier>.View(
      buildAction: ViewBuilder<View, L, KoshianMode.Applier>.() -> Unit
) {
   apply(ViewConstructor, buildAction)
}
