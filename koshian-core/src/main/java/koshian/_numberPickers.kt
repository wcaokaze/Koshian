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
import android.widget.NumberPicker
import kotlin.contracts.*

object NumberPickerConstructor : KoshianViewConstructor<NumberPicker, Nothing> {
   override fun instantiate(context: Context?) = NumberPicker(context)
   override fun instantiateLayoutParams(): Nothing = throw UnsupportedOperationException()
}

/**
 * creates a new NumberPicker and adds it into this ViewGroup.
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams> CreatorParent<L>.NumberPicker(
      creatorAction: ViewCreator<NumberPicker, L>.() -> Unit
): NumberPicker {
   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }
   return create(NumberPickerConstructor, creatorAction)
}

/**
 * creates a new NumberPicker with name, and adds it into this ViewGroup.
 *
 * The name can be referenced in [applyKoshian]
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams> CreatorParent<L>.NumberPicker(
      name: String,
      creatorAction: ViewCreator<NumberPicker, L>.() -> Unit
): NumberPicker {
   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }
   return create(name, NumberPickerConstructor, creatorAction)
}

/**
 * If the next View is a NumberPicker, applies Koshian to it.
 *
 * Otherwise, creates a new NumberPicker and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams, S : KoshianStyle>
      ApplierParent<L, S>.NumberPicker(
            applierAction: ViewApplier<NumberPicker, L, S>.() -> Unit
      )
{
   apply(NumberPickerConstructor, applierAction)
}

/**
 * If the next View is a NumberPicker, applies Koshian to it.
 *
 * Otherwise, creates a new NumberPicker and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams, S : KoshianStyle>
      ApplierParent<L, S>.NumberPicker(
            styleElement: KoshianStyle.StyleElement<NumberPicker>,
            applierAction: ViewApplier<NumberPicker, L, S>.() -> Unit
      )
{
   apply(NumberPickerConstructor, styleElement, applierAction)
}

/**
 * Applies Koshian to all NumberPickers that are named the specified in this ViewGroup.
 * If there are no NumberPickers named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams, S : KoshianStyle>
      ApplierParent<L, S>.NumberPicker(
            name: String,
            applierAction: ViewApplier<NumberPicker, L, S>.() -> Unit
      )
{
   apply(NumberPickerConstructor, name, applierAction)
}

/**
 * Applies Koshian to all NumberPickers that are named the specified in this ViewGroup.
 * If there are no NumberPickers named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams, S : KoshianStyle>
      ApplierParent<L, S>.NumberPicker(
            name: String,
            styleElement: KoshianStyle.StyleElement<NumberPicker>,
            applierAction: ViewApplier<NumberPicker, L, S>.() -> Unit
      )
{
   apply(NumberPickerConstructor, name, styleElement, applierAction)
}

/**
 * registers a style applier function into this [KoshianStyle].
 *
 * Styles can be applied via [applyKoshian]
 */
@Suppress("FunctionName")
inline fun KoshianStyle.NumberPicker(
      crossinline styleAction: ViewStyle<NumberPicker>.() -> Unit
): KoshianStyle.StyleElement<NumberPicker> {
   return createStyleElement(styleAction)
}
