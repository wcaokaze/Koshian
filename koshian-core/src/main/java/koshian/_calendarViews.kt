@file:Suppress("UNUSED")
package koshian

import android.content.Context
import android.widget.CalendarView
import kotlin.contracts.*

object CalendarViewConstructor : KoshianViewConstructor<CalendarView> {
   override fun instantiate(context: Context) = CalendarView(context)
}

/**
 * creates a new CalendarView and adds it into this ViewGroup.
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Creator>.CalendarView(
      buildAction: ViewBuilder<CalendarView, L, KoshianMode.Creator>.() -> Unit
): CalendarView {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return create(CalendarViewConstructor, buildAction)
}

/**
 * creates a new CalendarView with name, and adds it into this ViewGroup.
 *
 * The name can be referenced in [applyKoshian]
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Creator>.CalendarView(
      name: String,
      buildAction: ViewBuilder<CalendarView, L, KoshianMode.Creator>.() -> Unit
): CalendarView {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return create(name, CalendarViewConstructor, buildAction)
}

/**
 * If the next View is a CalendarView, applies Koshian to it.
 *
 * Otherwise, creates a new CalendarView and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Applier>.CalendarView(
      buildAction: ViewBuilder<CalendarView, L, KoshianMode.Applier>.() -> Unit
) {
   apply(CalendarViewConstructor, buildAction)
}

/**
 * Applies Koshian to all CalendarViews that are named the specified in this ViewGroup.
 * If there are no CalendarViews named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Applier>.CalendarView(
      name: String,
      buildAction: ViewBuilder<CalendarView, L, KoshianMode.Applier>.() -> Unit
) {
   apply(name, buildAction)
}
