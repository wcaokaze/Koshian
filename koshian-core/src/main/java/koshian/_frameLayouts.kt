package koshian

import android.content.*
import android.widget.*

object FrameLayoutConstructor : KoshianViewGroupConstructor<FrameLayout, FrameLayout.LayoutParams> {
   override fun instantiate(context: Context) = FrameLayout(context)
   override fun instantiateLayoutParams() = FrameLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
}

inline val KoshianParent.frameLayout get() = FrameLayoutConstructor
