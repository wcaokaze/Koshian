@file:Suppress("UNUSED")
package koshian

import android.content.Context
import android.widget.Toolbar
import androidx.annotation.RequiresApi
import kotlin.contracts.*

@RequiresApi(21)
object ToolbarConstructor : KoshianViewConstructor<Toolbar> {
   override fun instantiate(context: Context?) = Toolbar(context)
}

/**
 * creates a new Toolbar and adds it into this ViewGroup.
 */
@ExperimentalContracts
@RequiresApi(21)
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Creator>.Toolbar(
      buildAction: ViewBuilder<Toolbar, L, KoshianMode.Creator>.() -> Unit
): Toolbar {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return create(ToolbarConstructor, buildAction)
}

/**
 * If the next View is a Toolbar, applies Koshian to it.
 *
 * Otherwise, creates a new Toolbar and inserts it to the current position.
 *
 * @see applyKoshian
 */
@RequiresApi(21)
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Applier>.Toolbar(
      buildAction: ViewBuilder<Toolbar, L, KoshianMode.Applier>.() -> Unit
) {
   apply(ToolbarConstructor, buildAction)
}
