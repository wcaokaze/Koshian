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

object ToggleButtonConstructor : KoshianViewConstructor<ToggleButton>() {
   override fun instantiate(context: Context?) = ToggleButton(context)
}

/**
 * creates a new ToggleButton and adds it into this ViewGroup.
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Creator>.ToggleButton(
      buildAction: ViewBuilder<ToggleButton, L, KoshianMode.Creator>.() -> Unit
): ToggleButton {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return create(ToggleButtonConstructor, buildAction)
}

/**
 * creates a new ToggleButton with name, and adds it into this ViewGroup.
 *
 * The name can be referenced in [applyKoshian]
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Creator>.ToggleButton(
      name: String,
      buildAction: ViewBuilder<ToggleButton, L, KoshianMode.Creator>.() -> Unit
): ToggleButton {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return create(name, ToggleButtonConstructor, buildAction)
}

/**
 * If the next View is a ToggleButton, applies Koshian to it.
 *
 * Otherwise, creates a new ToggleButton and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Applier>.ToggleButton(
      buildAction: ViewBuilder<ToggleButton, L, KoshianMode.Applier>.() -> Unit
) {
   apply(ToggleButtonConstructor, buildAction)
}

/**
 * Applies Koshian to all ToggleButtons that are named the specified in this ViewGroup.
 * If there are no ToggleButtons named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Applier>.ToggleButton(
      name: String,
      buildAction: ViewBuilder<ToggleButton, L, KoshianMode.Applier>.() -> Unit
) {
   apply(name, buildAction)
}
