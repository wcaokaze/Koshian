package koshian

import android.view.*
import android.widget.*

inline fun <L, M : KoshianMode>
      Koshian<ViewManager, *, L, M>.frameLayout(
            buildAction: ViewGroupBuilder<FrameLayout, L, FrameLayout.LayoutParams, M>.() -> Unit
      ): FrameLayout
{
   return this(
         ::FrameLayout,
         { FrameLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT) },
         buildAction
   )
}
