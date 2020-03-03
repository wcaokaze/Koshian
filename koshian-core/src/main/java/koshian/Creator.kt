package koshian

import android.content.*
import android.view.*

inline fun <R> koshian(
      context: Context,
      koshianBuilder: Koshian<Nothing, Nothing, ViewGroup.LayoutParams, KoshianMode.Creator>.() -> R
): R {
   val oldContext = `$$KoshianInternal`.context
   val oldParentConstructor = `$$KoshianInternal`.parentViewConstructor
   `$$KoshianInternal`.context = context
   `$$KoshianInternal`.parentViewConstructor = KoshianRoot.CONSTRUCTOR

   try {
      val koshian = Koshian<Nothing, Nothing, ViewGroup.LayoutParams, KoshianMode.Creator>(KoshianRoot.INSTANCE)
      return koshian.koshianBuilder()
   } finally {
      `$$KoshianInternal`.context = oldContext
      `$$KoshianInternal`.parentViewConstructor = oldParentConstructor
   }
}

inline fun <P, L, R>
      P.addView(
            parentConstructor: KoshianViewGroupConstructor<P, L>,
            buildAction: Koshian<P, Nothing, L, KoshianMode.Creator>.() -> R
      ): R
      where P : ViewGroup,
            L : ViewGroup.LayoutParams
{
   val oldContext = `$$KoshianInternal`.context
   val oldParentConstructor = `$$KoshianInternal`.parentViewConstructor
   `$$KoshianInternal`.context = context
   `$$KoshianInternal`.parentViewConstructor = parentConstructor

   try {
      val koshian = Koshian<P, Nothing, L, KoshianMode.Creator>(this)
      return koshian.buildAction()
   } finally {
      `$$KoshianInternal`.context = oldContext
      `$$KoshianInternal`.parentViewConstructor = oldParentConstructor
   }
}

inline fun <V, L, C, CL>
      Koshian<V, L, CL, KoshianMode.Creator>.create(
            constructor: KoshianViewConstructor<C>,
            buildAction: ViewBuilder<C, CL, KoshianMode.Creator>.() -> Unit
      ): C
      where V : ViewManager,
            C : View
{
   val view = constructor.instantiate(`$$KoshianInternal`.context)

   val parent = `$$koshianInternal$view` as ViewManager
   val parentViewConstructor = `$$KoshianInternal`.parentViewConstructor
   val layoutParams = parentViewConstructor.instantiateLayoutParams()

   parent.addView(view, layoutParams)

   val koshian = ViewBuilder<C, CL, KoshianMode.Creator>(view)
   koshian.buildAction()
   return view
}

inline fun <V, L, C, CL, CCL>
      Koshian<V, L, CL, KoshianMode.Creator>.create(
            constructor: KoshianViewGroupConstructor<C, CCL>,
            buildAction: ViewGroupBuilder<C, CL, CCL, KoshianMode.Creator>.() -> Unit
      ): C
      where V : ViewManager,
            C : View,
            CCL : ViewGroup.LayoutParams
{
   val view = constructor.instantiate(`$$KoshianInternal`.context)

   val parent = `$$koshianInternal$view` as ViewManager
   val parentViewConstructor = `$$KoshianInternal`.parentViewConstructor

   val layoutParams = parentViewConstructor.instantiateLayoutParams()

   parent.addView(view, layoutParams)

   `$$KoshianInternal`.parentViewConstructor = constructor
   val koshian = ViewGroupBuilder<C, CL, CCL, KoshianMode.Creator>(view)
   koshian.buildAction()
   `$$KoshianInternal`.parentViewConstructor = parentViewConstructor

   return view
}
