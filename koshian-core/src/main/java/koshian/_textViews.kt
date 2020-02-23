package koshian

import android.widget.*

inline fun <L> KoshianParent<L, KoshianMode.Creator>.textView(
      buildAction: ViewBuilder<TextView, L, KoshianMode.Creator>.() -> Unit
): TextView {
   return this(
         ::TextView,
         buildAction
   )
}
