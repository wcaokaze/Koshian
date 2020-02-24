package koshian

import android.widget.*

inline fun <L> KoshianParent<L, KoshianMode.Creator>.linearLayout(
      buildAction: ViewGroupBuilder<LinearLayout, L, LinearLayout.LayoutParams, KoshianMode.Creator>.() -> Unit
): LinearLayout {
   return create(
         ::LinearLayout,
         { LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT) },
         buildAction
   )
}
