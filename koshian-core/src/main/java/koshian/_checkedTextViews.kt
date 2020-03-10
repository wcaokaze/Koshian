@file:Suppress("UNUSED")
package koshian

import android.content.Context
import android.widget.CheckedTextView
import kotlin.contracts.*

object CheckedTextViewConstructor : KoshianViewConstructor<CheckedTextView> {
   override fun instantiate(context: Context?) = CheckedTextView(context)
}

/**
 * creates a new CheckedTextView and adds it into this ViewGroup.
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Creator>.CheckedTextView(
      buildAction: ViewBuilder<CheckedTextView, L, KoshianMode.Creator>.() -> Unit
): CheckedTextView {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return create(CheckedTextViewConstructor, buildAction)
}

/**
 * If the next View is a CheckedTextView, applies Koshian to it.
 *
 * Otherwise, creates a new CheckedTextView and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Applier>.CheckedTextView(
      buildAction: ViewBuilder<CheckedTextView, L, KoshianMode.Applier>.() -> Unit
) {
   apply(CheckedTextViewConstructor, buildAction)
}
