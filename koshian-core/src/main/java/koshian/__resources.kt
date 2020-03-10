@file:Suppress("UNUSED")
package koshian

import android.graphics.*
import android.graphics.drawable.*
import android.view.*
import androidx.annotation.*

@Suppress("nothing_to_inline")
inline fun KoshianExt<View, *>.drawable(@DrawableRes resId: Int): Drawable
      = (`$$koshianInternal$view` as View).context.resources.getDrawable(resId)!!

fun KoshianExt<View, *>.drawable(@DrawableRes resId: Int, @ColorInt color: Int): Drawable {
   val drawable = (`$$koshianInternal$view` as View).context.resources.getDrawable(resId)!!.mutate()
   drawable.setColorFilter(color, PorterDuff.Mode.SRC_IN)

   return drawable
}

@Suppress("nothing_to_inline")
inline fun KoshianExt<View, *>.string(@StringRes resId: Int): String
      = (`$$koshianInternal$view` as View).context.getString(resId)

@Suppress("nothing_to_inline")
inline fun KoshianExt<View, *>.string(@StringRes resId: Int, vararg formatArgs: Any?): String
      = (`$$koshianInternal$view` as View).context.getString(resId, *formatArgs)

val Int.opaque: Int
   @ColorInt get() = (this and 0xffffff) or (0xff shl 24)

/**
 * @param opacity 0.0 .. 1.0
 */
infix fun Int.opacity(opacity: Double): Int {
   val alpha = (0xff * opacity).toInt().coerceIn(0x00, 0xff)
   return (this and 0xffffff) or (alpha shl 24)
}
