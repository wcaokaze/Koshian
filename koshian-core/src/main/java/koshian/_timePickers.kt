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
import android.view.ViewGroup
import android.widget.TimePicker
import kotlin.contracts.*

object TimePickerConstructor : KoshianViewConstructor<TimePicker, Nothing> {
   override fun instantiate(context: Context?) = TimePicker(context)
   override fun instantiateLayoutParams(): Nothing = throw UnsupportedOperationException()
}

/**
 * creates a new TimePicker and adds it into this ViewGroup.
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams> CreatorParent<L>.TimePicker(
      creatorAction: ViewCreator<TimePicker, L>.() -> Unit
): TimePicker {
   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }
   return create(TimePickerConstructor, creatorAction)
}

/**
 * creates a new TimePicker with name, and adds it into this ViewGroup.
 *
 * The name can be referenced in [applyKoshian]
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams> CreatorParent<L>.TimePicker(
      name: String,
      creatorAction: ViewCreator<TimePicker, L>.() -> Unit
): TimePicker {
   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }
   return create(name, TimePickerConstructor, creatorAction)
}

/**
 * If the next View is a TimePicker, applies Koshian to it.
 *
 * Otherwise, creates a new TimePicker and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams, S : KoshianStyle>
      ApplierParent<L, S>.TimePicker(
            applierAction: ViewApplier<TimePicker, L, S>.() -> Unit
      )
{
   apply(TimePickerConstructor, applierAction)
}

/**
 * If the next View is a TimePicker, applies Koshian to it.
 *
 * Otherwise, creates a new TimePicker and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams, S : KoshianStyle>
      ApplierParent<L, S>.TimePicker(
            styleElement: KoshianStyle.StyleElement<TimePicker>,
            applierAction: ViewApplier<TimePicker, L, S>.() -> Unit
      )
{
   apply(TimePickerConstructor, styleElement, applierAction)
}

/**
 * Applies Koshian to all TimePickers that are named the specified in this ViewGroup.
 * If there are no TimePickers named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams, S : KoshianStyle>
      ApplierParent<L, S>.TimePicker(
            name: String,
            applierAction: ViewApplier<TimePicker, L, S>.() -> Unit
      )
{
   apply(TimePickerConstructor, name, applierAction)
}

/**
 * Applies Koshian to all TimePickers that are named the specified in this ViewGroup.
 * If there are no TimePickers named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams, S : KoshianStyle>
      ApplierParent<L, S>.TimePicker(
            name: String,
            styleElement: KoshianStyle.StyleElement<TimePicker>,
            applierAction: ViewApplier<TimePicker, L, S>.() -> Unit
      )
{
   apply(TimePickerConstructor, name, styleElement, applierAction)
}

/**
 * registers a style applier function into this [KoshianStyle].
 *
 * Styles can be applied via [applyKoshian]
 */
@Suppress("FunctionName")
inline fun KoshianStyle.TimePicker(
      crossinline styleAction: ViewStyle<TimePicker>.() -> Unit
): KoshianStyle.StyleElement<TimePicker> {
   return createStyleElement(styleAction)
}
