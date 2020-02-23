package koshian

import android.content.*
import android.view.*

inline fun <R> koshian(
      context: Context,
      koshianBuilder: Koshian<Nothing, Nothing, ViewGroup.LayoutParams, KoshianMode.Create>.() -> R
): R {
   val oldContext = `$$KoshianInternal`.context
   val oldLayoutParamsProvider = `$$KoshianInternal`.layoutParamsProvider
   `$$KoshianInternal`.context = context
   `$$KoshianInternal`.layoutParamsProvider = { ViewGroup.LayoutParams(WRAP_CONTENT, WRAP_CONTENT) }

   try {
      val koshian = Koshian<Nothing, Nothing, ViewGroup.LayoutParams, KoshianMode.Create>(KoshianRoot.INSTANCE)
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

inline operator fun <V, L, C, CL, M>
      Koshian<V, L, CL, M>.invoke(
            constructor: (Context?) -> C,
            buildAction: ViewBuilder<C, CL, M>.() -> Unit
      ): C
      where V : ViewManager,
            C : View,
            M : KoshianMode
{
   val view = constructor(`$$KoshianInternal`.context)

   val parent = `$$koshianInternal$view` as ViewManager
   val parentLayoutParamsProvider = `$$KoshianInternal`.layoutParamsProvider
   val layoutParams = parentLayoutParamsProvider() as ViewGroup.LayoutParams?

   parent.addView(view, layoutParams)

   val koshian = ViewBuilder<C, CL, M>(view)
   koshian.buildAction()
   return view
}

inline operator fun <V, L, C, CL, CCL, M>
      Koshian<V, L, CL, M>.invoke(
            constructor: (Context?) -> C,
            noinline layoutParamsProvider: () -> CCL,
            buildAction: ViewGroupBuilder<C, CL, CCL, M>.() -> Unit
      ): C
      where V : ViewManager,
            C : View,
            CCL : ViewGroup.LayoutParams,
            M : KoshianMode
{
   val view = constructor(`$$KoshianInternal`.context)

   val parent = `$$koshianInternal$view` as ViewManager
   val parentLayoutParamsProvider = `$$KoshianInternal`.layoutParamsProvider
   val layoutParams = parentLayoutParamsProvider() as ViewGroup.LayoutParams?

   parent.addView(view, layoutParams)

   `$$KoshianInternal`.layoutParamsProvider = layoutParamsProvider
   val koshian = ViewGroupBuilder<C, CL, CCL, M>(view)
   koshian.buildAction()
   `$$KoshianInternal`.layoutParamsProvider = parentLayoutParamsProvider

   return view
}

inline val <V : View> Koshian<V, *, *, *>.view: V get() {
   @Suppress("UNCHECKED_CAST")
   return `$$koshianInternal$view` as V
}

inline val <L> Koshian<View, L, *, *>.layout: L get() {
   @Suppress("UNCHECKED_CAST")
   return (`$$koshianInternal$view` as View).layoutParams as L
}
