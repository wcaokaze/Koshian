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
import android.widget.DatePicker
import kotlin.contracts.*

object DatePickerConstructor : KoshianViewConstructor<DatePicker, Nothing> {
   override fun instantiate(context: Context?) = DatePicker(context)
   override fun instantiateLayoutParams(): Nothing = throw UnsupportedOperationException()
}

/**
 * creates a new DatePicker and adds it into this ViewGroup.
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams> CreatorParent<L>.DatePicker(
      creatorAction: ViewCreator<DatePicker, L>.() -> Unit
): DatePicker {
   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }
   return create(DatePickerConstructor, creatorAction)
}

/**
 * creates a new DatePicker with name, and adds it into this ViewGroup.
 *
 * The name can be referenced in [applyKoshian]
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams> CreatorParent<L>.DatePicker(
      name: String,
      creatorAction: ViewCreator<DatePicker, L>.() -> Unit
): DatePicker {
   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }
   return create(name, DatePickerConstructor, creatorAction)
}

/**
 * If the next View is a DatePicker, applies Koshian to it.
 *
 * Otherwise, creates a new DatePicker and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams, S : KoshianStyle>
      ApplierParent<L, S>.DatePicker(
            applierAction: ViewApplier<DatePicker, L, S>.() -> Unit
      )
{
   apply(DatePickerConstructor, applierAction)
}

/**
 * If the next View is a DatePicker, applies Koshian to it.
 *
 * Otherwise, creates a new DatePicker and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams, S : KoshianStyle>
      ApplierParent<L, S>.DatePicker(
            styleElement: KoshianStyle.StyleElement<DatePicker>,
            applierAction: ViewApplier<DatePicker, L, S>.() -> Unit
      )
{
   apply(DatePickerConstructor, styleElement, applierAction)
}

/**
 * Applies Koshian to all DatePickers that are named the specified in this ViewGroup.
 * If there are no DatePickers named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams, S : KoshianStyle>
      ApplierParent<L, S>.DatePicker(
            name: String,
            applierAction: ViewApplier<DatePicker, L, S>.() -> Unit
      )
{
   apply(DatePickerConstructor, name, applierAction)
}

/**
 * Applies Koshian to all DatePickers that are named the specified in this ViewGroup.
 * If there are no DatePickers named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams, S : KoshianStyle>
      ApplierParent<L, S>.DatePicker(
            name: String,
            styleElement: KoshianStyle.StyleElement<DatePicker>,
            applierAction: ViewApplier<DatePicker, L, S>.() -> Unit
      )
{
   apply(DatePickerConstructor, name, styleElement, applierAction)
}

/**
 * registers a style applier function into this [KoshianStyle].
 *
 * Styles can be applied via [applyKoshian]
 */
@Suppress("FunctionName")
inline fun KoshianStyle.DatePicker(
      crossinline styleAction: ViewStyle<DatePicker>.() -> Unit
): KoshianStyle.StyleElement<DatePicker> {
   return createStyleElement(styleAction)
}

