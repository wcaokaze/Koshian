package koshian

import android.content.Context
import android.widget.EditText
import kotlin.contracts.*

object EditTextConstructor : KoshianViewConstructor<EditText> {
   override fun instantiate(context: Context?) = EditText(context)
}

/**
 * creates a new EditText and adds it into this ViewGroup.
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Creator>.EditText(
      buildAction: ViewBuilder<EditText, L, KoshianMode.Creator>.() -> Unit
): EditText {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return create(EditTextConstructor, buildAction)
}

/**
 * If the next View is a EditText, applies Koshian to it.
 *
 * Otherwise, creates a new EditText and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Applier>.EditText(
      buildAction: ViewBuilder<EditText, L, KoshianMode.Applier>.() -> Unit
) {
   apply(EditTextConstructor, buildAction)
}
