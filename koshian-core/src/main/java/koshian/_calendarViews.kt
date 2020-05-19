/*
 * Copyright 2020 wcaokaze
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
inline fun <L> CreatorParent<L>.CalendarView(
      creatorAction: ViewCreator<CalendarView, L>.() -> Unit
): CalendarView {
   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }
   return create(CalendarViewConstructor, creatorAction)
}

/**
 * creates a new CalendarView with name, and adds it into this ViewGroup.
 *
 * The name can be referenced in [applyKoshian]
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> CreatorParent<L>.CalendarView(
      name: String,
      creatorAction: ViewCreator<CalendarView, L>.() -> Unit
): CalendarView {
   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }
   return create(name, CalendarViewConstructor, creatorAction)
}

/**
 * If the next View is a CalendarView, applies Koshian to it.
 *
 * Otherwise, creates a new CalendarView and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L, S : KoshianStyle>
      ApplierParent<L, S>.CalendarView(
            applierAction: ViewApplier<CalendarView, L, S>.() -> Unit
      )
{
   apply(CalendarViewConstructor, applierAction)
}

/**
 * If the next View is a CalendarView, applies Koshian to it.
 *
 * Otherwise, creates a new CalendarView and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L, S : KoshianStyle>
      ApplierParent<L, S>.CalendarView(
            styleElement: KoshianStyle.StyleElement<CalendarView>,
            applierAction: ViewApplier<CalendarView, L, S>.() -> Unit
      )
{
   apply(CalendarViewConstructor, styleElement, applierAction)
}

/**
 * Applies Koshian to all CalendarViews that are named the specified in this ViewGroup.
 * If there are no CalendarViews named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L, S : KoshianStyle>
      ApplierParent<L, S>.CalendarView(
            name: String,
            applierAction: ViewApplier<CalendarView, L, S>.() -> Unit
      )
{
   apply(name, applierAction)
}

/**
 * Applies Koshian to all CalendarViews that are named the specified in this ViewGroup.
 * If there are no CalendarViews named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L, S : KoshianStyle>
      ApplierParent<L, S>.CalendarView(
            name: String,
            styleElement: KoshianStyle.StyleElement<CalendarView>,
            applierAction: ViewApplier<CalendarView, L, S>.() -> Unit
      )
{
   apply(name, styleElement, applierAction)
}

/**
 * registers a style applier function into this [KoshianStyle].
 *
 * Styles can be applied via [applyKoshian]
 */
@Suppress("FunctionName")
inline fun KoshianStyle.CalendarView(
      crossinline styleAction: ViewStyle<CalendarView>.() -> Unit
): KoshianStyle.StyleElement<CalendarView> {
   return createStyleElement(styleAction)
}
