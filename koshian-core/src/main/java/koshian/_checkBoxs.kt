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
import android.widget.CheckBox
import kotlin.contracts.*

object CheckBoxConstructor : KoshianViewConstructor<CheckBox>(CheckBox::class.java) {
   override fun instantiate(context: Context?) = CheckBox(context)
}

/**
 * creates a new CheckBox and adds it into this ViewGroup.
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Creator>.CheckBox(
      buildAction: ViewBuilder<CheckBox, L, KoshianMode.Creator>.() -> Unit
): CheckBox {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return create(CheckBoxConstructor, buildAction)
}

/**
 * creates a new CheckBox with name, and adds it into this ViewGroup.
 *
 * The name can be referenced in [applyKoshian]
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Creator>.CheckBox(
      name: String,
      buildAction: ViewBuilder<CheckBox, L, KoshianMode.Creator>.() -> Unit
): CheckBox {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return create(name, CheckBoxConstructor, buildAction)
}

/**
 * If the next View is a CheckBox, applies Koshian to it.
 *
 * Otherwise, creates a new CheckBox and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Applier>.CheckBox(
      buildAction: ViewBuilder<CheckBox, L, KoshianMode.Applier>.() -> Unit
) {
   apply(CheckBoxConstructor, buildAction)
}

/**
 * Applies Koshian to all CheckBoxs that are named the specified in this ViewGroup.
 * If there are no CheckBoxs named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Applier>.CheckBox(
      name: String,
      buildAction: ViewBuilder<CheckBox, L, KoshianMode.Applier>.() -> Unit
) {
   apply(name, buildAction)
}
