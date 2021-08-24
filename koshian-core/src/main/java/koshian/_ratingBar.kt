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
import android.widget.RatingBar
import kotlin.contracts.*

object RatingBarConstructor : KoshianViewConstructor<RatingBar, Nothing> {
   override fun instantiate(context: Context?) = RatingBar(context)
   override fun instantiateLayoutParams(): Nothing = throw UnsupportedOperationException()
}

/**
 * creates a new RatingBar and adds it into this ViewGroup.
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams> CreatorParent<L>.RatingBar(
      creatorAction: ViewCreator<RatingBar, L>.() -> Unit
): RatingBar {
   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }
   return create(RatingBarConstructor, creatorAction)
}

/**
 * creates a new RatingBar with name, and adds it into this ViewGroup.
 *
 * The name can be referenced in [applyKoshian]
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams> CreatorParent<L>.RatingBar(
      name: String,
      creatorAction: ViewCreator<RatingBar, L>.() -> Unit
): RatingBar {
   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }
   return create(name, RatingBarConstructor, creatorAction)
}

/**
 * If the next View is a RatingBar, applies Koshian to it.
 *
 * Otherwise, creates a new RatingBar and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams, S : KoshianStyle>
      ApplierParent<L, S>.RatingBar(
            applierAction: ViewApplier<RatingBar, L, S>.() -> Unit
      )
{
   apply(RatingBarConstructor, applierAction)
}

/**
 * If the next View is a RatingBar, applies Koshian to it.
 *
 * Otherwise, creates a new RatingBar and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams, S : KoshianStyle>
      ApplierParent<L, S>.RatingBar(
            styleElement: KoshianStyle.StyleElement<RatingBar>,
            applierAction: ViewApplier<RatingBar, L, S>.() -> Unit
      )
{
   apply(RatingBarConstructor, styleElement, applierAction)
}

/**
 * Applies Koshian to all RatingBars that are named the specified in this ViewGroup.
 * If there are no RatingBars named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams, S : KoshianStyle>
      ApplierParent<L, S>.RatingBar(
            name: String,
            applierAction: ViewApplier<RatingBar, L, S>.() -> Unit
      )
{
   apply(RatingBarConstructor, name, applierAction)
}

/**
 * Applies Koshian to all RatingBars that are named the specified in this ViewGroup.
 * If there are no RatingBars named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams, S : KoshianStyle>
      ApplierParent<L, S>.RatingBar(
            name: String,
            styleElement: KoshianStyle.StyleElement<RatingBar>,
            applierAction: ViewApplier<RatingBar, L, S>.() -> Unit
      )
{
   apply(RatingBarConstructor, name, styleElement, applierAction)
}

/**
 * registers a style applier function into this [KoshianStyle].
 *
 * Styles can be applied via [applyKoshian]
 */
@Suppress("FunctionName")
inline fun KoshianStyle.RatingBar(
      crossinline styleAction: ViewStyle<RatingBar>.() -> Unit
): KoshianStyle.StyleElement<RatingBar> {
   return createStyleElement(styleAction)
}
