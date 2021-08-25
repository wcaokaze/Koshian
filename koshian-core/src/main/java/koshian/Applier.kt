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

object NothingConstructor : KoshianViewConstructor<Nothing, Nothing> {
   override fun instantiate(context: Context?) = throw UnsupportedOperationException()
   override fun instantiateLayoutParams()      = throw UnsupportedOperationException()
}

@KoshianMarker
inline fun <V : View> V.applyKoshian(
      applierAction: ViewApplier<V, ViewGroup.LayoutParams, Nothing>.() -> Unit
) {
   val oldStyle = `$$StyleInternal`.style
   `$$StyleInternal`.style = null

   try {
      val koshian = ViewApplier<V, ViewGroup.LayoutParams, Nothing>(
            this, context, NothingConstructor, applyingIndex = 0
      )

      koshian.applierAction()
   } finally {
      `$$StyleInternal`.style = oldStyle
   }
}

inline fun <V : View, L : ViewGroup.LayoutParams> V.applyKoshian(
      constructor: KoshianViewConstructor<V, L>,
      applierAction: ViewGroupApplier<V, ViewGroup.LayoutParams, L, Nothing>.() -> Unit
) {
   val oldStyle = `$$StyleInternal`.style
   `$$StyleInternal`.style = null

   try {
      val koshian = ViewGroupApplier<V, ViewGroup.LayoutParams, L, Nothing>(
            this, context, constructor, applyingIndex = 0
      )

      koshian.applierAction()
   } finally {
      `$$StyleInternal`.style = oldStyle
   }
}

inline fun <reified V, L, S>
      Koshian<ViewGroup, *, L, KoshianMode.Applier<S>>.apply(
            constructor: KoshianViewConstructor<V, Nothing>,
            applierAction: ViewApplier<V, L, S>.() -> Unit
      )
      where V : View, L : ViewGroup.LayoutParams, S : KoshianStyle
{
   val view = `$$ApplierInternal`.findViewOrInsertNew(this, constructor, V::class.java)

   val koshian = ViewApplier<V, L, S>(view, context, constructor, applyingIndex = 0)
   koshian.applierAction()
}

inline fun <reified V, L, S>
      Koshian<ViewGroup, *, L, KoshianMode.Applier<S>>.apply(
            constructor: KoshianViewConstructor<V, Nothing>,
            styleElement: KoshianStyle.StyleElement<V>,
            applierAction: ViewApplier<V, L, S>.() -> Unit
      )
      where V : View, L : ViewGroup.LayoutParams, S : KoshianStyle
{
   val view = `$$ApplierInternal`.findViewOrInsertNewAndApplyStyle(
         this, constructor, styleElement, V::class.java)

   val koshian = ViewApplier<V, L, S>(view, context, constructor, applyingIndex = 0)
   koshian.applierAction()
}

inline fun <reified V, L, S>
      Koshian<ViewGroup, *, L, KoshianMode.Applier<S>>.apply(
            constructor: KoshianViewConstructor<V, Nothing>,
            name: String,
            applierAction: ViewApplier<V, L, S>.() -> Unit
      )
      where V : View, S : KoshianStyle
{
   val parent = `$$koshianInternal$view` as ViewManager

   for (view in `$$ApplierInternal`.findViewByName(parent, name, V::class.java)) {
      val koshian = ViewApplier<V, L, S>(view, context, constructor, applyingIndex = 0)
      koshian.applierAction()
   }
}

inline fun <reified V, L, S>
      Koshian<ViewGroup, *, L, KoshianMode.Applier<S>>.apply(
            constructor: KoshianViewConstructor<V, Nothing>,
            name: String,
            styleElement: KoshianStyle.StyleElement<V>,
            applierAction: ViewApplier<V, L, S>.() -> Unit
      )
      where V : View, S : KoshianStyle
{
   for (view in `$$ApplierInternal`.findViewByName(`$$koshianInternal$view`, name, V::class.java)) {
      styleElement.applyStyleTo(view, context)
      val koshian = ViewApplier<V, L, S>(view, context, constructor, applyingIndex = 0)
      koshian.applierAction()
   }
}

inline fun <reified V, L, CL, S>
      Koshian<ViewGroup, *, L, KoshianMode.Applier<S>>.applyViewGroup(
            constructor: KoshianViewConstructor<V, CL>,
            applierAction: ViewGroupApplier<V, L, CL, S>.() -> Unit
      )
      where V : View,
            L : ViewGroup.LayoutParams,
            CL : ViewGroup.LayoutParams,
            S : KoshianStyle
{
   val view = `$$ApplierInternal`.findViewOrInsertNew(this, constructor, V::class.java)

   val koshian = ViewGroupApplier<V, L, CL, S>(view, context, constructor, applyingIndex = 0)
   koshian.applierAction()
}

inline fun <reified V, L, CL, S>
      Koshian<ViewGroup, *, L, KoshianMode.Applier<S>>.applyViewGroup(
            constructor: KoshianViewConstructor<V, CL>,
            styleElement: KoshianStyle.StyleElement<V>,
            applierAction: ViewGroupApplier<V, L, CL, S>.() -> Unit
      )
      where V : View,
            L : ViewGroup.LayoutParams,
            CL : ViewGroup.LayoutParams,
            S : KoshianStyle
{
   val view = `$$ApplierInternal`.findViewOrInsertNewAndApplyStyle(
         this, constructor, styleElement, V::class.java)

   val koshian = ViewGroupApplier<V, L, CL, S>(view, context, constructor, applyingIndex = 0)
   koshian.applierAction()
}

inline fun <reified V, L, CL, S>
      Koshian<ViewGroup, *, L, KoshianMode.Applier<S>>.apply(
            name: String,
            constructor: KoshianViewConstructor<V, CL>,
            applierAction: ViewGroupApplier<V, L, CL, S>.() -> Unit
      )
      where V : View,
            CL : ViewGroup.LayoutParams,
            S : KoshianStyle
{
   for (view in `$$ApplierInternal`.findViewByName(`$$koshianInternal$view`, name, V::class.java)) {
      val koshian = ViewGroupApplier<V, L, CL, S>(view, context, constructor, applyingIndex = 0)
      koshian.applierAction()
   }
}

inline fun <reified V, L, CL, S>
      Koshian<ViewGroup, *, L, KoshianMode.Applier<S>>.apply(
            name: String,
            constructor: KoshianViewConstructor<V, CL>,
            styleElement: KoshianStyle.StyleElement<V>,
            applierAction: ViewGroupApplier<V, L, CL, S>.() -> Unit
      )
      where V : View,
            CL : ViewGroup.LayoutParams,
            S : KoshianStyle
{
   for (view in `$$ApplierInternal`.findViewByName(`$$koshianInternal$view`, name, V::class.java)) {
      styleElement.applyStyleTo(view, context)

      val koshian = ViewGroupApplier<V, L, CL, S>(view, context, constructor, applyingIndex = 0)
      koshian.applierAction()
   }
}
