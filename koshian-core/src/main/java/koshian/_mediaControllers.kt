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
import android.widget.MediaController
import kotlin.contracts.*

object MediaControllerConstructor : KoshianViewConstructor<MediaController, Nothing> {
   override fun instantiate(context: Context?) = MediaController(context)
   override fun instantiateLayoutParams(): Nothing = throw UnsupportedOperationException()
}

/**
 * creates a new MediaController and adds it into this ViewGroup.
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams> CreatorParent<L>.MediaController(
      creatorAction: ViewCreator<MediaController, L>.() -> Unit
): MediaController {
   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }
   return create(MediaControllerConstructor, creatorAction)
}

/**
 * creates a new MediaController with name, and adds it into this ViewGroup.
 *
 * The name can be referenced in [applyKoshian]
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams> CreatorParent<L>.MediaController(
      name: String,
      creatorAction: ViewCreator<MediaController, L>.() -> Unit
): MediaController {
   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }
   return create(name, MediaControllerConstructor, creatorAction)
}

/**
 * If the next View is a MediaController, applies Koshian to it.
 *
 * Otherwise, creates a new MediaController and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams, S : KoshianStyle>
      ApplierParent<L, S>.MediaController(
            applierAction: ViewApplier<MediaController, L, S>.() -> Unit
      )
{
   apply(MediaControllerConstructor, applierAction)
}

/**
 * If the next View is a MediaController, applies Koshian to it.
 *
 * Otherwise, creates a new MediaController and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams, S : KoshianStyle>
      ApplierParent<L, S>.MediaController(
            styleElement: KoshianStyle.StyleElement<MediaController>,
            applierAction: ViewApplier<MediaController, L, S>.() -> Unit
      )
{
   apply(MediaControllerConstructor, styleElement, applierAction)
}

/**
 * Applies Koshian to all MediaControllers that are named the specified in this ViewGroup.
 * If there are no MediaControllers named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams, S : KoshianStyle>
      ApplierParent<L, S>.MediaController(
            name: String,
            applierAction: ViewApplier<MediaController, L, S>.() -> Unit
      )
{
   apply(MediaControllerConstructor, name, applierAction)
}

/**
 * Applies Koshian to all MediaControllers that are named the specified in this ViewGroup.
 * If there are no MediaControllers named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams, S : KoshianStyle>
      ApplierParent<L, S>.MediaController(
            name: String,
            styleElement: KoshianStyle.StyleElement<MediaController>,
            applierAction: ViewApplier<MediaController, L, S>.() -> Unit
      )
{
   apply(MediaControllerConstructor, name, styleElement, applierAction)
}

/**
 * registers a style applier function into this [KoshianStyle].
 *
 * Styles can be applied via [applyKoshian]
 */
@Suppress("FunctionName")
inline fun KoshianStyle.MediaController(
      crossinline styleAction: ViewStyle<MediaController>.() -> Unit
): KoshianStyle.StyleElement<MediaController> {
   return createStyleElement(styleAction)
}
