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
import android.widget.EditText
import kotlin.contracts.*

object EditTextConstructor : KoshianViewConstructor<EditText, Nothing> {
   override fun instantiate(context: Context?) = EditText(context)
   override fun instantiateLayoutParams(): Nothing = throw UnsupportedOperationException()
}

/**
 * creates a new EditText and adds it into this ViewGroup.
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams> CreatorParent<L>.EditText(
      creatorAction: ViewCreator<EditText, L>.() -> Unit
): EditText {
   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }
   return create(EditTextConstructor, creatorAction)
}

/**
 * creates a new EditText with name, and adds it into this ViewGroup.
 *
 * The name can be referenced in [applyKoshian]
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams> CreatorParent<L>.EditText(
      name: String,
      creatorAction: ViewCreator<EditText, L>.() -> Unit
): EditText {
   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }
   return create(name, EditTextConstructor, creatorAction)
}

/**
 * If the next View is a EditText, applies Koshian to it.
 *
 * Otherwise, creates a new EditText and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams, S : KoshianStyle>
      ApplierParent<L, S>.EditText(
            applierAction: ViewApplier<EditText, L, S>.() -> Unit
      )
{
   apply(EditTextConstructor, applierAction)
}

/**
 * If the next View is a EditText, applies Koshian to it.
 *
 * Otherwise, creates a new EditText and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams, S : KoshianStyle>
      ApplierParent<L, S>.EditText(
            styleElement: KoshianStyle.StyleElement<EditText>,
            applierAction: ViewApplier<EditText, L, S>.() -> Unit
      )
{
   apply(EditTextConstructor, styleElement, applierAction)
}

/**
 * Applies Koshian to all EditTexts that are named the specified in this ViewGroup.
 * If there are no EditTexts named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams, S : KoshianStyle>
      ApplierParent<L, S>.EditText(
            name: String,
            applierAction: ViewApplier<EditText, L, S>.() -> Unit
      )
{
   apply(EditTextConstructor, name, applierAction)
}

/**
 * Applies Koshian to all EditTexts that are named the specified in this ViewGroup.
 * If there are no EditTexts named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams, S : KoshianStyle>
      ApplierParent<L, S>.EditText(
            name: String,
            styleElement: KoshianStyle.StyleElement<EditText>,
            applierAction: ViewApplier<EditText, L, S>.() -> Unit
      )
{
   apply(EditTextConstructor, name, styleElement, applierAction)
}

/**
 * registers a style applier function into this [KoshianStyle].
 *
 * Styles can be applied via [applyKoshian]
 */
@Suppress("FunctionName")
inline fun KoshianStyle.EditText(
      crossinline styleAction: ViewStyle<EditText>.() -> Unit
): KoshianStyle.StyleElement<EditText> {
   return createStyleElement(styleAction)
}
