package koshian

import android.content.*
import android.view.*
import android.widget.*

object LinearLayoutConstructor : KoshianViewParentConstructor<LinearLayout, LinearLayout.LayoutParams> {
   override fun instantiate(context: Context?) = LinearLayout(context)
   override fun instantiateLayoutParams() = LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
}

inline val Koshian<ViewManager, *, *>.linearLayout get() = LinearLayoutConstructor
