package koshian

import android.content.*
import android.view.*
import android.widget.*
import kotlin.contracts.*

object FrameLayoutConstructor : KoshianViewGroupConstructor<FrameLayout, FrameLayout.LayoutParams> {
   override fun instantiate(context: Context) = FrameLayout(context)
   override fun instantiateLayoutParams() = FrameLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
}

/**
 * adds Views into this FrameLayout.
 */
@ExperimentalContracts
inline fun <R> FrameLayout.addView(
      buildAction: ViewGroupBuilder<FrameLayout, Nothing, FrameLayout.LayoutParams, KoshianMode.Creator>.() -> R
): R {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return addView(FrameLayoutConstructor, buildAction)
}

/**
 * creates a new FrameLayout and adds it into this ViewGroup.
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Creator>.FrameLayout(
      buildAction: ViewGroupBuilder<FrameLayout, L, FrameLayout.LayoutParams, KoshianMode.Creator>.() -> Unit
): FrameLayout {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return create(FrameLayoutConstructor, buildAction)
}

/**
 * finds Views that are already added in this FrameLayout,
 * and applies Koshian DSL to them.
 */
inline fun FrameLayout.applyKoshian(
      applyAction: ViewGroupBuilder<FrameLayout, ViewGroup.LayoutParams, FrameLayout.LayoutParams, KoshianMode.Applier>.() -> Unit
) {
   applyKoshian(FrameLayoutConstructor, applyAction)
}

@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Applier>.FrameLayout(
      buildAction: ViewGroupBuilder<FrameLayout, L, FrameLayout.LayoutParams, KoshianMode.Applier>.() -> Unit
) {
   apply(FrameLayoutConstructor, buildAction)
}
