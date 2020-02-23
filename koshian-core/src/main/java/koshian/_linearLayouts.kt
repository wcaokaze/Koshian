package koshian

import android.content.*
import android.widget.*

object LinearLayoutConstructor : KoshianViewGroupConstructor<LinearLayout, LinearLayout.LayoutParams> {
   override fun instantiate(context: Context?) = LinearLayout(context)
   override fun instantiateLayoutParams() = LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
}

inline fun <L, M : KoshianMode>
      Koshian<*, *, L, M>.linearLayout(
            buildAction: ViewGroupBuilder<LinearLayout, L, LinearLayout.LayoutParams, M>.() -> Unit
      ): LinearLayout
{
   return this(LinearLayoutConstructor, buildAction)
}
