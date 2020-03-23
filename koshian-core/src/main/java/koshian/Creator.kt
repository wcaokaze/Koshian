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
import com.wcaokaze.koshian.*
import kotlin.contracts.*

@ExperimentalContracts
inline fun <R> koshian(
      context: Context,
      koshianBuilder: Koshian<Nothing, Nothing, ViewGroup.LayoutParams, KoshianMode.Creator>.() -> R
): R {
   contract { callsInPlace(koshianBuilder, InvocationKind.EXACTLY_ONCE) }

   val oldContext = `$$KoshianInternal`.context
   val oldParentConstructor = `$$KoshianInternal`.parentViewConstructor
   val oldApplyingIndex = `$$ApplierInternal`.applyingIndex
   `$$KoshianInternal`.context = context
   `$$KoshianInternal`.parentViewConstructor = KoshianRoot.CONSTRUCTOR
   `$$ApplierInternal`.applyingIndex = -1

   `$$KoshianInternal`.init(context)

   try {
      val koshian = Koshian<Nothing, Nothing, ViewGroup.LayoutParams, KoshianMode.Creator>(KoshianRoot.INSTANCE)
      return koshian.koshianBuilder()
   } finally {
      `$$KoshianInternal`.context = oldContext
      `$$KoshianInternal`.parentViewConstructor = oldParentConstructor
      `$$ApplierInternal`.applyingIndex = oldApplyingIndex
   }
}

@ExperimentalContracts
inline fun <P, L, R>
      P.addView(
            parentConstructor: KoshianViewGroupConstructor<P, L>,
            buildAction: Koshian<P, Nothing, L, KoshianMode.Creator>.() -> R
      ): R
      where P : ViewGroup,
            L : ViewGroup.LayoutParams
{
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }

   val oldContext = `$$KoshianInternal`.context
   val oldParentConstructor = `$$KoshianInternal`.parentViewConstructor
   `$$KoshianInternal`.context = context
   `$$KoshianInternal`.parentViewConstructor = parentConstructor

   try {
      val koshian = Koshian<P, Nothing, L, KoshianMode.Creator>(this)
      return koshian.buildAction()
   } finally {
      `$$KoshianInternal`.context = oldContext
      `$$KoshianInternal`.parentViewConstructor = oldParentConstructor
   }
}

inline fun <V, L, C, CL>
      Koshian<V, L, CL, KoshianMode.Creator>.create(
            constructor: KoshianViewConstructor<C>,
            buildAction: ViewBuilder<C, CL, KoshianMode.Creator>.() -> Unit
      ): C
      where V : ViewManager,
            C : View
{
   val view = constructor.instantiate(`$$KoshianInternal`.context)

   val parent = `$$koshianInternal$view` as ViewManager
   val parentViewConstructor = `$$KoshianInternal`.parentViewConstructor
   val layoutParams = parentViewConstructor.instantiateLayoutParams()

   parent.addView(view, layoutParams)

   val koshian = ViewBuilder<C, CL, KoshianMode.Creator>(view)
   koshian.buildAction()
   return view
}

inline fun <V, L, C, CL>
      Koshian<V, L, CL, KoshianMode.Creator>.create(
            name: String,
            constructor: KoshianViewConstructor<C>,
            buildAction: ViewBuilder<C, CL, KoshianMode.Creator>.() -> Unit
      ): C
      where V : ViewManager,
            C : View
{
   val view = constructor.instantiate(`$$KoshianInternal`.context)
   view.setTag(R.id.view_tag_name, name)

   val parent = `$$koshianInternal$view` as ViewManager
   val parentViewConstructor = `$$KoshianInternal`.parentViewConstructor
   val layoutParams = parentViewConstructor.instantiateLayoutParams()

   parent.addView(view, layoutParams)

   val koshian = ViewBuilder<C, CL, KoshianMode.Creator>(view)
   koshian.buildAction()
   return view
}

inline fun <V, L, C, CL, CCL>
      Koshian<V, L, CL, KoshianMode.Creator>.create(
            constructor: KoshianViewGroupConstructor<C, CCL>,
            buildAction: ViewGroupBuilder<C, CL, CCL, KoshianMode.Creator>.() -> Unit
      ): C
      where V : ViewManager,
            C : View,
            CCL : ViewGroup.LayoutParams
{
   val view = constructor.instantiate(`$$KoshianInternal`.context)

   val parent = `$$koshianInternal$view` as ViewManager
   val parentViewConstructor = `$$KoshianInternal`.parentViewConstructor

   val layoutParams = parentViewConstructor.instantiateLayoutParams()

   parent.addView(view, layoutParams)

   `$$KoshianInternal`.parentViewConstructor = constructor
   val koshian = ViewGroupBuilder<C, CL, CCL, KoshianMode.Creator>(view)
   koshian.buildAction()
   `$$KoshianInternal`.parentViewConstructor = parentViewConstructor

   return view
}

inline fun <V, L, C, CL, CCL>
      Koshian<V, L, CL, KoshianMode.Creator>.create(
            name: String,
            constructor: KoshianViewGroupConstructor<C, CCL>,
            buildAction: ViewGroupBuilder<C, CL, CCL, KoshianMode.Creator>.() -> Unit
      ): C
      where V : ViewManager,
            C : View,
            CCL : ViewGroup.LayoutParams
{
   val view = constructor.instantiate(`$$KoshianInternal`.context)
   view.setTag(R.id.view_tag_name, name)

   val parent = `$$koshianInternal$view` as ViewManager
   val parentViewConstructor = `$$KoshianInternal`.parentViewConstructor

   val layoutParams = parentViewConstructor.instantiateLayoutParams()

   parent.addView(view, layoutParams)

   `$$KoshianInternal`.parentViewConstructor = constructor
   val koshian = ViewGroupBuilder<C, CL, CCL, KoshianMode.Creator>(view)
   koshian.buildAction()
   `$$KoshianInternal`.parentViewConstructor = parentViewConstructor

   return view
}
