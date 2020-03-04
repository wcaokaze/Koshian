package koshian

import android.content.*
import android.widget.*
import kotlin.contracts.*

object TextViewConstructor : KoshianViewConstructor<TextView> {
   override fun instantiate(context: Context?) = TextView(context)
}

@ExperimentalContracts
inline fun <L> KoshianParent<L, KoshianMode.Creator>.textView(
      buildAction: ViewBuilder<TextView, L, KoshianMode.Creator>.() -> Unit
): TextView {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return create(TextViewConstructor, buildAction)
}

inline fun <L> KoshianParent<L, KoshianMode.Applier>.textView(
      buildAction: ViewBuilder<TextView, L, KoshianMode.Applier>.() -> Unit
) {
   apply(TextViewConstructor, buildAction)
}
