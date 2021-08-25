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
import android.widget.ImageButton
import kotlin.contracts.*

object ImageButtonConstructor : KoshianViewConstructor<ImageButton, Nothing> {
   override fun instantiate(context: Context?) = ImageButton(context)
   override fun instantiateLayoutParams(): Nothing = throw UnsupportedOperationException()
}

/**
 * creates a new ImageButton and adds it into this ViewGroup.
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams> CreatorParent<L>.ImageButton(
      creatorAction: ViewCreator<ImageButton, L>.() -> Unit
): ImageButton {
   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }
   return create(ImageButtonConstructor, creatorAction)
}

/**
 * creates a new ImageButton with name, and adds it into this ViewGroup.
 *
 * The name can be referenced in [applyKoshian]
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams> CreatorParent<L>.ImageButton(
      name: String,
      creatorAction: ViewCreator<ImageButton, L>.() -> Unit
): ImageButton {
   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }
   return create(name, ImageButtonConstructor, creatorAction)
}

/**
 * If the next View is a ImageButton, applies Koshian to it.
 *
 * Otherwise, creates a new ImageButton and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams, S : KoshianStyle>
      ApplierParent<L, S>.ImageButton(
            applierAction: ViewApplier<ImageButton, L, S>.() -> Unit
      )
{
   apply(ImageButtonConstructor, applierAction)
}

/**
 * If the next View is a ImageButton, applies Koshian to it.
 *
 * Otherwise, creates a new ImageButton and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams, S : KoshianStyle>
      ApplierParent<L, S>.ImageButton(
            styleElement: KoshianStyle.StyleElement<ImageButton>,
            applierAction: ViewApplier<ImageButton, L, S>.() -> Unit
      )
{
   apply(ImageButtonConstructor, styleElement, applierAction)
}

/**
 * Applies Koshian to all ImageButtons that are named the specified in this ViewGroup.
 * If there are no ImageButtons named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams, S : KoshianStyle>
      ApplierParent<L, S>.ImageButton(
            name: String,
            applierAction: ViewApplier<ImageButton, L, S>.() -> Unit
      )
{
   apply(ImageButtonConstructor, name, applierAction)
}

/**
 * Applies Koshian to all ImageButtons that are named the specified in this ViewGroup.
 * If there are no ImageButtons named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams, S : KoshianStyle>
      ApplierParent<L, S>.ImageButton(
            name: String,
            styleElement: KoshianStyle.StyleElement<ImageButton>,
            applierAction: ViewApplier<ImageButton, L, S>.() -> Unit
      )
{
   apply(ImageButtonConstructor, name, styleElement, applierAction)
}

/**
 * registers a style applier function into this [KoshianStyle].
 *
 * Styles can be applied via [applyKoshian]
 */
@Suppress("FunctionName")
inline fun KoshianStyle.ImageButton(
      crossinline styleAction: ViewStyle<ImageButton>.() -> Unit
): KoshianStyle.StyleElement<ImageButton> {
   return createStyleElement(styleAction)
}
