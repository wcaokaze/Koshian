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
import android.widget.VideoView
import kotlin.contracts.*

object VideoViewConstructor : KoshianViewConstructor<VideoView> {
   override fun instantiate(context: Context?) = VideoView(context)
}

/**
 * creates a new VideoView and adds it into this ViewGroup.
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> CreatorParent<L>.VideoView(
      creatorAction: ViewCreator<VideoView, L>.() -> Unit
): VideoView {
   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }
   return create(VideoViewConstructor, creatorAction)
}

/**
 * creates a new VideoView with name, and adds it into this ViewGroup.
 *
 * The name can be referenced in [applyKoshian]
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> CreatorParent<L>.VideoView(
      name: String,
      creatorAction: ViewCreator<VideoView, L>.() -> Unit
): VideoView {
   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }
   return create(name, VideoViewConstructor, creatorAction)
}

/**
 * If the next View is a VideoView, applies Koshian to it.
 *
 * Otherwise, creates a new VideoView and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L, S : KoshianStyle>
      ApplierParent<L, S>.VideoView(
            applierAction: ViewApplier<VideoView, L, S>.() -> Unit
      )
{
   apply(VideoViewConstructor, applierAction)
}

/**
 * If the next View is a VideoView, applies Koshian to it.
 *
 * Otherwise, creates a new VideoView and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L, S : KoshianStyle>
      ApplierParent<L, S>.VideoView(
            styleElement: KoshianStyle.StyleElement<VideoView>,
            applierAction: ViewApplier<VideoView, L, S>.() -> Unit
      )
{
   apply(VideoViewConstructor, styleElement, applierAction)
}

/**
 * Applies Koshian to all VideoViews that are named the specified in this ViewGroup.
 * If there are no VideoViews named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L, S : KoshianStyle>
      ApplierParent<L, S>.VideoView(
            name: String,
            applierAction: ViewApplier<VideoView, L, S>.() -> Unit
      )
{
   apply(name, applierAction)
}

/**
 * Applies Koshian to all VideoViews that are named the specified in this ViewGroup.
 * If there are no VideoViews named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L, S : KoshianStyle>
      ApplierParent<L, S>.VideoView(
            name: String,
            styleElement: KoshianStyle.StyleElement<VideoView>,
            applierAction: ViewApplier<VideoView, L, S>.() -> Unit
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
inline fun KoshianStyle.VideoView(
      crossinline styleAction: ViewStyle<VideoView>.() -> Unit
): KoshianStyle.StyleElement<VideoView> {
   return createStyleElement(styleAction)
}
