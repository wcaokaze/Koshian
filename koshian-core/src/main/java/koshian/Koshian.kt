package koshian

import android.view.*

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
{
   val Int   .dip: Int inline get() = `$$KoshianInternal`.dipToPx(this)
   val Float .dip: Int inline get() = `$$KoshianInternal`.dipToPx(this)
   val Double.dip: Int inline get() = `$$KoshianInternal`.dipToPx(this)

   val Int   .px: Int inline get() = this
   val Float .px: Int inline get() = this.toInt()
   val Double.px: Int inline get() = this.toInt()

   inline operator fun <V>
         V.invoke(
               applyAction: ViewBuilder<V, CL, KoshianMode.Applier>.() -> Unit
         )
         where V : View
   {
      `$$KoshianInternal`.assertNextView(`$$koshianInternal$view`, this)

      val koshian = ViewBuilder<V, CL, KoshianMode.Applier>(this)
      koshian.applyAction()
   }
}

inline val <V : View> Koshian<V, *, *, *>.view: V get() {
   @Suppress("UNCHECKED_CAST")
   return `$$koshianInternal$view` as V
}

inline val <L> Koshian<View, L, *, *>.layout: L get() {
   @Suppress("UNCHECKED_CAST")
   return (`$$koshianInternal$view` as View).layoutParams as L
}
