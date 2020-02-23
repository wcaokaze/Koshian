package koshian

import android.widget.*

inline fun <L, M : KoshianMode>
      KoshianParent<L, M>.frameLayout(
            buildAction: ViewGroupBuilder<FrameLayout, L, FrameLayout.LayoutParams, M>.() -> Unit
      ): FrameLayout
{
   return this(
         ::FrameLayout,
         { FrameLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT) },
         buildAction
   )
}
