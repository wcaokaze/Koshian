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

inline fun <L> KoshianParent<L, KoshianMode.Applier>.textView(
      buildAction: ViewBuilder<TextView, L, KoshianMode.Applier>.() -> Unit
) {
   return apply(buildAction)
}
