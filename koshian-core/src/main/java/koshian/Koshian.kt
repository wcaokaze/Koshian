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

/**
 * @param V The type of [view]
 * @param L the type of [layout]
 *
 * @param CL
 *   When V is a subtype of ViewGroup, the type of LayoutParams for V.
 *   e.g. When V is FrameLayout, CL is FrameLayout.LayoutParams
 */
@KoshianMarker
inline class Koshian<out V, out L, out CL, M : KoshianMode>
      (val `$$koshianInternal$view`: Any?)
{
   val Int   .dip: Int inline get() = `$$KoshianInternal`.dipToPx(this)
   val Float .dip: Int inline get() = `$$KoshianInternal`.dipToPx(this)
   val Double.dip: Int inline get() = `$$KoshianInternal`.dipToPx(this)

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
               applyAction: ViewBuilder<V, CL, KoshianMode.Applier>.() -> Unit
         )
         where V : View
   {
      `$$ApplierInternal`.invokeViewInKoshian(`$$koshianInternal$view`, this)

      val koshian = ViewBuilder<V, CL, KoshianMode.Applier>(this)
      koshian.applyAction()
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
   inline operator fun <A>
         A.invoke(
               applyAction: ViewBuilder<A, CL, KoshianMode.Applier>.() -> Unit
         )
         where A : KoshianApplicable
   {
      `$$ApplierInternal`.invokeViewInKoshian(`$$koshianInternal$view`, this)

      val koshian = ViewBuilder<A, CL, KoshianMode.Applier>(this)
      koshian.applyAction()
   }
}

inline val <V : View> Koshian<V, *, *, *>.view: V get() {
   @Suppress("UNCHECKED_CAST")
   return `$$koshianInternal$view` as V
}

@Deprecated(
      "It always fails to apply koshian to the current koshian-view. Did you mean `View {}`?",
      ReplaceWith("View", "koshian.*"))
inline fun <V : View, L> Koshian<V, *, L, *>.view(
      applyAction: ViewBuilder<V, L, KoshianMode.Applier>.() -> Unit
) {
   view.invoke(applyAction)
}

inline val <L> Koshian<View, L, *, *>.layout: L get() {
   @Suppress("UNCHECKED_CAST")
   return (`$$koshianInternal$view` as View).layoutParams as L
}
