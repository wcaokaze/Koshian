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
inline fun <L> KoshianParent<L, KoshianMode.Creator>.VideoView(
      buildAction: ViewBuilder<VideoView, L, KoshianMode.Creator>.() -> Unit
): VideoView {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return create(VideoViewConstructor, buildAction)
}

/**
 * creates a new VideoView with name, and adds it into this ViewGroup.
 *
 * The name can be referenced in [applyKoshian]
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Creator>.VideoView(
      name: String,
      buildAction: ViewBuilder<VideoView, L, KoshianMode.Creator>.() -> Unit
): VideoView {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return create(name, VideoViewConstructor, buildAction)
}

/**
 * If the next View is a VideoView, applies Koshian to it.
 *
 * Otherwise, creates a new VideoView and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Applier>.VideoView(
      buildAction: ViewBuilder<VideoView, L, KoshianMode.Applier>.() -> Unit
) {
   apply(VideoViewConstructor, buildAction)
}

/**
 * Applies Koshian to all VideoViews that are named the specified in this ViewGroup.
 * If there are no VideoViews named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Applier>.VideoView(
      name: String,
      buildAction: ViewBuilder<VideoView, L, KoshianMode.Applier>.() -> Unit
) {
   apply(name, buildAction)
}
