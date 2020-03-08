package koshian

import android.content.Context
import android.widget.Chronometer
import kotlin.contracts.*

object ChronometerConstructor : KoshianViewConstructor<Chronometer> {
   override fun instantiate(context: Context?) = Chronometer(context)
}

/**
 * creates a new Chronometer and adds it into this ViewGroup.
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Creator>.Chronometer(
      buildAction: ViewBuilder<Chronometer, L, KoshianMode.Creator>.() -> Unit
): Chronometer {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return create(ChronometerConstructor, buildAction)
}

/**
 * If the next View is a Chronometer, applies Koshian to it.
 *
 * Otherwise, creates a new Chronometer and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Applier>.Chronometer(
      buildAction: ViewBuilder<Chronometer, L, KoshianMode.Applier>.() -> Unit
) {
   apply(ChronometerConstructor, buildAction)
}
