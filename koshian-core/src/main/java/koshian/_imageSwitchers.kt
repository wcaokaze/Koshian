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
import android.widget.ImageSwitcher
import kotlin.contracts.*

object ImageSwitcherConstructor : KoshianViewConstructor<ImageSwitcher> {
   override fun instantiate(context: Context?) = ImageSwitcher(context)
}

/**
 * creates a new ImageSwitcher and adds it into this ViewGroup.
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Creator>.ImageSwitcher(
      buildAction: ViewBuilder<ImageSwitcher, L, KoshianMode.Creator>.() -> Unit
): ImageSwitcher {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return create(ImageSwitcherConstructor, buildAction)
}

/**
 * creates a new ImageSwitcher with name, and adds it into this ViewGroup.
 *
 * The name can be referenced in [applyKoshian]
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Creator>.ImageSwitcher(
      name: String,
      buildAction: ViewBuilder<ImageSwitcher, L, KoshianMode.Creator>.() -> Unit
): ImageSwitcher {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return create(name, ImageSwitcherConstructor, buildAction)
}

/**
 * If the next View is a ImageSwitcher, applies Koshian to it.
 *
 * Otherwise, creates a new ImageSwitcher and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L, S : KoshianStyle>
      KoshianParent<L, KoshianMode.Applier<S>>.ImageSwitcher(
            buildAction: ViewBuilder<ImageSwitcher, L, KoshianMode.Applier<S>>.() -> Unit
      )
{
   apply(ImageSwitcherConstructor, buildAction)
}

/**
 * Applies Koshian to all ImageSwitchers that are named the specified in this ViewGroup.
 * If there are no ImageSwitchers named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L, S : KoshianStyle>
      KoshianParent<L, KoshianMode.Applier<S>>.ImageSwitcher(
            name: String,
            buildAction: ViewBuilder<ImageSwitcher, L, KoshianMode.Applier<S>>.() -> Unit
      )
{
   apply(name, buildAction)
}
