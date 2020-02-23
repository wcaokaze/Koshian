package koshian

import android.widget.*

inline fun <L, M : KoshianMode>
      KoshianParent<L, M>.linearLayout(
            buildAction: ViewGroupBuilder<LinearLayout, L, LinearLayout.LayoutParams, M>.() -> Unit
      ): LinearLayout
{
   return this(
         ::LinearLayout,
         { LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT) },
         buildAction
   )
}
