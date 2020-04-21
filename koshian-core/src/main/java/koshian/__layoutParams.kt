/*
 * Copyright 2020 wcaokaze
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@file:Suppress("UNUSED")
package koshian

import android.os.*
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

var ViewGroup.MarginLayoutParams.margins: Int
   @Deprecated(message = "The getter always throws an Exception", level = DeprecationLevel.ERROR)
   get() = throw UnsupportedOperationException()
   set(value) {
      if (Build.VERSION.SDK_INT >= 17) {
         marginStart = value
         topMargin = value
         marginEnd = value
         bottomMargin = value
      } else {
         leftMargin = value
         topMargin = value
         rightMargin = value
         bottomMargin = value
      }
   }

var ViewGroup.MarginLayoutParams.horizontalMargin: Int
   @Deprecated(message = "The getter always throws an Exception", level = DeprecationLevel.ERROR)
   get() = throw UnsupportedOperationException()
   set(value) {
      if (Build.VERSION.SDK_INT >= 17) {
         marginStart = value
         marginEnd = value
      } else {
         leftMargin = value
         rightMargin = value
      }
   }

var ViewGroup.MarginLayoutParams.verticalMargin: Int
   @Deprecated(message = "The getter always throws an Exception", level = DeprecationLevel.ERROR)
   get() = throw UnsupportedOperationException()
   inline set(value) {
      topMargin = value
      bottomMargin = value
   }
