package koshian

import android.content.*
import android.view.*

fun <R> koshian(context: Context, koshianBuilder: Koshian.() -> R): R {
   val oldContext = `$$KoshianInternal`.context
   `$$KoshianInternal`.context = context

   try {
      val koshian = Koshian()
      return koshian.koshianBuilder()
   } finally {
      `$$KoshianInternal`.context = oldContext
   }
}

class Koshian

fun Koshian.view(koshianViewBuilder: () -> Unit): View {
   return View(`$$KoshianInternal`.context)
}
