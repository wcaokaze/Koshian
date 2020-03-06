package koshian

import android.content.*
import android.widget.*
import kotlin.contracts.*

object TextViewConstructor : KoshianViewConstructor<TextView> {
   override fun instantiate(context: Context?) = TextView(context)
}

@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Creator>.TextView(
      buildAction: ViewBuilder<TextView, L, KoshianMode.Creator>.() -> Unit
): TextView {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return create(TextViewConstructor, buildAction)
}

@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Applier>.TextView(
      buildAction: ViewBuilder<TextView, L, KoshianMode.Applier>.() -> Unit
) {
   apply(TextViewConstructor, buildAction)
}
