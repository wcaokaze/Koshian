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
import android.widget.DatePicker
import kotlin.contracts.*

object DatePickerConstructor : KoshianViewConstructor<DatePicker>(DatePicker::class.java) {
   override fun instantiate(context: Context?) = DatePicker(context)
}

/**
 * creates a new DatePicker and adds it into this ViewGroup.
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Creator>.DatePicker(
      buildAction: ViewBuilder<DatePicker, L, KoshianMode.Creator>.() -> Unit
): DatePicker {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return create(DatePickerConstructor, buildAction)
}

/**
 * creates a new DatePicker with name, and adds it into this ViewGroup.
 *
 * The name can be referenced in [applyKoshian]
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Creator>.DatePicker(
      name: String,
      buildAction: ViewBuilder<DatePicker, L, KoshianMode.Creator>.() -> Unit
): DatePicker {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return create(name, DatePickerConstructor, buildAction)
}

/**
 * If the next View is a DatePicker, applies Koshian to it.
 *
 * Otherwise, creates a new DatePicker and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Applier>.DatePicker(
      buildAction: ViewBuilder<DatePicker, L, KoshianMode.Applier>.() -> Unit
) {
   apply(DatePickerConstructor, buildAction)
}

/**
 * Applies Koshian to all DatePickers that are named the specified in this ViewGroup.
 * If there are no DatePickers named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Applier>.DatePicker(
      name: String,
      buildAction: ViewBuilder<DatePicker, L, KoshianMode.Applier>.() -> Unit
) {
   apply(name, buildAction)
}
