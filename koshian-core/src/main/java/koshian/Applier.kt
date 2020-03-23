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

object NothingConstructor : KoshianViewGroupConstructor<Nothing, Nothing>() {
   override fun instantiate(context: Context?) = throw UnsupportedOperationException()
   override fun instantiateLayoutParams()      = throw UnsupportedOperationException()
}

inline fun <V : View> V.applyKoshian(
      applyAction: ViewBuilder<V, ViewGroup.LayoutParams, KoshianMode.Applier>.() -> Unit
) {
   val oldContext = `$$KoshianInternal`.context
   val oldParentConstructor = `$$KoshianInternal`.parentViewConstructor
   val oldApplyingIndex = `$$KoshianInternal`.applyingIndex
   `$$KoshianInternal`.context = context
   `$$KoshianInternal`.parentViewConstructor = NothingConstructor
   `$$KoshianInternal`.applyingIndex = 0

   try {
      val koshian = ViewBuilder<V, ViewGroup.LayoutParams, KoshianMode.Applier>(this)
      koshian.applyAction()
   } finally {
      `$$KoshianInternal`.context = oldContext
      `$$KoshianInternal`.parentViewConstructor = oldParentConstructor
      `$$KoshianInternal`.applyingIndex = oldApplyingIndex
   }
}

inline fun <V : View> V.applyKoshian(
      style: KoshianStyle,
      constructor: KoshianViewConstructor<V>,
      applyAction: ViewBuilder<V, ViewGroup.LayoutParams, KoshianMode.Applier>.() -> Unit
) {
   val oldContext = `$$KoshianInternal`.context
   val oldParentConstructor = `$$KoshianInternal`.parentViewConstructor
   val oldApplyingIndex = `$$KoshianInternal`.applyingIndex
   `$$KoshianInternal`.context = context
   `$$KoshianInternal`.parentViewConstructor = NothingConstructor
   `$$KoshianInternal`.applyingIndex = 0

   style.defaultStyle()

   try {
      val koshian = ViewBuilder<V, ViewGroup.LayoutParams, KoshianMode.Applier>(this)

      @Suppress("UNCHECKED_CAST")
      val styleAction = constructor.styleAction
            as ViewBuilder<V, ViewGroup.LayoutParams, KoshianMode.Applier>.() -> Unit

      koshian.styleAction()
      koshian.applyAction()
   } finally {
      `$$KoshianInternal`.context = oldContext
      `$$KoshianInternal`.parentViewConstructor = oldParentConstructor
      `$$KoshianInternal`.applyingIndex = oldApplyingIndex
   }
}

inline fun <V : View, L : ViewGroup.LayoutParams> V.applyKoshian(
      constructor: KoshianViewGroupConstructor<V, L>,
      applyAction: ViewGroupBuilder<V, ViewGroup.LayoutParams, L, KoshianMode.Applier>.() -> Unit
) {
   val oldContext = `$$KoshianInternal`.context
   val oldParentConstructor = `$$KoshianInternal`.parentViewConstructor
   val oldApplyingIndex = `$$KoshianInternal`.applyingIndex
   `$$KoshianInternal`.context = context
   `$$KoshianInternal`.parentViewConstructor = constructor
   `$$KoshianInternal`.applyingIndex = 0

   try {
      val koshian = ViewGroupBuilder<V, ViewGroup.LayoutParams, L, KoshianMode.Applier>(this)
      koshian.applyAction()
   } finally {
      `$$KoshianInternal`.context = oldContext
      `$$KoshianInternal`.parentViewConstructor = oldParentConstructor
      `$$KoshianInternal`.applyingIndex = oldApplyingIndex
   }
}

inline fun <reified V, L>
      Koshian<ViewManager, *, L, KoshianMode.Applier>.apply(
            constructor: KoshianViewConstructor<V>,
            buildAction: ViewBuilder<V, L, KoshianMode.Applier>.() -> Unit
      )
      where V : View
{
   val parent = `$$koshianInternal$view` as ViewManager

   var view = `$$KoshianInternal`.findView(parent, V::class.java)

   if (view == null) {
      view = `$$KoshianInternal`.addNewView(parent, constructor)
   }

   val koshian = ViewBuilder<V, L, KoshianMode.Applier>(view)
   koshian.buildAction()
}

inline fun <reified V, L>
      Koshian<ViewManager, *, L, KoshianMode.Applier>.apply(
            name: String,
            buildAction: ViewBuilder<V, L, KoshianMode.Applier>.() -> Unit
      )
      where V : View
{
   val parent = `$$koshianInternal$view` as ViewManager

   for (view in `$$KoshianInternal`.findViewByName(parent, name, V::class.java)) {
      val koshian = ViewBuilder<V, L, KoshianMode.Applier>(view)
      koshian.buildAction()
   }
}

inline fun <reified V, L, CL>
      Koshian<ViewManager, *, L, KoshianMode.Applier>.apply(
            constructor: KoshianViewGroupConstructor<V, CL>,
            applyAction: ViewGroupBuilder<V, L, CL, KoshianMode.Applier>.() -> Unit
      )
      where V : View,
            CL : ViewGroup.LayoutParams
{
   val oldParentConstructor = `$$KoshianInternal`.parentViewConstructor
   `$$KoshianInternal`.parentViewConstructor = constructor

   val parent = `$$koshianInternal$view` as ViewManager

   val view = `$$KoshianInternal`.findView(parent, V::class.java)
         ?: constructor.instantiate(`$$KoshianInternal`.context)

   val koshian = ViewBuilder<V, L, KoshianMode.Applier>(view)

   val oldApplyingIndex = `$$KoshianInternal`.applyingIndex
   `$$KoshianInternal`.applyingIndex = 0
   koshian.applyAction()
   `$$KoshianInternal`.applyingIndex = oldApplyingIndex

   `$$KoshianInternal`.parentViewConstructor = oldParentConstructor
}

inline fun <reified V, L, CL>
      Koshian<ViewManager, *, L, KoshianMode.Applier>.apply(
            name: String,
            constructor: KoshianViewGroupConstructor<V, CL>,
            applyAction: ViewGroupBuilder<V, L, CL, KoshianMode.Applier>.() -> Unit
      )
      where V : View,
            CL : ViewGroup.LayoutParams
{
   val oldParentConstructor = `$$KoshianInternal`.parentViewConstructor
   `$$KoshianInternal`.parentViewConstructor = constructor

   val parent = `$$koshianInternal$view` as ViewManager
   val oldApplyingIndex = `$$KoshianInternal`.applyingIndex

   for (view in `$$KoshianInternal`.findViewByName(parent, name, V::class.java)) {
      val koshian = ViewBuilder<V, L, KoshianMode.Applier>(view)

      `$$KoshianInternal`.applyingIndex = 0
      koshian.applyAction()
   }

   `$$KoshianInternal`.applyingIndex = oldApplyingIndex
   `$$KoshianInternal`.parentViewConstructor = oldParentConstructor
}
