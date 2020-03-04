package koshian

import android.content.*
import android.view.*
import android.widget.*
import kotlin.contracts.*

object FrameLayoutConstructor : KoshianViewGroupConstructor<FrameLayout, FrameLayout.LayoutParams> {
   override fun instantiate(context: Context) = FrameLayout(context)
   override fun instantiateLayoutParams() = FrameLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
}

@ExperimentalContracts
inline fun <L> KoshianParent<L, KoshianMode.Creator>.frameLayout(
      buildAction: ViewGroupBuilder<FrameLayout, L, FrameLayout.LayoutParams, KoshianMode.Creator>.() -> Unit
): FrameLayout {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return create(FrameLayoutConstructor, buildAction)
}

inline fun <L> KoshianParent<L, KoshianMode.Applier>.frameLayout(
      buildAction: ViewGroupBuilder<FrameLayout, L, FrameLayout.LayoutParams, KoshianMode.Applier>.() -> Unit
) {
   apply(FrameLayoutConstructor, buildAction)
}

@ExperimentalContracts
inline fun <R> FrameLayout.addView(
      buildAction: ViewGroupBuilder<FrameLayout, Nothing, FrameLayout.LayoutParams, KoshianMode.Creator>.() -> R
): R {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return addView(FrameLayoutConstructor, buildAction)
}

inline fun FrameLayout.applyKoshian(
      applyAction: ViewGroupBuilder<FrameLayout, ViewGroup.LayoutParams, FrameLayout.LayoutParams, KoshianMode.Applier>.() -> Unit
) {
   applyKoshian(FrameLayoutConstructor, applyAction)
}
