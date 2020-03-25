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
import android.widget.CheckedTextView
import kotlin.contracts.*

object CheckedTextViewConstructor : KoshianViewConstructor<CheckedTextView>(CheckedTextView::class.java) {
   override fun instantiate(context: Context?) = CheckedTextView(context)
}

/**
 * creates a new CheckedTextView and adds it into this ViewGroup.
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Creator>.CheckedTextView(
      buildAction: ViewBuilder<CheckedTextView, L, KoshianMode.Creator>.() -> Unit
): CheckedTextView {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return create(CheckedTextViewConstructor, buildAction)
}

/**
 * creates a new CheckedTextView with name, and adds it into this ViewGroup.
 *
 * The name can be referenced in [applyKoshian]
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Creator>.CheckedTextView(
      name: String,
      buildAction: ViewBuilder<CheckedTextView, L, KoshianMode.Creator>.() -> Unit
): CheckedTextView {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return create(name, CheckedTextViewConstructor, buildAction)
}

/**
 * If the next View is a CheckedTextView, applies Koshian to it.
 *
 * Otherwise, creates a new CheckedTextView and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Applier>.CheckedTextView(
      buildAction: ViewBuilder<CheckedTextView, L, KoshianMode.Applier>.() -> Unit
) {
   apply(CheckedTextViewConstructor, buildAction)
}

/**
 * Applies Koshian to all CheckedTextViews that are named the specified in this ViewGroup.
 * If there are no CheckedTextViews named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Applier>.CheckedTextView(
      name: String,
      buildAction: ViewBuilder<CheckedTextView, L, KoshianMode.Applier>.() -> Unit
) {
   apply(name, buildAction)
}
