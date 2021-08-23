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

/**
 * extend this class and define your styles.
 * ```kotlin
 * class LightStyle : KoshianStyle() {
 *    val primaryText = TextView {
 *       view.textColor = 0x2196f3.opaque
 *       view.typeface = BOLD
 *    }
 * }
 * ```
 * We can use styles in [applyKoshian].
 * ```kotlin
 * layout.applyKoshian(LightStyle()) {
 *    TextView(style.primaryText) {
 *       view.text = "User"
 *    }
 * }
 * ```
 *
 * We can also define defaultStyle.
 * ```kotlin
 * class LightStyle : KoshianStyle() {
 *    override fun defaultStyle() {
 *       TextView {
 *          view.setPadding(8.dp)
 *          view.textSizeSp = 12
 *          view.lineSpace = 4.dp
 *       }
 *    }
 *
 *    val primaryText = TextView {
 *       view.textColor = 0x2196f3.opaque
 *       view.typeface = BOLD
 *    }
 * }
 * ```
 * ```kotlin
 * layout.applyKoshian(LightStyle()) {
 *    TextView(style.primaryText) {
 *       // The TextView will be applied defaultStyle, `primaryText`, and this lambda.
 *       view.text = "User"
 *    }
 * }
 * ```
 */
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
      fun applyStyleTo(view: V, context: Context)
   }

   inline fun <reified V : View> createStyleElement(
         crossinline styleAction: ViewStyle<V>.() -> Unit
   ): StyleElement<V> {
      val styleElement = object : StyleElement<V> {
         override fun applyStyleTo(view: V, context: Context) {
            val viewBuilder = ViewStyle<V>(view, context)
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
            applierAction: ViewApplier<V, ViewGroup.LayoutParams, S>.() -> Unit
      )
      where V : View, S : KoshianStyle
{
   val oldParentConstructor = `$$KoshianInternal`.parentViewConstructor
   val oldApplyingIndex = `$$ApplierInternal`.applyingIndex
   val oldStyle = `$$StyleInternal`.style
   `$$KoshianInternal`.parentViewConstructor = NothingConstructor
   `$$ApplierInternal`.applyingIndex = 0
   `$$StyleInternal`.style = style

   style.initializeDefaultStyle()

   try {
      val koshian = ViewApplier<V, ViewGroup.LayoutParams, S>(this, context)
      `$$StyleInternal`.applyCurrentStyleRecursive(this, context)
      koshian.applierAction()
   } finally {
      `$$KoshianInternal`.parentViewConstructor = oldParentConstructor
      `$$ApplierInternal`.applyingIndex = oldApplyingIndex
      `$$StyleInternal`.style = oldStyle
   }
}

inline fun <V, L, S>
      V.applyKoshian(
            style: S,
            constructor: KoshianViewGroupConstructor<V, L>,
            applierAction: ViewGroupApplier<V, ViewGroup.LayoutParams, L, S>.() -> Unit
      )
      where V : View, L : ViewGroup.LayoutParams, S : KoshianStyle
{
   val oldParentConstructor = `$$KoshianInternal`.parentViewConstructor
   val oldApplyingIndex = `$$ApplierInternal`.applyingIndex
   val oldStyle = `$$StyleInternal`.style
   `$$KoshianInternal`.parentViewConstructor = constructor
   `$$ApplierInternal`.applyingIndex = 0
   `$$StyleInternal`.style = style

   style.initializeDefaultStyle()

   try {
      val koshian = ViewGroupApplier<V, ViewGroup.LayoutParams, L, S>(this, context)
      `$$StyleInternal`.applyCurrentStyleRecursive(this, context)
      koshian.applierAction()
   } finally {
      `$$KoshianInternal`.parentViewConstructor = oldParentConstructor
      `$$ApplierInternal`.applyingIndex = oldApplyingIndex
      `$$StyleInternal`.style = oldStyle
   }
}
