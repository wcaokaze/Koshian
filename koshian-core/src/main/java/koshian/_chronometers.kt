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
import android.widget.Chronometer
import kotlin.contracts.*

object ChronometerConstructor : KoshianViewConstructor<Chronometer, Nothing> {
   override fun instantiate(context: Context?) = Chronometer(context)
   override fun instantiateLayoutParams(): Nothing = throw UnsupportedOperationException()
}

/**
 * creates a new Chronometer and adds it into this ViewGroup.
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams> CreatorParent<L>.Chronometer(
      creatorAction: ViewCreator<Chronometer, L>.() -> Unit
): Chronometer {
   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }
   return create(ChronometerConstructor, creatorAction)
}

/**
 * creates a new Chronometer with name, and adds it into this ViewGroup.
 *
 * The name can be referenced in [applyKoshian]
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams> CreatorParent<L>.Chronometer(
      name: String,
      creatorAction: ViewCreator<Chronometer, L>.() -> Unit
): Chronometer {
   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }
   return create(name, ChronometerConstructor, creatorAction)
}

/**
 * If the next View is a Chronometer, applies Koshian to it.
 *
 * Otherwise, creates a new Chronometer and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams, S : KoshianStyle>
      ApplierParent<L, S>.Chronometer(
            applierAction: ViewApplier<Chronometer, L, S>.() -> Unit
      )
{
   apply(ChronometerConstructor, applierAction)
}

/**
 * If the next View is a Chronometer, applies Koshian to it.
 *
 * Otherwise, creates a new Chronometer and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams, S : KoshianStyle>
      ApplierParent<L, S>.Chronometer(
            styleElement: KoshianStyle.StyleElement<Chronometer>,
            applierAction: ViewApplier<Chronometer, L, S>.() -> Unit
      )
{
   apply(ChronometerConstructor, styleElement, applierAction)
}

/**
 * Applies Koshian to all Chronometers that are named the specified in this ViewGroup.
 * If there are no Chronometers named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams, S : KoshianStyle>
      ApplierParent<L, S>.Chronometer(
            name: String,
            applierAction: ViewApplier<Chronometer, L, S>.() -> Unit
      )
{
   apply(ChronometerConstructor, name, applierAction)
}

/**
 * Applies Koshian to all Chronometers that are named the specified in this ViewGroup.
 * If there are no Chronometers named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams, S : KoshianStyle>
      ApplierParent<L, S>.Chronometer(
            name: String,
            styleElement: KoshianStyle.StyleElement<Chronometer>,
            applierAction: ViewApplier<Chronometer, L, S>.() -> Unit
      )
{
   apply(ChronometerConstructor, name, styleElement, applierAction)
}

/**
 * registers a style applier function into this [KoshianStyle].
 *
 * Styles can be applied via [applyKoshian]
 */
@Suppress("FunctionName")
inline fun KoshianStyle.Chronometer(
      crossinline styleAction: ViewStyle<Chronometer>.() -> Unit
): KoshianStyle.StyleElement<Chronometer> {
   return createStyleElement(styleAction)
}
