package koshian

import android.content.*
import android.view.*
import android.widget.*

object FrameLayoutConstructor : KoshianViewGroupConstructor<FrameLayout, FrameLayout.LayoutParams> {
   override fun instantiate(context: Context) = FrameLayout(context)
   override fun instantiateLayoutParams() = FrameLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
}

inline fun <L> KoshianParent<L, KoshianMode.Creator>.frameLayout(
      buildAction: ViewGroupBuilder<FrameLayout, L, FrameLayout.LayoutParams, KoshianMode.Creator>.() -> Unit
): FrameLayout {
   return create(FrameLayoutConstructor, buildAction)
}

inline fun <L> KoshianParent<L, KoshianMode.Applier>.frameLayout(
      buildAction: ViewGroupBuilder<FrameLayout, L, FrameLayout.LayoutParams, KoshianMode.Applier>.() -> Unit
) {
   apply(FrameLayoutConstructor, buildAction)
}

inline fun applyKoshian(
      view: FrameLayout,
      applyAction: ViewGroupBuilder<FrameLayout, ViewGroup.LayoutParams, FrameLayout.LayoutParams, KoshianMode.Applier>.() -> Unit
) {
   applyKoshian(view, FrameLayoutConstructor, applyAction)
}
