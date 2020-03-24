package koshian

import android.view.*

abstract class KoshianStyle {
   open fun defaultStyle() {}
}

inline fun <V : View> V.applyKoshian(
      style: KoshianStyle,
      constructor: KoshianViewConstructor<V>,
      applyAction: ViewBuilder<V, ViewGroup.LayoutParams, KoshianMode.Applier>.() -> Unit
) {
   val oldContext = `$$KoshianInternal`.context
   val oldParentConstructor = `$$KoshianInternal`.parentViewConstructor
   val oldApplyingIndex = `$$ApplierInternal`.applyingIndex
   `$$KoshianInternal`.context = context
   `$$KoshianInternal`.parentViewConstructor = NothingConstructor
   `$$ApplierInternal`.applyingIndex = 0

   style.defaultStyle()

   try {
      val koshian = ViewBuilder<V, ViewGroup.LayoutParams, KoshianMode.Applier>(this)
      `$$StyleInternal`.applyStyle(this)
      koshian.applyAction()
   } finally {
      `$$KoshianInternal`.context = oldContext
      `$$KoshianInternal`.parentViewConstructor = oldParentConstructor
      `$$ApplierInternal`.applyingIndex = oldApplyingIndex
   }
}

inline fun <V : View> createStyle(
      viewConstructor: KoshianViewConstructor<V>,
      crossinline styleAction: ViewBuilder<V, Nothing, KoshianMode.Style>.() -> Unit
) {
   viewConstructor.styleAction = { view: V ->
      val viewBuilder = ViewBuilder<V, Nothing, KoshianMode.Style>(view)
      viewBuilder.styleAction()
      null
   }
}
