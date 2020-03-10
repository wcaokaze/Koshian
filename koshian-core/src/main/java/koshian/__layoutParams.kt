@file:Suppress("UNUSED")
package koshian

import android.view.*

inline val WRAP_CONTENT get() = ViewGroup.LayoutParams.WRAP_CONTENT
inline val MATCH_PARENT get() = ViewGroup.LayoutParams.MATCH_PARENT

val KoshianExt<*, ViewGroup.LayoutParams>.START             inline get() = Gravity.START
val KoshianExt<*, ViewGroup.LayoutParams>.END               inline get() = Gravity.END
val KoshianExt<*, ViewGroup.LayoutParams>.CENTER_HORIZONTAL inline get() = Gravity.CENTER_HORIZONTAL

val KoshianExt<*, ViewGroup.LayoutParams>.TOP             inline get() = Gravity.TOP
val KoshianExt<*, ViewGroup.LayoutParams>.BOTTOM          inline get() = Gravity.BOTTOM
val KoshianExt<*, ViewGroup.LayoutParams>.CENTER_VERTICAL inline get() = Gravity.CENTER_VERTICAL

val KoshianExt<*, ViewGroup.LayoutParams>.START_TOP    inline get() = Gravity.START or Gravity.TOP
val KoshianExt<*, ViewGroup.LayoutParams>.START_BOTTOM inline get() = Gravity.START or Gravity.BOTTOM
val KoshianExt<*, ViewGroup.LayoutParams>.START_CENTER inline get() = Gravity.START or Gravity.CENTER_VERTICAL

val KoshianExt<*, ViewGroup.LayoutParams>.END_TOP    inline get() = Gravity.END or Gravity.TOP
val KoshianExt<*, ViewGroup.LayoutParams>.END_BOTTOM inline get() = Gravity.END or Gravity.BOTTOM
val KoshianExt<*, ViewGroup.LayoutParams>.END_CENTER inline get() = Gravity.END or Gravity.CENTER_VERTICAL

val KoshianExt<*, ViewGroup.LayoutParams>.CENTER_TOP    inline get() = Gravity.CENTER_HORIZONTAL or Gravity.TOP
val KoshianExt<*, ViewGroup.LayoutParams>.CENTER_BOTTOM inline get() = Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM
val KoshianExt<*, ViewGroup.LayoutParams>.CENTER        inline get() = Gravity.CENTER
