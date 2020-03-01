package koshian

import android.content.*
import android.view.*

object NothingConstructor : KoshianViewGroupConstructor<Nothing, Nothing> {
   override fun instantiate(context: Context?) = throw UnsupportedOperationException()
   override fun instantiateLayoutParams()      = throw UnsupportedOperationException()
}

inline fun <V : View> applyKoshian(
      view: V,
      applyAction: ViewBuilder<V, ViewGroup.LayoutParams, KoshianMode.Applier>.() -> Unit
) {
   val oldContext = `$$KoshianInternal`.context
   val oldParentConstructor = `$$KoshianInternal`.parentViewConstructor
   `$$KoshianInternal`.context = view.context
   `$$KoshianInternal`.parentViewConstructor = NothingConstructor

   try {
      val koshian = ViewBuilder<V, ViewGroup.LayoutParams, KoshianMode.Applier>(view)
      koshian.applyAction()
   } finally {
      `$$KoshianInternal`.context = oldContext
      `$$KoshianInternal`.parentViewConstructor = oldParentConstructor
   }
}

inline fun <V : View, L : ViewGroup.LayoutParams> applyKoshian(
      view: V,
      constructor: KoshianViewGroupConstructor<V, L>,
      applyAction: ViewGroupBuilder<V, ViewGroup.LayoutParams, L, KoshianMode.Applier>.() -> Unit
) {
   val oldContext = `$$KoshianInternal`.context
   val oldParentConstructor = `$$KoshianInternal`.parentViewConstructor
   `$$KoshianInternal`.context = view.context
   `$$KoshianInternal`.parentViewConstructor = constructor

   try {
      val koshian = ViewGroupBuilder<V, ViewGroup.LayoutParams, L, KoshianMode.Applier>(view)

      val oldApplyingIndex = `$$KoshianInternal`.applyingIndex
      `$$KoshianInternal`.applyingIndex = 0
      koshian.applyAction()
      `$$KoshianInternal`.applyingIndex = oldApplyingIndex
   } finally {
      `$$KoshianInternal`.context = oldContext
      `$$KoshianInternal`.parentViewConstructor = oldParentConstructor
   }
}

inline fun <reified V, L>
      Koshian<ViewManager, *, L, KoshianMode.Applier>.apply(
            buildAction: ViewBuilder<V, L, KoshianMode.Applier>.() -> Unit
      )
      where V : View
{
   val parent = `$$koshianInternal$view` as ViewManager
   val view = `$$KoshianInternal`.findView(parent, V::class.java)
         ?: throw IllegalStateException()

   val koshian = ViewBuilder<V, L, KoshianMode.Applier>(view)
   koshian.buildAction()
}

inline fun <reified V, L, CL>
      Koshian<ViewManager, *, L, KoshianMode.Applier>.apply(
            constructor: KoshianViewGroupConstructor<V, CL>,
            applyAction: ViewGroupBuilder<V, L, CL, KoshianMode.Applier>.() -> Unit
      )
      where V : View,
            CL : ViewGroup.LayoutParams
{
   val oldParentConstructor = `$$KoshianInternal`.parentViewConstructor
   `$$KoshianInternal`.parentViewConstructor = constructor

   val parent = `$$koshianInternal$view` as ViewManager
   val view = `$$KoshianInternal`.findView(parent, V::class.java)
         ?: throw IllegalStateException()

   val koshian = ViewBuilder<V, L, KoshianMode.Applier>(view)

   val oldApplyingIndex = `$$KoshianInternal`.applyingIndex
   `$$KoshianInternal`.applyingIndex = 0
   koshian.applyAction()
   `$$KoshianInternal`.applyingIndex = oldApplyingIndex

   `$$KoshianInternal`.parentViewConstructor = oldParentConstructor
}
