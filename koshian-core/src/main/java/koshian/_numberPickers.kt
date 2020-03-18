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
import android.widget.NumberPicker
import kotlin.contracts.*

object NumberPickerConstructor : KoshianViewConstructor<NumberPicker> {
   override fun instantiate(context: Context?) = NumberPicker(context)
}

/**
 * creates a new NumberPicker and adds it into this ViewGroup.
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Creator>.NumberPicker(
      buildAction: ViewBuilder<NumberPicker, L, KoshianMode.Creator>.() -> Unit
): NumberPicker {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return create(NumberPickerConstructor, buildAction)
}

/**
 * creates a new NumberPicker with name, and adds it into this ViewGroup.
 *
 * The name can be referenced in [applyKoshian]
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Creator>.NumberPicker(
      name: String,
      buildAction: ViewBuilder<NumberPicker, L, KoshianMode.Creator>.() -> Unit
): NumberPicker {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return create(name, NumberPickerConstructor, buildAction)
}

/**
 * If the next View is a NumberPicker, applies Koshian to it.
 *
 * Otherwise, creates a new NumberPicker and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Applier>.NumberPicker(
      buildAction: ViewBuilder<NumberPicker, L, KoshianMode.Applier>.() -> Unit
) {
   apply(NumberPickerConstructor, buildAction)
}

/**
 * Applies Koshian to all NumberPickers that are named the specified in this ViewGroup.
 * If there are no NumberPickers named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Applier>.NumberPicker(
      name: String,
      buildAction: ViewBuilder<NumberPicker, L, KoshianMode.Applier>.() -> Unit
) {
   apply(name, buildAction)
}
