package koshian

import android.view.*
import android.widget.*

inline fun <L, M : KoshianMode>
      Koshian<ViewManager, *, L, M>.linearLayout(
            buildAction: ViewGroupBuilder<LinearLayout, L, LinearLayout.LayoutParams, M>.() -> Unit
      ): LinearLayout
{
   return this(
         ::LinearLayout,
         { LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT) },
         buildAction
   )
}
