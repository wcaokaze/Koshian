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
 * @param V The type of [view]
 * @param L the type of [layout]
 *
 * @param CL
 *   When V is a subtype of ViewGroup, the type of LayoutParams for V.
 *   e.g. When V is FrameLayout, CL is FrameLayout.LayoutParams
 */
@KoshianMarker
class Koshian<out V, out L, out CL, M : KoshianMode>(
      @JvmField val `$$koshianInternal$view`: V,
      @JvmField val context: Context,
      @JvmField val viewConstructor: KoshianViewConstructor<out V, out CL>,
      applyingIndex: Int,
      style: KoshianStyle?
) {
   @JvmField var `$$koshianInternal$applyingIndex`: Int = applyingIndex
   @JvmField var `$$koshianInternal$style`: KoshianStyle? = style

   constructor(
         view: V,
         context: Context,
         viewConstructor: KoshianViewConstructor<out V, out CL>,
   ) : this(view, context, viewConstructor, applyingIndex = -1, style = null)

   @Deprecated("Use dp instead", ReplaceWith("dp")) val Int   .dip: Int inline get() = dp
   @Deprecated("Use dp instead", ReplaceWith("dp")) val Float .dip: Int inline get() = dp
   @Deprecated("Use dp instead", ReplaceWith("dp")) val Double.dip: Int inline get() = dp

   val Int   .dp: Int inline get() = `$$KoshianInternal`.dpToPx(this)
   val Float .dp: Int inline get() = `$$KoshianInternal`.dpToPx(this)
   val Double.dp: Int inline get() = `$$KoshianInternal`.dpToPx(this)

   val Int   .sp: Float inline get() = `$$KoshianInternal`.spToPx(this)
   val Float .sp: Float inline get() = `$$KoshianInternal`.spToPx(this)
   val Double.sp: Float inline get() = `$$KoshianInternal`.spToPx(this)

   val Int   .px: Int inline get() = this
   val Float .px: Int inline get() = this.toInt()
   val Double.px: Int inline get() = this.toInt()

   /**
    * ## In [Creator][KoshianMode.Creator]
    *
    * add this view to the current Koshian-view. (throws if the current
    * Koshian-View is not a ViewGroup)
    *
    * ## In [Applier][KoshianMode.Applier]
    *
    * - If this view is already added into any ViewGroup
    *
    *     check the next view is this view.
    *
    * - If this view is not added into any ViewGroup yet
    *
    *     add this view to the current Koshian-view. (throws if the current
    *     Koshian-View is not a ViewGroup)
    *
    *
    * ## Pseudocode
    * ```
    * when (currentKoshian.mode == Creator) {
    *    Creator -> {
    *       if (this.parent != null) { throw IllegalStateException() }
    *       if (currentKoshian.view !is ViewGroup) { throw IllegalStateException() }
    *
    *       currentKoshian.view.addView(this)
    *    }
    *
    *    Applier -> {
    *       if (this.parent == null) {
    *          if (currentKoshian.view !is ViewGroup) { throw IllegalStateException() }
    *
    *          currentKoshian.view.addView(cursor++, this)
    *          applyAction()
    *       } else {
    *          if (currentKoshian.view !is ViewGroup) { throw IllegalStateException() }
    *          if (currentKoshian.view[++cursor] !== this) { throw AssertionError() }
    *
    *          applyAction()
    *       }
    *    }
    * }
    * ```
    */
   inline operator fun <V>
         V.invoke(
               applierAction: ViewApplier<V, CL, Nothing>.() -> Unit
         )
         where V : View
   {
      `$$ApplierInternal`.invokeViewInKoshian(this@Koshian, this)

      val koshian = ViewApplier<V, CL, Nothing>(this, context, InvokeViewConstructor(this))

      koshian.applierAction()
   }

   /**
    * ## In [Creator][KoshianMode.Creator]
    *
    * add this view to the current Koshian-view. (throws if the current
    * Koshian-View is not a ViewGroup)
    *
    * ## In [Applier][KoshianMode.Applier]
    *
    * - If this view is already added into any ViewGroup
    *
    *     check the next view is this view.
    *
    * - If this view is not added into any ViewGroup yet
    *
    *     add this view to the current Koshian-view. (throws if the current
    *     Koshian-View is not a ViewGroup)
    *
    *
    * ## Pseudocode
    * ```
    * when (currentKoshian.mode == Creator) {
    *    Creator -> {
    *       if (this.parent != null) { throw IllegalStateException() }
    *       if (currentKoshian.view !is ViewGroup) { throw IllegalStateException() }
    *
    *       currentKoshian.view.addView(this)
    *    }
    *
    *    Applier -> {
    *       if (this.parent == null) {
    *          if (currentKoshian.view !is ViewGroup) { throw IllegalStateException() }
    *
    *          currentKoshian.view.addView(cursor++, this)
    *          applyAction()
    *       } else {
    *          if (currentKoshian.view !is ViewGroup) { throw IllegalStateException() }
    *          if (currentKoshian.view[++cursor] !== this) { throw AssertionError() }
    *
    *          applyAction()
    *       }
    *    }
    * }
    * ```
    */
   inline operator fun <A, R>
         A.invoke(
               applierAction: ViewApplier<A, CL, Nothing>.() -> Unit
         ): R
         where A : KoshianApplicable<R>
   {
      @Suppress("UNCHECKED_CAST")
      val mode = `$$ApplierInternal`.invokeViewInKoshian(this@Koshian, this)

      beforeApply(mode)

      val koshian = ViewApplier<A, CL, Nothing>(this, context, InvokeViewConstructor(this))
      koshian.applierAction()

      afterApply(mode)

      return getResult(mode)
   }

   @PublishedApi
   internal class InvokeViewConstructor<V>(private val view: V)
      : KoshianViewConstructor<V, Nothing>
   {
      override fun instantiate(context: Context?): V = view

      override fun instantiateLayoutParams(): Nothing {
         throw UnsupportedOperationException()
      }
   }
}

inline val <V : View> Koshian<V, *, *, *>.view: V
   get() = `$$koshianInternal$view`

@Deprecated(
      "It always fails to apply koshian to the current koshian-view. Did you mean `View {}`?",
      ReplaceWith("View", "koshian.*"))
inline fun <V : View, L> Koshian<V, *, L, *>.view(
      applierAction: ViewApplier<V, L, Nothing>.() -> Unit
) {
   view.invoke(applierAction)
}

inline val <L : ViewGroup.LayoutParams> Koshian<View, L, *, *>.layout: L get() {
   @Suppress("UNCHECKED_CAST")
   return `$$koshianInternal$view`.layoutParams as L
}
