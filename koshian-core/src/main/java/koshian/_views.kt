package koshian

import android.content.*
import android.view.*
import kotlin.contracts.*

object ViewConstructor : KoshianViewConstructor<View> {
   override fun instantiate(context: Context?) = View(context)
}

@ExperimentalContracts
inline fun <L> KoshianParent<L, KoshianMode.Creator>.view(
      buildAction: ViewBuilder<View, L, KoshianMode.Creator>.() -> Unit
): View {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return create(ViewConstructor, buildAction)
}

inline fun <L> KoshianParent<L, KoshianMode.Applier>.view(
      buildAction: ViewBuilder<View, L, KoshianMode.Applier>.() -> Unit
) {
   apply(ViewConstructor, buildAction)
}
