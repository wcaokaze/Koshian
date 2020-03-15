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
 * creates a new Toolbar with name, and adds it into this ViewGroup.
 *
 * The name can be referenced in [applyKoshian]
 */
@ExperimentalContracts
@RequiresApi(21)
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Creator>.Toolbar(
      name: String,
      buildAction: ViewBuilder<Toolbar, L, KoshianMode.Creator>.() -> Unit
): Toolbar {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return create(name, ToolbarConstructor, buildAction)
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

/**
 * Applies Koshian to all Toolbars that are named the specified in this ViewGroup.
 * If there are no Toolbars named the specified, do nothing.
 *
 * @see applyKoshian
 */
@RequiresApi(21)
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Applier>.Toolbar(
      name: String,
      buildAction: ViewBuilder<Toolbar, L, KoshianMode.Applier>.() -> Unit
) {
   apply(name, buildAction)
}


var Toolbar.titleTextColor: Int
   @Deprecated(message = "The getter always throws an Exception", level = DeprecationLevel.ERROR)
   get() = throw UnsupportedOperationException()
   @RequiresApi(21)
   inline set(value) = setTitleTextColor(value)
