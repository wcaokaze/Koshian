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

package koshian

import android.content.*
import android.view.*

import kotlin.contracts.*

@KoshianMarker
@ExperimentalContracts
inline fun <R> koshian(
      context: Context,
      creatorAction: Koshian<ViewManager, Nothing, ViewGroup.LayoutParams, KoshianMode.Creator>.() -> R
): R {
   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }

   val oldApplyingIndex = `$$ApplierInternal`.applyingIndex
   val oldStyle = `$$StyleInternal`.style
   `$$ApplierInternal`.applyingIndex = -1
   `$$StyleInternal`.style = null

   `$$KoshianInternal`.init(context)

   try {
      val koshian = Koshian<KoshianRoot, Nothing, ViewGroup.LayoutParams, KoshianMode.Creator>(
            KoshianRoot.INSTANCE,
            context,
            KoshianRoot.CONSTRUCTOR
      )

      return koshian.creatorAction()
   } finally {
      `$$ApplierInternal`.applyingIndex = oldApplyingIndex
      `$$StyleInternal`.style = oldStyle
   }
}

@ExperimentalContracts
inline fun <P, L, R>
      P.addView(
            constructor: KoshianViewConstructor<P, L>,
            creatorAction: Koshian<P, Nothing, L, KoshianMode.Creator>.() -> R
      ): R
      where P : ViewGroup,
            L : ViewGroup.LayoutParams
{
   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }

   val oldApplyingIndex = `$$ApplierInternal`.applyingIndex
   val oldStyle = `$$StyleInternal`.style
   `$$ApplierInternal`.applyingIndex = -1
   `$$StyleInternal`.style = null

   try {
      val koshian = Koshian<P, Nothing, L, KoshianMode.Creator>(this, context, constructor)
      return koshian.creatorAction()
   } finally {
      `$$ApplierInternal`.applyingIndex = oldApplyingIndex
      `$$StyleInternal`.style = oldStyle
   }
}

inline fun <V, L, CL>
      Koshian<ViewManager, *, L, KoshianMode.Creator>.create(
            constructor: KoshianViewConstructor<out V, out CL>,
            creatorAction: Koshian<V, L, CL, KoshianMode.Creator>.() -> Unit
      ): V
      where V : View,
            L : ViewGroup.LayoutParams,
            CL : ViewGroup.LayoutParams
{
   val view = `$$CreatorInternal`.addNewView(
         `$$koshianInternal$view`, context, viewConstructor, constructor)

   val koshian = Koshian<V, L, CL, KoshianMode.Creator>(view, context, constructor)
   koshian.creatorAction()

   return view
}

inline fun <V, L, CL>
      Koshian<ViewManager, *, L, KoshianMode.Creator>.create(
            name: String,
            constructor: KoshianViewConstructor<out V, out CL>,
            creatorAction: Koshian<V, L, CL, KoshianMode.Creator>.() -> Unit
      ): V
      where V : View,
            L : ViewGroup.LayoutParams,
            CL : ViewGroup.LayoutParams
{
   val view = `$$CreatorInternal`.addNewView(
         `$$koshianInternal$view`, context, name, viewConstructor, constructor)

   val koshian = Koshian<V, L, CL, KoshianMode.Creator>(view, context, constructor)
   koshian.creatorAction()

   return view
}
