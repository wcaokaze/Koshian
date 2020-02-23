package koshian

import android.view.*
import android.widget.*

inline fun <L, M : KoshianMode>
      Koshian<ViewManager, *, L, M>.textView(
            buildAction: ViewBuilder<TextView, L, M>.() -> Unit
      ): TextView
{
   return this(
         ::TextView,
         buildAction
   )
}
