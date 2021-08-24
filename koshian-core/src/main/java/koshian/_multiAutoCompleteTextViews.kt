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
import android.widget.MultiAutoCompleteTextView
import kotlin.contracts.*

object MultiAutoCompleteTextViewConstructor : KoshianViewConstructor<MultiAutoCompleteTextView, Nothing> {
   override fun instantiate(context: Context?) = MultiAutoCompleteTextView(context)
   override fun instantiateLayoutParams(): Nothing = throw UnsupportedOperationException()
}

/**
 * creates a new MultiAutoCompleteTextView and adds it into this ViewGroup.
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams> CreatorParent<L>.MultiAutoCompleteTextView(
      creatorAction: ViewCreator<MultiAutoCompleteTextView, L>.() -> Unit
): MultiAutoCompleteTextView {
   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }
   return create(MultiAutoCompleteTextViewConstructor, creatorAction)
}

/**
 * creates a new MultiAutoCompleteTextView with name, and adds it into this ViewGroup.
 *
 * The name can be referenced in [applyKoshian]
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams> CreatorParent<L>.MultiAutoCompleteTextView(
      name: String,
      creatorAction: ViewCreator<MultiAutoCompleteTextView, L>.() -> Unit
): MultiAutoCompleteTextView {
   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }
   return create(name, MultiAutoCompleteTextViewConstructor, creatorAction)
}

/**
 * If the next View is a MultiAutoCompleteTextView, applies Koshian to it.
 *
 * Otherwise, creates a new MultiAutoCompleteTextView and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams, S : KoshianStyle>
      ApplierParent<L, S>.MultiAutoCompleteTextView(
            applierAction: ViewApplier<MultiAutoCompleteTextView, L, S>.() -> Unit
      )
{
   apply(MultiAutoCompleteTextViewConstructor, applierAction)
}

/**
 * If the next View is a MultiAutoCompleteTextView, applies Koshian to it.
 *
 * Otherwise, creates a new MultiAutoCompleteTextView and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams, S : KoshianStyle>
      ApplierParent<L, S>.MultiAutoCompleteTextView(
            styleElement: KoshianStyle.StyleElement<MultiAutoCompleteTextView>,
            applierAction: ViewApplier<MultiAutoCompleteTextView, L, S>.() -> Unit
      )
{
   apply(MultiAutoCompleteTextViewConstructor, styleElement, applierAction)
}

/**
 * Applies Koshian to all MultiAutoCompleteTextViews that are named the specified in this ViewGroup.
 * If there are no MultiAutoCompleteTextViews named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams, S : KoshianStyle>
      ApplierParent<L, S>.MultiAutoCompleteTextView(
            name: String,
            applierAction: ViewApplier<MultiAutoCompleteTextView, L, S>.() -> Unit
      )
{
   apply(MultiAutoCompleteTextViewConstructor, name, applierAction)
}

/**
 * Applies Koshian to all MultiAutoCompleteTextViews that are named the specified in this ViewGroup.
 * If there are no MultiAutoCompleteTextViews named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams, S : KoshianStyle>
      ApplierParent<L, S>.MultiAutoCompleteTextView(
            name: String,
            styleElement: KoshianStyle.StyleElement<MultiAutoCompleteTextView>,
            applierAction: ViewApplier<MultiAutoCompleteTextView, L, S>.() -> Unit
      )
{
   apply(MultiAutoCompleteTextViewConstructor, name, styleElement, applierAction)
}

/**
 * registers a style applier function into this [KoshianStyle].
 *
 * Styles can be applied via [applyKoshian]
 */
@Suppress("FunctionName")
inline fun KoshianStyle.MultiAutoCompleteTextView(
      crossinline styleAction: ViewStyle<MultiAutoCompleteTextView>.() -> Unit
): KoshianStyle.StyleElement<MultiAutoCompleteTextView> {
   return createStyleElement(styleAction)
}
