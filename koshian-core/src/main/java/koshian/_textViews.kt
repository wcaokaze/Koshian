package koshian

import android.widget.*

inline fun <L, M : KoshianMode>
      Koshian<*, *, L, M>.textView(
            buildAction: ViewBuilder<TextView, L, M>.() -> Unit
      ): TextView
{
   return this(KoshianViewConstructor(::TextView), buildAction)
}
