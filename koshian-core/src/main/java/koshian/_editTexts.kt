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
import android.widget.EditText
import kotlin.contracts.*

object EditTextConstructor : KoshianViewConstructor<EditText>(EditText::class.java) {
   override fun instantiate(context: Context?) = EditText(context)
}

/**
 * creates a new EditText and adds it into this ViewGroup.
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Creator>.EditText(
      buildAction: ViewBuilder<EditText, L, KoshianMode.Creator>.() -> Unit
): EditText {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return create(EditTextConstructor, buildAction)
}

/**
 * creates a new EditText with name, and adds it into this ViewGroup.
 *
 * The name can be referenced in [applyKoshian]
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Creator>.EditText(
      name: String,
      buildAction: ViewBuilder<EditText, L, KoshianMode.Creator>.() -> Unit
): EditText {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return create(name, EditTextConstructor, buildAction)
}

/**
 * If the next View is a EditText, applies Koshian to it.
 *
 * Otherwise, creates a new EditText and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Applier>.EditText(
      buildAction: ViewBuilder<EditText, L, KoshianMode.Applier>.() -> Unit
) {
   apply(EditTextConstructor, buildAction)
}

/**
 * Applies Koshian to all EditTexts that are named the specified in this ViewGroup.
 * If there are no EditTexts named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Applier>.EditText(
      name: String,
      buildAction: ViewBuilder<EditText, L, KoshianMode.Applier>.() -> Unit
) {
   apply(name, buildAction)
}
