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
import android.widget.Switch
import kotlin.contracts.*

object SwitchConstructor : KoshianViewConstructor<Switch> {
   override fun instantiate(context: Context?) = Switch(context)
}

/**
 * creates a new Switch and adds it into this ViewGroup.
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> CreatorParent<L>.Switch(
      creatorAction: ViewCreator<Switch, L>.() -> Unit
): Switch {
   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }
   return create(SwitchConstructor, creatorAction)
}

/**
 * creates a new Switch with name, and adds it into this ViewGroup.
 *
 * The name can be referenced in [applyKoshian]
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> CreatorParent<L>.Switch(
      name: String,
      creatorAction: ViewCreator<Switch, L>.() -> Unit
): Switch {
   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }
   return create(name, SwitchConstructor, creatorAction)
}

/**
 * If the next View is a Switch, applies Koshian to it.
 *
 * Otherwise, creates a new Switch and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L, S : KoshianStyle>
      ApplierParent<L, S>.Switch(
            applierAction: ViewApplier<Switch, L, S>.() -> Unit
      )
{
   apply(SwitchConstructor, applierAction)
}

/**
 * If the next View is a Switch, applies Koshian to it.
 *
 * Otherwise, creates a new Switch and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L, S : KoshianStyle>
      ApplierParent<L, S>.Switch(
            styleElement: KoshianStyle.StyleElement<Switch>,
            applierAction: ViewApplier<Switch, L, S>.() -> Unit
      )
{
   apply(SwitchConstructor, styleElement, applierAction)
}

/**
 * Applies Koshian to all Switchs that are named the specified in this ViewGroup.
 * If there are no Switchs named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L, S : KoshianStyle>
      ApplierParent<L, S>.Switch(
            name: String,
            applierAction: ViewApplier<Switch, L, S>.() -> Unit
      )
{
   apply(name, applierAction)
}

/**
 * Applies Koshian to all Switchs that are named the specified in this ViewGroup.
 * If there are no Switchs named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L, S : KoshianStyle>
      ApplierParent<L, S>.Switch(
            name: String,
            styleElement: KoshianStyle.StyleElement<Switch>,
            applierAction: ViewApplier<Switch, L, S>.() -> Unit
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
inline fun KoshianStyle.Switch(
      crossinline styleAction: ViewStyle<Switch>.() -> Unit
): KoshianStyle.StyleElement<Switch> {
   return createStyleElement(styleAction)
}
