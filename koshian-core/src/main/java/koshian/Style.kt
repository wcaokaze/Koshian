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

import android.view.*

@KoshianMarker
abstract class KoshianStyle {
   private var isCreatingDefaultStyle = false

   @JvmField
   val `$$koshianInternal$elementMap` = HashMap<Class<out View>, StyleElement<*>>()

   open fun defaultStyle() {}

   @PublishedApi
   internal fun initializeDefaultStyle() {
      try {
         isCreatingDefaultStyle = true
         `$$koshianInternal$elementMap`.clear()
         defaultStyle()
      } finally {
         isCreatingDefaultStyle = false
      }
   }

   interface StyleElement<V : View> {
      fun applyStyleTo(view: V)
   }

   inline fun <reified V : View> createStyleElement(
         crossinline styleAction: ViewBuilder<V, Nothing, KoshianMode.Style>.() -> Unit
   ): StyleElement<V> {
      val styleElement = object : StyleElement<V> {
         override fun applyStyleTo(view: V) {
            val viewBuilder = ViewBuilder<V, Nothing, KoshianMode.Style>(view)
            viewBuilder.styleAction()
         }
      }

      putIntoMapIfDefaultStyle(V::class.java, styleElement)
      return styleElement
   }

   @PublishedApi
   internal fun <V : View> putIntoMapIfDefaultStyle(
         viewClass: Class<V>,
         styleElement: StyleElement<V>
   ) {
      if (isCreatingDefaultStyle) {
         `$$koshianInternal$elementMap`[viewClass] = styleElement
      }
   }
}

inline val <S : KoshianStyle>
      (@Suppress("UNUSED") Koshian<*, *, *, KoshianMode.Applier<S>>).style: S
   get() {
      @Suppress("UNCHECKED_CAST")
      return `$$StyleInternal`.style as S
   }

inline fun <V, S>
      V.applyKoshian(
            style: S,
            applyAction: ViewBuilder<V, ViewGroup.LayoutParams, KoshianMode.Applier<S>>.() -> Unit
      )
      where V : View, S : KoshianStyle
{
   val oldContext = `$$KoshianInternal`.context
   val oldParentConstructor = `$$KoshianInternal`.parentViewConstructor
   val oldApplyingIndex = `$$ApplierInternal`.applyingIndex
   val oldStyle = `$$StyleInternal`.style
   `$$KoshianInternal`.context = context
   `$$KoshianInternal`.parentViewConstructor = NothingConstructor
   `$$ApplierInternal`.applyingIndex = 0
   `$$StyleInternal`.style = style

   style.initializeDefaultStyle()

   try {
      val koshian = ViewBuilder<V, ViewGroup.LayoutParams, KoshianMode.Applier<S>>(this)
      `$$StyleInternal`.applyCurrentStyleRecursive(this)
      koshian.applyAction()
   } finally {
      `$$KoshianInternal`.context = oldContext
      `$$KoshianInternal`.parentViewConstructor = oldParentConstructor
      `$$ApplierInternal`.applyingIndex = oldApplyingIndex
      `$$StyleInternal`.style = oldStyle
   }
}

inline fun <V, L, S>
      V.applyKoshian(
            style: S,
            constructor: KoshianViewGroupConstructor<V, L>,
            applyAction: ViewGroupBuilder<V, ViewGroup.LayoutParams, L, KoshianMode.Applier<S>>.() -> Unit
      )
      where V : View, L : ViewGroup.LayoutParams, S : KoshianStyle
{
   val oldContext = `$$KoshianInternal`.context
   val oldParentConstructor = `$$KoshianInternal`.parentViewConstructor
   val oldApplyingIndex = `$$ApplierInternal`.applyingIndex
   val oldStyle = `$$StyleInternal`.style
   `$$KoshianInternal`.context = context
   `$$KoshianInternal`.parentViewConstructor = constructor
   `$$ApplierInternal`.applyingIndex = 0
   `$$StyleInternal`.style = style

   style.initializeDefaultStyle()

   try {
      val koshian = ViewGroupBuilder<V, ViewGroup.LayoutParams, L, KoshianMode.Applier<S>>(this)
      `$$StyleInternal`.applyCurrentStyleRecursive(this)
      koshian.applyAction()
   } finally {
      `$$KoshianInternal`.context = oldContext
      `$$KoshianInternal`.parentViewConstructor = oldParentConstructor
      `$$ApplierInternal`.applyingIndex = oldApplyingIndex
      `$$StyleInternal`.style = oldStyle
   }
}
