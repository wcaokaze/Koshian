package koshian

import android.content.*
import android.view.*
import android.widget.*

inline fun <R> koshian(context: Context, koshianBuilder: Koshian<Nothing>.() -> R): R {
   val oldContext = `$$KoshianInternal`.context
   `$$KoshianInternal`.context = context

   try {
      val koshian = Koshian<Nothing>(Unit)
      return koshian.koshianBuilder()
   } finally {
      `$$KoshianInternal`.context = oldContext
   }
}

inline class Koshian<out V>(val `$$koshianInternal$view`: Any?)

inline val <V : View> Koshian<V>.view: V get() {
   @Suppress("UNCHECKED_CAST")
   return `$$koshianInternal$view` as V
}

inline fun Koshian<*>.textView(koshianViewBuilder: Koshian<TextView>.() -> Unit): TextView {
   val view = TextView(`$$KoshianInternal`.context)
   val koshian = Koshian<TextView>(view)
   koshian.koshianViewBuilder()
   return view
}
