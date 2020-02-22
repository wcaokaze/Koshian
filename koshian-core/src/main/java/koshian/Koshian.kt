package koshian

import android.content.*
import android.view.*

fun <R> koshian(context: Context, koshianBuilder: Koshian.() -> R): R {
   val koshian = Koshian(context)
   return koshian.koshianBuilder()
}

class Koshian(val context: Context)

fun Koshian.view(koshianViewBuilder: () -> Unit): View {
   return View(context)
}
