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
import android.widget.Space
import kotlin.contracts.*

object SpaceConstructor : KoshianViewConstructor<Space, Nothing> {
   override fun instantiate(context: Context?) = Space(context)
   override fun instantiateLayoutParams(): Nothing = throw UnsupportedOperationException()
}

/**
 * creates a new Space and adds it into this ViewGroup.
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams> CreatorParent<L>.Space(
      creatorAction: ViewCreator<Space, L>.() -> Unit
): Space {
   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }
   return create(SpaceConstructor, creatorAction)
}

/**
 * creates a new Space with name, and adds it into this ViewGroup.
 *
 * The name can be referenced in [applyKoshian]
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams> CreatorParent<L>.Space(
      name: String,
      creatorAction: ViewCreator<Space, L>.() -> Unit
): Space {
   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }
   return create(name, SpaceConstructor, creatorAction)
}

/**
 * If the next View is a Space, applies Koshian to it.
 *
 * Otherwise, creates a new Space and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams, S : KoshianStyle>
      ApplierParent<L, S>.Space(
            applierAction: ViewApplier<Space, L, S>.() -> Unit
      )
{
   apply(SpaceConstructor, applierAction)
}

/**
 * If the next View is a Space, applies Koshian to it.
 *
 * Otherwise, creates a new Space and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams, S : KoshianStyle>
      ApplierParent<L, S>.Space(
            styleElement: KoshianStyle.StyleElement<Space>,
            applierAction: ViewApplier<Space, L, S>.() -> Unit
      )
{
   apply(SpaceConstructor, styleElement, applierAction)
}

/**
 * Applies Koshian to all Spaces that are named the specified in this ViewGroup.
 * If there are no Spaces named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams, S : KoshianStyle>
      ApplierParent<L, S>.Space(
            name: String,
            applierAction: ViewApplier<Space, L, S>.() -> Unit
      )
{
   apply(SpaceConstructor, name, applierAction)
}

/**
 * Applies Koshian to all Spaces that are named the specified in this ViewGroup.
 * If there are no Spaces named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams, S : KoshianStyle>
      ApplierParent<L, S>.Space(
            name: String,
            styleElement: KoshianStyle.StyleElement<Space>,
            applierAction: ViewApplier<Space, L, S>.() -> Unit
      )
{
   apply(SpaceConstructor, name, styleElement, applierAction)
}

/**
 * registers a style applier function into this [KoshianStyle].
 *
 * Styles can be applied via [applyKoshian]
 */
@Suppress("FunctionName")
inline fun KoshianStyle.Space(
      crossinline styleAction: ViewStyle<Space>.() -> Unit
): KoshianStyle.StyleElement<Space> {
   return createStyleElement(styleAction)
}
