package koshian

import android.content.*
import android.widget.*

object TextViewConstructor : KoshianViewConstructor<TextView> {
   override fun instantiate(context: Context?) = TextView(context)
}

inline fun <L> KoshianParent<L, KoshianMode.Creator>.textView(
      buildAction: ViewBuilder<TextView, L, KoshianMode.Creator>.() -> Unit
): TextView {
   return create(TextViewConstructor, buildAction)
}
