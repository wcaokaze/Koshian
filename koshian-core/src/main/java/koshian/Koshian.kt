package koshian

import android.content.*
import android.view.*
import android.widget.*

fun <R> koshian(context: Context, koshianBuilder: Koshian<Unit>.() -> R): R {
   val oldContext = `$$KoshianInternal`.context
   `$$KoshianInternal`.context = context

   try {
      val koshian = Koshian(Unit)
      return koshian.koshianBuilder()
   } finally {
      `$$KoshianInternal`.context = oldContext
   }
}

class Koshian<out V>(val `$$koshianInternal$view`: V)

val <V : View> Koshian<V>.view: V get() = `$$koshianInternal$view`

fun Koshian<*>.textView(koshianViewBuilder: Koshian<TextView>.() -> Unit): TextView {
   val view = TextView(`$$KoshianInternal`.context)
   val koshian = Koshian(view)
   koshian.koshianViewBuilder()
   return view
}
