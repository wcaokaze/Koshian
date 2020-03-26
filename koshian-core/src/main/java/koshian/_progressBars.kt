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
inline fun <L> KoshianParent<L, KoshianMode.Creator>.ProgressBar(
      buildAction: ViewBuilder<ProgressBar, L, KoshianMode.Creator>.() -> Unit
): ProgressBar {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return create(ProgressBarConstructor, buildAction)
}

/**
 * creates a new ProgressBar with name, and adds it into this ViewGroup.
 *
 * The name can be referenced in [applyKoshian]
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Creator>.ProgressBar(
      name: String,
      buildAction: ViewBuilder<ProgressBar, L, KoshianMode.Creator>.() -> Unit
): ProgressBar {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return create(name, ProgressBarConstructor, buildAction)
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
      KoshianParent<L, KoshianMode.Applier<S>>.ProgressBar(
            buildAction: ViewBuilder<ProgressBar, L, KoshianMode.Applier<S>>.() -> Unit
      )
{
   apply(ProgressBarConstructor, buildAction)
}

/**
 * Applies Koshian to all ProgressBars that are named the specified in this ViewGroup.
 * If there are no ProgressBars named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L, S : KoshianStyle>
      KoshianParent<L, KoshianMode.Applier<S>>.ProgressBar(
            name: String,
            buildAction: ViewBuilder<ProgressBar, L, KoshianMode.Applier<S>>.() -> Unit
      )
{
   apply(name, buildAction)
}
