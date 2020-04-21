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

@ExperimentalContracts
inline fun <R> koshian(
      context: Context,
      creatorAction: Koshian<ViewManager, Nothing, ViewGroup.LayoutParams, KoshianMode.Creator>.() -> R
): R {
   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }

   val oldContext = `$$KoshianInternal`.context
   val oldParentConstructor = `$$KoshianInternal`.parentViewConstructor
   val oldApplyingIndex = `$$ApplierInternal`.applyingIndex
   val oldStyle = `$$StyleInternal`.style
   `$$KoshianInternal`.context = context
   `$$KoshianInternal`.parentViewConstructor = KoshianRoot.CONSTRUCTOR
   `$$ApplierInternal`.applyingIndex = -1
   `$$StyleInternal`.style = null

   `$$KoshianInternal`.init(context)

   try {
      val koshian = Koshian<Nothing, Nothing, ViewGroup.LayoutParams, KoshianMode.Creator>(KoshianRoot.INSTANCE)
      return koshian.creatorAction()
   } finally {
      `$$KoshianInternal`.context = oldContext
      `$$KoshianInternal`.parentViewConstructor = oldParentConstructor
      `$$ApplierInternal`.applyingIndex = oldApplyingIndex
      `$$StyleInternal`.style = oldStyle
   }
}

@ExperimentalContracts
inline fun <P, L, R>
      P.addView(
            parentConstructor: KoshianViewGroupConstructor<P, L>,
            creatorAction: Koshian<P, Nothing, L, KoshianMode.Creator>.() -> R
      ): R
      where P : ViewGroup,
            L : ViewGroup.LayoutParams
{
   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }

   val oldContext = `$$KoshianInternal`.context
   val oldParentConstructor = `$$KoshianInternal`.parentViewConstructor
   val oldApplyingIndex = `$$ApplierInternal`.applyingIndex
   val oldStyle = `$$StyleInternal`.style
   `$$KoshianInternal`.context = context
   `$$KoshianInternal`.parentViewConstructor = parentConstructor
   `$$ApplierInternal`.applyingIndex = -1
   `$$StyleInternal`.style = null

   try {
      val koshian = Koshian<P, Nothing, L, KoshianMode.Creator>(this)
      return koshian.creatorAction()
   } finally {
      `$$KoshianInternal`.context = oldContext
      `$$KoshianInternal`.parentViewConstructor = oldParentConstructor
      `$$ApplierInternal`.applyingIndex = oldApplyingIndex
      `$$StyleInternal`.style = oldStyle
   }
}

inline fun <V, L>
      Koshian<ViewManager, *, L, KoshianMode.Creator>.create(
            constructor: KoshianViewConstructor<V>,
            creatorAction: ViewCreator<V, L>.() -> Unit
      ): V
      where V : View
{
   val view = `$$CreatorInternal`.addNewView(`$$koshianInternal$view`, constructor)

   val koshian = ViewCreator<V, L>(view)
   koshian.creatorAction()
   return view
}

inline fun <V, L>
      Koshian<ViewManager, *, L, KoshianMode.Creator>.create(
            name: String,
            constructor: KoshianViewConstructor<V>,
            creatorAction: ViewCreator<V, L>.() -> Unit
      ): V
      where V : View
{
   val view = `$$CreatorInternal`.addNewView(`$$koshianInternal$view`, name, constructor)

   val koshian = ViewCreator<V, L>(view)
   koshian.creatorAction()
   return view
}

inline fun <V, L, CL>
      Koshian<ViewManager, *, L, KoshianMode.Creator>.create(
            constructor: KoshianViewGroupConstructor<V, CL>,
            creatorAction: ViewGroupCreator<V, L, CL>.() -> Unit
      ): V
      where V : ViewGroup,
            L : ViewGroup.LayoutParams,
            CL : ViewGroup.LayoutParams
{
   val view = `$$CreatorInternal`.addNewView(`$$koshianInternal$view`, constructor)

   val oldParentViewConstructor = `$$KoshianInternal`.parentViewConstructor
   `$$KoshianInternal`.parentViewConstructor = constructor

   val koshian = ViewGroupCreator<V, L, CL>(view)
   koshian.creatorAction()

   `$$KoshianInternal`.parentViewConstructor = oldParentViewConstructor

   return view
}

inline fun <V, L, CL>
      Koshian<ViewManager, L, CL, KoshianMode.Creator>.create(
            name: String,
            constructor: KoshianViewGroupConstructor<V, CL>,
            creatorAction: ViewGroupCreator<V, L, CL>.() -> Unit
      ): V
      where V : View,
            L : ViewGroup.LayoutParams,
            CL : ViewGroup.LayoutParams
{
   val view = `$$CreatorInternal`.addNewView(`$$koshianInternal$view`, name, constructor)

   val oldParentViewConstructor = `$$KoshianInternal`.parentViewConstructor
   `$$KoshianInternal`.parentViewConstructor = constructor

   val koshian = ViewGroupCreator<V, L, CL>(view)
   koshian.creatorAction()

   `$$KoshianInternal`.parentViewConstructor = oldParentViewConstructor

   return view
}
