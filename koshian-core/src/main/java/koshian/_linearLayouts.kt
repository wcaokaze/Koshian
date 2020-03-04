package koshian

import android.content.*
import android.view.*
import android.widget.*
import kotlin.contracts.*

object LinearLayoutConstructor : KoshianViewGroupConstructor<LinearLayout, LinearLayout.LayoutParams> {
   override fun instantiate(context: Context?) = LinearLayout(context)
   override fun instantiateLayoutParams() = LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
}

@ExperimentalContracts
inline fun <L> KoshianParent<L, KoshianMode.Creator>.linearLayout(
      buildAction: ViewGroupBuilder<LinearLayout, L, LinearLayout.LayoutParams, KoshianMode.Creator>.() -> Unit
): LinearLayout {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return create(LinearLayoutConstructor, buildAction)
}

inline fun <L> KoshianParent<L, KoshianMode.Applier>.linearLayout(
      buildAction: ViewGroupBuilder<LinearLayout, L, LinearLayout.LayoutParams, KoshianMode.Applier>.() -> Unit
) {
   apply(LinearLayoutConstructor, buildAction)
}

@ExperimentalContracts
inline fun <R> LinearLayout.addView(
      buildAction: ViewGroupBuilder<LinearLayout, Nothing, LinearLayout.LayoutParams, KoshianMode.Creator>.() -> R
): R {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return addView(LinearLayoutConstructor, buildAction)
}

inline fun LinearLayout.applyKoshian(
      applyAction: ViewGroupBuilder<LinearLayout, ViewGroup.LayoutParams, LinearLayout.LayoutParams, KoshianMode.Applier>.() -> Unit
) {
   applyKoshian(LinearLayoutConstructor, applyAction)
}
