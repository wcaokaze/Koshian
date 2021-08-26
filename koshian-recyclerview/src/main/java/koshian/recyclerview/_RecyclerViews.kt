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
package koshian.recyclerview

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import koshian.*
import kotlin.contracts.*

object RecyclerViewConstructor : KoshianViewConstructor<RecyclerView, Nothing> {
   override fun instantiate(context: Context) = RecyclerView(context)
   override fun instantiateLayoutParams(): Nothing = throw UnsupportedOperationException()
}

/**
 * creates a new RecyclerView and adds it into this ViewGroup.
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams> CreatorParent<L>.RecyclerView(
      creatorAction: ViewCreator<RecyclerView, L>.() -> Unit
): RecyclerView {
   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }
   return create(RecyclerViewConstructor, creatorAction)
}

/**
 * creates a new RecyclerView with name, and adds it into this ViewGroup.
 *
 * The name can be referenced in [applyKoshian]
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams> CreatorParent<L>.RecyclerView(
      name: String,
      creatorAction: ViewCreator<RecyclerView, L>.() -> Unit
): RecyclerView {
   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }
   return create(name, RecyclerViewConstructor, creatorAction)
}

/**
 * If the next View is a RecyclerView, applies Koshian to it.
 *
 * Otherwise, creates a new RecyclerView and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams, S : KoshianStyle>
      ApplierParent<L, S>.RecyclerView(
            applierAction: ViewApplier<RecyclerView, L, S>.() -> Unit
      )
{
   apply(RecyclerViewConstructor, applierAction)
}

/**
 * If the next View is a RecyclerView, applies Koshian to it.
 *
 * Otherwise, creates a new RecyclerView and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams, S : KoshianStyle>
      ApplierParent<L, S>.RecyclerView(
            styleElement: KoshianStyle.StyleElement<RecyclerView>,
            applierAction: ViewApplier<RecyclerView, L, S>.() -> Unit
      )
{
   apply(RecyclerViewConstructor, styleElement, applierAction)
}

/**
 * Applies Koshian to all RecyclerViews that are named the specified in this ViewGroup.
 * If there are no RecyclerViews named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams, S : KoshianStyle>
      ApplierParent<L, S>.RecyclerView(
            name: String,
            applierAction: ViewApplier<RecyclerView, L, S>.() -> Unit
      )
{
   apply(RecyclerViewConstructor, name, applierAction)
}

/**
 * Applies Koshian to all RecyclerViews that are named the specified in this ViewGroup.
 * If there are no RecyclerViews named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams, S : KoshianStyle>
      ApplierParent<L, S>.RecyclerView(
            name: String,
            styleElement: KoshianStyle.StyleElement<RecyclerView>,
            applierAction: ViewApplier<RecyclerView, L, S>.() -> Unit
      )
{
   apply(RecyclerViewConstructor, name, styleElement, applierAction)
}

/**
 * registers a style applier function into this [KoshianStyle].
 *
 * Styles can be applied via [applyKoshian]
 */
@Suppress("FunctionName")
inline fun KoshianStyle.RecyclerView(
      crossinline styleAction: ViewStyle<RecyclerView>.() -> Unit
): KoshianStyle.StyleElement<RecyclerView> {
   return createStyleElement(styleAction)
}
