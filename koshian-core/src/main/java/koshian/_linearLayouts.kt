package koshian

import android.content.*
import android.view.*
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
   apply(LinearLayoutConstructor, buildAction)
}

inline fun <R> LinearLayout.addView(
      buildAction: ViewGroupBuilder<LinearLayout, Nothing, LinearLayout.LayoutParams, KoshianMode.Creator>.() -> R
): R {
   return addView(LinearLayoutConstructor, buildAction)
}

inline fun LinearLayout.applyKoshian(
      applyAction: ViewGroupBuilder<LinearLayout, ViewGroup.LayoutParams, LinearLayout.LayoutParams, KoshianMode.Applier>.() -> Unit
) {
   applyKoshian(LinearLayoutConstructor, applyAction)
}
