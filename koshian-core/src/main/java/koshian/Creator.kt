package koshian

import android.content.*
import android.view.*

inline operator fun <V, L, C, CL>
      Koshian<V, L, CL, KoshianMode.Creator>.invoke(
            constructor: (Context?) -> C,
            buildAction: ViewBuilder<C, CL, KoshianMode.Creator>.() -> Unit
      ): C
      where V : ViewManager,
            C : View
{
   val view = constructor(`$$KoshianInternal`.context)

   val parent = `$$koshianInternal$view` as ViewManager
   val parentLayoutParamsProvider = `$$KoshianInternal`.layoutParamsProvider
   val layoutParams = parentLayoutParamsProvider() as ViewGroup.LayoutParams?

   parent.addView(view, layoutParams)

   val koshian = ViewBuilder<C, CL, KoshianMode.Creator>(view)
   koshian.buildAction()
   return view
}

inline operator fun <V, L, C, CL, CCL>
      Koshian<V, L, CL, KoshianMode.Creator>.invoke(
            constructor: (Context?) -> C,
            noinline layoutParamsProvider: () -> CCL,
            buildAction: ViewGroupBuilder<C, CL, CCL, KoshianMode.Creator>.() -> Unit
      ): C
      where V : ViewManager,
            C : View,
            CCL : ViewGroup.LayoutParams
{
   val view = constructor(`$$KoshianInternal`.context)

   val parent = `$$koshianInternal$view` as ViewManager
   val parentLayoutParamsProvider = `$$KoshianInternal`.layoutParamsProvider
   val layoutParams = parentLayoutParamsProvider() as ViewGroup.LayoutParams?

   parent.addView(view, layoutParams)

   `$$KoshianInternal`.layoutParamsProvider = layoutParamsProvider
   val koshian = ViewGroupBuilder<C, CL, CCL, KoshianMode.Creator>(view)
   koshian.buildAction()
   `$$KoshianInternal`.layoutParamsProvider = parentLayoutParamsProvider

   return view
}
