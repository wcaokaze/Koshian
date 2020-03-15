package koshian

import android.content.*
import android.view.*

object NothingConstructor : KoshianViewGroupConstructor<Nothing, Nothing> {
   override fun instantiate(context: Context?) = throw UnsupportedOperationException()
   override fun instantiateLayoutParams()      = throw UnsupportedOperationException()
}

inline fun <V : View> V.applyKoshian(
      applyAction: ViewBuilder<V, ViewGroup.LayoutParams, KoshianMode.Applier>.() -> Unit
) {
   val oldContext = `$$KoshianInternal`.context
   val oldParentConstructor = `$$KoshianInternal`.parentViewConstructor
   `$$KoshianInternal`.context = context
   `$$KoshianInternal`.parentViewConstructor = NothingConstructor

   try {
      val koshian = ViewBuilder<V, ViewGroup.LayoutParams, KoshianMode.Applier>(this)
      koshian.applyAction()
   } finally {
      `$$KoshianInternal`.context = oldContext
      `$$KoshianInternal`.parentViewConstructor = oldParentConstructor
   }
}

inline fun <V : View, L : ViewGroup.LayoutParams> V.applyKoshian(
      constructor: KoshianViewGroupConstructor<V, L>,
      applyAction: ViewGroupBuilder<V, ViewGroup.LayoutParams, L, KoshianMode.Applier>.() -> Unit
) {
   val oldContext = `$$KoshianInternal`.context
   val oldParentConstructor = `$$KoshianInternal`.parentViewConstructor
   `$$KoshianInternal`.context = context
   `$$KoshianInternal`.parentViewConstructor = constructor

   try {
      val koshian = ViewGroupBuilder<V, ViewGroup.LayoutParams, L, KoshianMode.Applier>(this)

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
            constructor: KoshianViewConstructor<V>,
            buildAction: ViewBuilder<V, L, KoshianMode.Applier>.() -> Unit
      )
      where V : View
{
   val parent = `$$koshianInternal$view` as ViewManager

   var view = `$$KoshianInternal`.findView(parent, V::class.java)

   if (view == null) {
      view = `$$KoshianInternal`.addNewView(parent, constructor)
   }

   val koshian = ViewBuilder<V, L, KoshianMode.Applier>(view)
   koshian.buildAction()
}

inline fun <reified V, L>
      Koshian<ViewManager, *, L, KoshianMode.Applier>.apply(
            name: String,
            buildAction: ViewBuilder<V, L, KoshianMode.Applier>.() -> Unit
      )
      where V : View
{
   val parent = `$$koshianInternal$view` as ViewManager

   for (view in `$$KoshianInternal`.findViewByName(parent, name, V::class.java)) {
      val koshian = ViewBuilder<V, L, KoshianMode.Applier>(view)
      koshian.buildAction()
   }
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
         ?: constructor.instantiate(`$$KoshianInternal`.context)

   val koshian = ViewBuilder<V, L, KoshianMode.Applier>(view)

   val oldApplyingIndex = `$$KoshianInternal`.applyingIndex
   `$$KoshianInternal`.applyingIndex = 0
   koshian.applyAction()
   `$$KoshianInternal`.applyingIndex = oldApplyingIndex

   `$$KoshianInternal`.parentViewConstructor = oldParentConstructor
}

inline fun <reified V, L, CL>
      Koshian<ViewManager, *, L, KoshianMode.Applier>.apply(
            name: String,
            constructor: KoshianViewGroupConstructor<V, CL>,
            applyAction: ViewGroupBuilder<V, L, CL, KoshianMode.Applier>.() -> Unit
      )
      where V : View,
            CL : ViewGroup.LayoutParams
{
   val oldParentConstructor = `$$KoshianInternal`.parentViewConstructor
   `$$KoshianInternal`.parentViewConstructor = constructor

   val parent = `$$koshianInternal$view` as ViewManager
   val oldApplyingIndex = `$$KoshianInternal`.applyingIndex

   for (view in `$$KoshianInternal`.findViewByName(parent, name, V::class.java)) {
      val koshian = ViewBuilder<V, L, KoshianMode.Applier>(view)

      `$$KoshianInternal`.applyingIndex = 0
      koshian.applyAction()
   }

   `$$KoshianInternal`.applyingIndex = oldApplyingIndex
   `$$KoshianInternal`.parentViewConstructor = oldParentConstructor
}
