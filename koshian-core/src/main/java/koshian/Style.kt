package koshian

import android.view.*

abstract class KoshianStyle {
   open fun defaultStyle() {}
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
