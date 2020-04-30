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

package koshian.recyclerview

import android.content.Context
import android.view.ViewManager
import androidx.recyclerview.widget.RecyclerView
import koshian.*
import kotlin.contracts.*

@KoshianMarker
@ExperimentalContracts
inline fun <R> (@Suppress("UNUSED") KoshianViewHolder<*>).koshian(
   context: Context,
   creatorAction: Koshian<ViewManager, Nothing, RecyclerView.LayoutParams, KoshianMode.Creator>.() -> R
): R {
   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }

   val oldContext = `$$KoshianInternal`.context
   val oldParentConstructor = `$$KoshianInternal`.parentViewConstructor
   val oldApplyingIndex = `$$ApplierInternal`.applyingIndex
   val oldStyle = `$$StyleInternal`.style
   `$$KoshianInternal`.context = context
   `$$KoshianInternal`.parentViewConstructor = KoshianRecyclerViewRoot.CONSTRUCTOR
   `$$ApplierInternal`.applyingIndex = -1
   `$$StyleInternal`.style = null

   `$$KoshianInternal`.init(context)

   try {
      val koshian = Koshian<Nothing, Nothing, RecyclerView.LayoutParams, KoshianMode.Creator>(KoshianRecyclerViewRoot.INSTANCE)
      return koshian.creatorAction()
   } finally {
      `$$KoshianInternal`.context = oldContext
      `$$KoshianInternal`.parentViewConstructor = oldParentConstructor
      `$$ApplierInternal`.applyingIndex = oldApplyingIndex
      `$$StyleInternal`.style = oldStyle
   }
}
