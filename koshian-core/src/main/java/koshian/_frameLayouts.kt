package koshian

import android.widget.*

inline fun <L> KoshianParent<L, KoshianMode.Creator>.frameLayout(
      buildAction: ViewGroupBuilder<FrameLayout, L, FrameLayout.LayoutParams, KoshianMode.Creator>.() -> Unit
): FrameLayout {
   return this(
         ::FrameLayout,
         { FrameLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT) },
         buildAction
   )
}
