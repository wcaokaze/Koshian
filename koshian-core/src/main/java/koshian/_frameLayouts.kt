package koshian

import android.content.*
import android.widget.*

object FrameLayoutConstructor : KoshianViewGroupConstructor<FrameLayout, FrameLayout.LayoutParams> {
   override fun instantiate(context: Context) = FrameLayout(context)
   override fun instantiateLayoutParams() = FrameLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
}

inline fun <L, M : KoshianMode>
      Koshian<*, *, L, M>.frameLayout(
            buildAction: ViewGroupBuilder<FrameLayout, L, FrameLayout.LayoutParams, M>.() -> Unit
      ): FrameLayout
{
   return this.invoke(FrameLayoutConstructor, buildAction)
}
