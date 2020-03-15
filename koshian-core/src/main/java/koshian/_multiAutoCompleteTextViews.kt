@file:Suppress("UNUSED")
package koshian

import android.content.Context
import android.widget.MultiAutoCompleteTextView
import kotlin.contracts.*

object MultiAutoCompleteTextViewConstructor : KoshianViewConstructor<MultiAutoCompleteTextView> {
   override fun instantiate(context: Context?) = MultiAutoCompleteTextView(context)
}

/**
 * creates a new MultiAutoCompleteTextView and adds it into this ViewGroup.
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Creator>.MultiAutoCompleteTextView(
      buildAction: ViewBuilder<MultiAutoCompleteTextView, L, KoshianMode.Creator>.() -> Unit
): MultiAutoCompleteTextView {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return create(MultiAutoCompleteTextViewConstructor, buildAction)
}

/**
 * creates a new MultiAutoCompleteTextView with name, and adds it into this ViewGroup.
 *
 * The name can be referenced in [applyKoshian]
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Creator>.MultiAutoCompleteTextView(
      name: String,
      buildAction: ViewBuilder<MultiAutoCompleteTextView, L, KoshianMode.Creator>.() -> Unit
): MultiAutoCompleteTextView {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return create(name, MultiAutoCompleteTextViewConstructor, buildAction)
}

/**
 * If the next View is a MultiAutoCompleteTextView, applies Koshian to it.
 *
 * Otherwise, creates a new MultiAutoCompleteTextView and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Applier>.MultiAutoCompleteTextView(
      buildAction: ViewBuilder<MultiAutoCompleteTextView, L, KoshianMode.Applier>.() -> Unit
) {
   apply(MultiAutoCompleteTextViewConstructor, buildAction)
}

/**
 * Applies Koshian to all MultiAutoCompleteTextViews that are named the specified in this ViewGroup.
 * If there are no MultiAutoCompleteTextViews named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Applier>.MultiAutoCompleteTextView(
      name: String,
      buildAction: ViewBuilder<MultiAutoCompleteTextView, L, KoshianMode.Applier>.() -> Unit
) {
   apply(name, buildAction)
}
