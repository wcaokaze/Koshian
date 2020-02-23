package koshian

import android.content.*
import android.view.*

inline fun <R> koshian(
      context: Context,
      koshianBuilder: Koshian<Nothing, Nothing, ViewGroup.LayoutParams>.() -> R
): R {
   val oldContext = `$$KoshianInternal`.context
   val oldParentConstructor = `$$KoshianInternal`.parentViewConstructor
   `$$KoshianInternal`.context = context
   `$$KoshianInternal`.parentViewConstructor = KoshianRoot.CONSTRUCTOR

   try {
      val koshian = Koshian<Nothing, Nothing, ViewGroup.LayoutParams>(KoshianRoot.INSTANCE)
      return koshian.koshianBuilder()
   } finally {
      `$$KoshianInternal`.context = oldContext
      `$$KoshianInternal`.parentViewConstructor = oldParentConstructor
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
inline class Koshian<out V, out L, out CL>(val `$$koshianInternal$view`: Any?) {
   inline operator fun <C : View>
         KoshianViewConstructor<C>.invoke(buildAction: ViewBuilder<C, CL>.() -> Unit): C
   {
      val view = instantiate(`$$KoshianInternal`.context)

      val parent = `$$koshianInternal$view` as ViewManager
      val parentViewConstructor = `$$KoshianInternal`.parentViewConstructor

      val layoutParams = parentViewConstructor.instantiateLayoutParams()

      parent.addView(view, layoutParams)

      val koshian = ViewBuilder<C, CL>(view)
      koshian.buildAction()
      return view
   }

   inline operator fun <C : View, CCL : ViewGroup.LayoutParams>
         KoshianViewParentConstructor<C, CCL>.invoke(buildAction: ViewGroupBuilder<C, CL, CCL>.() -> Unit): C
   {
      val view = instantiate(`$$KoshianInternal`.context)

      val parent = `$$koshianInternal$view` as ViewManager
      val parentViewConstructor = `$$KoshianInternal`.parentViewConstructor

      val layoutParams = parentViewConstructor.instantiateLayoutParams()

      parent.addView(view, layoutParams)

      `$$KoshianInternal`.parentViewConstructor = this
      val koshian = ViewGroupBuilder<C, CL, CCL>(view)
      koshian.buildAction()
      `$$KoshianInternal`.parentViewConstructor = parentViewConstructor

      return view
   }
}

inline val <V : View> Koshian<V, *, *>.view: V get() {
   @Suppress("UNCHECKED_CAST")
   return `$$koshianInternal$view` as V
}

inline val <L> Koshian<View, L, *>.layout: L get() {
   @Suppress("UNCHECKED_CAST")
   return (`$$koshianInternal$view` as View).layoutParams as L
}
