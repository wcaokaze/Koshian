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
import android.widget.ProgressBar
import kotlin.contracts.*

object ProgressBarConstructor : KoshianViewConstructor<ProgressBar> {
   override fun instantiate(context: Context?) = ProgressBar(context)
}

/**
 * creates a new ProgressBar and adds it into this ViewGroup.
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> CreatorParent<L>.ProgressBar(
      creatorAction: ViewCreator<ProgressBar, L>.() -> Unit
): ProgressBar {
   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }
   return create(ProgressBarConstructor, creatorAction)
}

/**
 * creates a new ProgressBar with name, and adds it into this ViewGroup.
 *
 * The name can be referenced in [applyKoshian]
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> CreatorParent<L>.ProgressBar(
      name: String,
      creatorAction: ViewCreator<ProgressBar, L>.() -> Unit
): ProgressBar {
   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }
   return create(name, ProgressBarConstructor, creatorAction)
}

/**
 * If the next View is a ProgressBar, applies Koshian to it.
 *
 * Otherwise, creates a new ProgressBar and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L, S : KoshianStyle>
      ApplierParent<L, S>.ProgressBar(
            applierAction: ViewApplier<ProgressBar, L, S>.() -> Unit
      )
{
   apply(ProgressBarConstructor, applierAction)
}

/**
 * If the next View is a ProgressBar, applies Koshian to it.
 *
 * Otherwise, creates a new ProgressBar and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L, S : KoshianStyle>
      ApplierParent<L, S>.ProgressBar(
            styleElement: KoshianStyle.StyleElement<ProgressBar>,
            applierAction: ViewApplier<ProgressBar, L, S>.() -> Unit
      )
{
   apply(ProgressBarConstructor, styleElement, applierAction)
}

/**
 * Applies Koshian to all ProgressBars that are named the specified in this ViewGroup.
 * If there are no ProgressBars named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L, S : KoshianStyle>
      ApplierParent<L, S>.ProgressBar(
            name: String,
            applierAction: ViewApplier<ProgressBar, L, S>.() -> Unit
      )
{
   apply(name, applierAction)
}

/**
 * Applies Koshian to all ProgressBars that are named the specified in this ViewGroup.
 * If there are no ProgressBars named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L, S : KoshianStyle>
      ApplierParent<L, S>.ProgressBar(
            name: String,
            styleElement: KoshianStyle.StyleElement<ProgressBar>,
            applierAction: ViewApplier<ProgressBar, L, S>.() -> Unit
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
inline fun KoshianStyle.ProgressBar(
      crossinline styleAction: ViewStyle<ProgressBar>.() -> Unit
): KoshianStyle.StyleElement<ProgressBar> {
   return createStyleElement(styleAction)
}
