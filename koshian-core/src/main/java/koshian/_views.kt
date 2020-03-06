package koshian

import android.content.*
import android.view.*
import kotlin.contracts.*

object ViewConstructor : KoshianViewConstructor<View> {
   override fun instantiate(context: Context?) = View(context)
}

@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Creator>.View(
      buildAction: ViewBuilder<View, L, KoshianMode.Creator>.() -> Unit
): View {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return create(ViewConstructor, buildAction)
}

@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Applier>.View(
      buildAction: ViewBuilder<View, L, KoshianMode.Applier>.() -> Unit
) {
   apply(ViewConstructor, buildAction)
}
