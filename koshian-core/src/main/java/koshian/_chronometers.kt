@file:Suppress("UNUSED")
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
 * creates a new Chronometer with name, and adds it into this ViewGroup.
 *
 * The name can be referenced in [applyKoshian]
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Creator>.Chronometer(
      name: String,
      buildAction: ViewBuilder<Chronometer, L, KoshianMode.Creator>.() -> Unit
): Chronometer {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return create(name, ChronometerConstructor, buildAction)
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

/**
 * Applies Koshian to all Chronometers that are named the specified in this ViewGroup.
 * If there are no Chronometers named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Applier>.Chronometer(
      name: String,
      buildAction: ViewBuilder<Chronometer, L, KoshianMode.Applier>.() -> Unit
) {
   apply(name, buildAction)
}
