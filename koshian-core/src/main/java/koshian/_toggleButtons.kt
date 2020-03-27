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
import android.widget.ToggleButton
import kotlin.contracts.*

object ToggleButtonConstructor : KoshianViewConstructor<ToggleButton> {
   override fun instantiate(context: Context?) = ToggleButton(context)
}

/**
 * creates a new ToggleButton and adds it into this ViewGroup.
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> CreatorParent<L>.ToggleButton(
      creatorAction: ViewCreator<ToggleButton, L>.() -> Unit
): ToggleButton {
   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }
   return create(ToggleButtonConstructor, creatorAction)
}

/**
 * creates a new ToggleButton with name, and adds it into this ViewGroup.
 *
 * The name can be referenced in [applyKoshian]
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> CreatorParent<L>.ToggleButton(
      name: String,
      creatorAction: ViewCreator<ToggleButton, L>.() -> Unit
): ToggleButton {
   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }
   return create(name, ToggleButtonConstructor, creatorAction)
}

/**
 * If the next View is a ToggleButton, applies Koshian to it.
 *
 * Otherwise, creates a new ToggleButton and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L, S : KoshianStyle>
      ApplierParent<L, S>.ToggleButton(
            applierAction: ViewApplier<ToggleButton, L, S>.() -> Unit
      )
{
   apply(ToggleButtonConstructor, applierAction)
}

/**
 * If the next View is a ToggleButton, applies Koshian to it.
 *
 * Otherwise, creates a new ToggleButton and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L, S : KoshianStyle>
      ApplierParent<L, S>.ToggleButton(
            styleElement: KoshianStyle.StyleElement<ToggleButton>,
            applierAction: ViewApplier<ToggleButton, L, S>.() -> Unit
      )
{
   apply(ToggleButtonConstructor, styleElement, applierAction)
}

/**
 * Applies Koshian to all ToggleButtons that are named the specified in this ViewGroup.
 * If there are no ToggleButtons named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L, S : KoshianStyle>
      ApplierParent<L, S>.ToggleButton(
            name: String,
            applierAction: ViewApplier<ToggleButton, L, S>.() -> Unit
      )
{
   apply(name, applierAction)
}

/**
 * Applies Koshian to all ToggleButtons that are named the specified in this ViewGroup.
 * If there are no ToggleButtons named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L, S : KoshianStyle>
      ApplierParent<L, S>.ToggleButton(
            name: String,
            styleElement: KoshianStyle.StyleElement<ToggleButton>,
            applierAction: ViewApplier<ToggleButton, L, S>.() -> Unit
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
inline fun KoshianStyle.ToggleButton(
      crossinline styleAction: ViewStyle<ToggleButton>.() -> Unit
): KoshianStyle.StyleElement<ToggleButton> {
   return createStyleElement(styleAction)
}
