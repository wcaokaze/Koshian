package koshian

import android.content.*
import android.view.*

object ViewConstructor : KoshianViewConstructor<View> {
   override fun instantiate(context: Context?) = View(context)
}

inline fun <L> KoshianParent<L, KoshianMode.Creator>.view(
      buildAction: ViewBuilder<View, L, KoshianMode.Creator>.() -> Unit
): View {
   return create(ViewConstructor, buildAction)
}

inline fun <L> KoshianParent<L, KoshianMode.Applier>.view(
      buildAction: ViewBuilder<View, L, KoshianMode.Applier>.() -> Unit
) {
   apply(ViewConstructor, buildAction)
}
