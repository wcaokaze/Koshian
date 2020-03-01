package koshian

import android.content.*
import android.widget.*

object LinearLayoutConstructor : KoshianViewGroupConstructor<LinearLayout, LinearLayout.LayoutParams> {
   override fun instantiate(context: Context?) = LinearLayout(context)
   override fun instantiateLayoutParams() = LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
}

inline fun <L> KoshianParent<L, KoshianMode.Creator>.linearLayout(
      buildAction: ViewGroupBuilder<LinearLayout, L, LinearLayout.LayoutParams, KoshianMode.Creator>.() -> Unit
): LinearLayout {
   return create(LinearLayoutConstructor, buildAction)
}

inline fun <L> KoshianParent<L, KoshianMode.Applier>.linearLayout(
      buildAction: ViewGroupBuilder<LinearLayout, L, LinearLayout.LayoutParams, KoshianMode.Applier>.() -> Unit
) {
   return apply(LinearLayoutConstructor, buildAction)
}
