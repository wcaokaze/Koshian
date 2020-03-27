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

object NothingConstructor : KoshianViewGroupConstructor<Nothing, Nothing> {
   override fun instantiate(context: Context?) = throw UnsupportedOperationException()
   override fun instantiateLayoutParams()      = throw UnsupportedOperationException()
}

inline fun <V : View> V.applyKoshian(
      applyAction: ViewBuilder<V, ViewGroup.LayoutParams, KoshianMode.Applier<Nothing>>.() -> Unit
) {
   val oldContext = `$$KoshianInternal`.context
   val oldParentConstructor = `$$KoshianInternal`.parentViewConstructor
   val oldApplyingIndex = `$$ApplierInternal`.applyingIndex
   val oldStyle = `$$StyleInternal`.style
   `$$KoshianInternal`.context = context
   `$$KoshianInternal`.parentViewConstructor = NothingConstructor
   `$$ApplierInternal`.applyingIndex = 0
   `$$StyleInternal`.style = null

   try {
      val koshian = ViewBuilder<V, ViewGroup.LayoutParams, KoshianMode.Applier<Nothing>>(this)
      koshian.applyAction()
   } finally {
      `$$KoshianInternal`.context = oldContext
      `$$KoshianInternal`.parentViewConstructor = oldParentConstructor
      `$$ApplierInternal`.applyingIndex = oldApplyingIndex
      `$$StyleInternal`.style = oldStyle
   }
}

inline fun <V : View, L : ViewGroup.LayoutParams> V.applyKoshian(
      constructor: KoshianViewGroupConstructor<V, L>,
      applyAction: ViewGroupBuilder<V, ViewGroup.LayoutParams, L, KoshianMode.Applier<Nothing>>.() -> Unit
) {
   val oldContext = `$$KoshianInternal`.context
   val oldParentConstructor = `$$KoshianInternal`.parentViewConstructor
   val oldApplyingIndex = `$$ApplierInternal`.applyingIndex
   val oldStyle = `$$StyleInternal`.style
   `$$KoshianInternal`.context = context
   `$$KoshianInternal`.parentViewConstructor = constructor
   `$$ApplierInternal`.applyingIndex = 0
   `$$StyleInternal`.style = null

   try {
      val koshian = ViewGroupBuilder<V, ViewGroup.LayoutParams, L, KoshianMode.Applier<Nothing>>(this)
      koshian.applyAction()
   } finally {
      `$$KoshianInternal`.context = oldContext
      `$$KoshianInternal`.parentViewConstructor = oldParentConstructor
      `$$ApplierInternal`.applyingIndex = oldApplyingIndex
      `$$StyleInternal`.style = oldStyle
   }
}

inline fun <reified V, L, S>
      Koshian<ViewManager, *, L, KoshianMode.Applier<S>>.apply(
            constructor: KoshianViewConstructor<V>,
            buildAction: ViewBuilder<V, L, KoshianMode.Applier<S>>.() -> Unit
      )
      where V : View, S : KoshianStyle
{
   val view = `$$ApplierInternal`.findViewOrInsertNew(
         `$$koshianInternal$view`, constructor, V::class.java)

   val koshian = ViewBuilder<V, L, KoshianMode.Applier<S>>(view)
   koshian.buildAction()
}

inline fun <reified V, L, S>
      Koshian<ViewManager, *, L, KoshianMode.Applier<S>>.apply(
            constructor: KoshianViewConstructor<V>,
            styleElement: KoshianStyle.StyleElement<V>,
            buildAction: ViewBuilder<V, L, KoshianMode.Applier<S>>.() -> Unit
      )
      where V : View, S : KoshianStyle
{
   val view = `$$ApplierInternal`.findViewOrInsertNewAndApplyStyle(
         `$$koshianInternal$view`, constructor, styleElement, V::class.java)

   val koshian = ViewBuilder<V, L, KoshianMode.Applier<S>>(view)
   koshian.buildAction()
}

inline fun <reified V, L, S>
      Koshian<ViewManager, *, L, KoshianMode.Applier<S>>.apply(
            name: String,
            buildAction: ViewBuilder<V, L, KoshianMode.Applier<S>>.() -> Unit
      )
      where V : View, S : KoshianStyle
{
   val parent = `$$koshianInternal$view` as ViewManager

   for (view in `$$ApplierInternal`.findViewByName(parent, name, V::class.java)) {
      val koshian = ViewBuilder<V, L, KoshianMode.Applier<S>>(view)
      koshian.buildAction()
   }
}

inline fun <reified V, L, S>
      Koshian<ViewManager, *, L, KoshianMode.Applier<S>>.apply(
            name: String,
            styleElement: KoshianStyle.StyleElement<V>,
            buildAction: ViewBuilder<V, L, KoshianMode.Applier<S>>.() -> Unit
      )
      where V : View, S : KoshianStyle
{
   for (view in `$$ApplierInternal`.findViewByName(`$$koshianInternal$view`, name, V::class.java)) {
      styleElement.applyStyleTo(view)
      val koshian = ViewBuilder<V, L, KoshianMode.Applier<S>>(view)
      koshian.buildAction()
   }
}

inline fun <reified V, L, CL, S>
      Koshian<ViewManager, *, L, KoshianMode.Applier<S>>.apply(
            constructor: KoshianViewGroupConstructor<V, CL>,
            applyAction: ViewGroupBuilder<V, L, CL, KoshianMode.Applier<S>>.() -> Unit
      )
      where V : View,
            CL : ViewGroup.LayoutParams,
            S : KoshianStyle
{
   val oldParentConstructor = `$$KoshianInternal`.parentViewConstructor
   `$$KoshianInternal`.parentViewConstructor = constructor

   val view = `$$ApplierInternal`.findViewOrInsertNew(
         `$$koshianInternal$view`, constructor, V::class.java)

   val koshian = ViewBuilder<V, L, KoshianMode.Applier<S>>(view)

   val oldApplyingIndex = `$$ApplierInternal`.applyingIndex
   `$$ApplierInternal`.applyingIndex = 0
   koshian.applyAction()
   `$$ApplierInternal`.applyingIndex = oldApplyingIndex

   `$$KoshianInternal`.parentViewConstructor = oldParentConstructor
}

inline fun <reified V, L, CL, S>
      Koshian<ViewManager, *, L, KoshianMode.Applier<S>>.apply(
            constructor: KoshianViewGroupConstructor<V, CL>,
            styleElement: KoshianStyle.StyleElement<V>,
            applyAction: ViewGroupBuilder<V, L, CL, KoshianMode.Applier<S>>.() -> Unit
      )
      where V : View,
            CL : ViewGroup.LayoutParams,
            S : KoshianStyle
{
   val oldParentConstructor = `$$KoshianInternal`.parentViewConstructor
   `$$KoshianInternal`.parentViewConstructor = constructor

   val view = `$$ApplierInternal`.findViewOrInsertNewAndApplyStyle(
         `$$koshianInternal$view`, constructor, styleElement, V::class.java)

   val koshian = ViewBuilder<V, L, KoshianMode.Applier<S>>(view)

   val oldApplyingIndex = `$$ApplierInternal`.applyingIndex
   `$$ApplierInternal`.applyingIndex = 0
   koshian.applyAction()
   `$$ApplierInternal`.applyingIndex = oldApplyingIndex

   `$$KoshianInternal`.parentViewConstructor = oldParentConstructor
}

inline fun <reified V, L, CL, S>
      Koshian<ViewManager, *, L, KoshianMode.Applier<S>>.apply(
            name: String,
            constructor: KoshianViewGroupConstructor<V, CL>,
            applyAction: ViewGroupBuilder<V, L, CL, KoshianMode.Applier<S>>.() -> Unit
      )
      where V : View,
            CL : ViewGroup.LayoutParams,
            S : KoshianStyle
{
   val oldParentConstructor = `$$KoshianInternal`.parentViewConstructor
   `$$KoshianInternal`.parentViewConstructor = constructor

   val oldApplyingIndex = `$$ApplierInternal`.applyingIndex

   for (view in `$$ApplierInternal`.findViewByName(`$$koshianInternal$view`, name, V::class.java)) {
      val koshian = ViewBuilder<V, L, KoshianMode.Applier<S>>(view)

      `$$ApplierInternal`.applyingIndex = 0
      koshian.applyAction()
   }

   `$$ApplierInternal`.applyingIndex = oldApplyingIndex
   `$$KoshianInternal`.parentViewConstructor = oldParentConstructor
}

inline fun <reified V, L, CL, S>
      Koshian<ViewManager, *, L, KoshianMode.Applier<S>>.apply(
            name: String,
            constructor: KoshianViewGroupConstructor<V, CL>,
            styleElement: KoshianStyle.StyleElement<V>,
            applyAction: ViewGroupBuilder<V, L, CL, KoshianMode.Applier<S>>.() -> Unit
      )
      where V : View,
            CL : ViewGroup.LayoutParams,
            S : KoshianStyle
{
   val oldParentConstructor = `$$KoshianInternal`.parentViewConstructor
   `$$KoshianInternal`.parentViewConstructor = constructor

   val oldApplyingIndex = `$$ApplierInternal`.applyingIndex

   for (view in `$$ApplierInternal`.findViewByName(`$$koshianInternal$view`, name, V::class.java)) {
      styleElement.applyStyleTo(view)

      val koshian = ViewBuilder<V, L, KoshianMode.Applier<S>>(view)

      `$$ApplierInternal`.applyingIndex = 0
      koshian.applyAction()
   }

   `$$ApplierInternal`.applyingIndex = oldApplyingIndex
   `$$KoshianInternal`.parentViewConstructor = oldParentConstructor
}
