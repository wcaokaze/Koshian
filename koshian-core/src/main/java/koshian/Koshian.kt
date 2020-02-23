package koshian

import android.content.*
import android.view.*

inline fun <R> koshian(
      context: Context,
      koshianBuilder: Koshian<Nothing, Nothing, ViewGroup.LayoutParams, KoshianMode.Creator>.() -> R
): R {
   val oldContext = `$$KoshianInternal`.context
   val oldLayoutParamsProvider = `$$KoshianInternal`.layoutParamsProvider
   `$$KoshianInternal`.context = context
   `$$KoshianInternal`.layoutParamsProvider = { ViewGroup.LayoutParams(WRAP_CONTENT, WRAP_CONTENT) }

   try {
      val koshian = Koshian<Nothing, Nothing, ViewGroup.LayoutParams, KoshianMode.Creator>(KoshianRoot.INSTANCE)
      return koshian.koshianBuilder()
   } finally {
      `$$KoshianInternal`.context = oldContext
      `$$KoshianInternal`.layoutParamsProvider = oldLayoutParamsProvider
   }
}

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

inline val <V : View> Koshian<V, *, *, *>.view: V get() {
   @Suppress("UNCHECKED_CAST")
   return `$$koshianInternal$view` as V
}

inline val <L> Koshian<View, L, *, *>.layout: L get() {
   @Suppress("UNCHECKED_CAST")
   return (`$$koshianInternal$view` as View).layoutParams as L
}
