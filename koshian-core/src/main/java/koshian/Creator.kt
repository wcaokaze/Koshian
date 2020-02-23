package koshian

import android.content.*
import android.view.*

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
