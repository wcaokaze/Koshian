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

import android.content.Context
import android.graphics.Typeface
import android.text.TextUtils
import android.util.TypedValue
import android.view.*
import android.widget.TextView
import kotlin.contracts.*

object TextViewConstructor : KoshianViewConstructor<TextView>() {
   override fun instantiate(context: Context?) = TextView(context)
}

/**
 * creates a new TextView and adds it into this ViewGroup.
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Creator>.TextView(
      buildAction: ViewBuilder<TextView, L, KoshianMode.Creator>.() -> Unit
): TextView {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return create(TextViewConstructor, buildAction)
}

/**
 * creates a new TextView with name, and adds it into this ViewGroup.
 *
 * The name can be referenced in [applyKoshian]
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Creator>.TextView(
      name: String,
      buildAction: ViewBuilder<TextView, L, KoshianMode.Creator>.() -> Unit
): TextView {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return create(name, TextViewConstructor, buildAction)
}

/**
 * If the next View is a TextView, applies Koshian to it.
 *
 * Otherwise, creates a new TextView and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Applier>.TextView(
      buildAction: ViewBuilder<TextView, L, KoshianMode.Applier>.() -> Unit
) {
   apply(TextViewConstructor, buildAction)
}

/**
 * Applies Koshian to all TextViews that are named the specified in this ViewGroup.
 * If there are no TextViews named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Applier>.TextView(
      name: String,
      buildAction: ViewBuilder<TextView, L, KoshianMode.Applier>.() -> Unit
) {
   apply(name, buildAction)
}

/**
 * applies Koshian DSL to this TextView.
 */
inline fun TextView.applyKoshian(
      style: KoshianStyle,
      applyAction: ViewBuilder<TextView, ViewGroup.LayoutParams, KoshianMode.Applier>.() -> Unit
) {
   applyKoshian(style, TextViewConstructor, applyAction)
}

/**
 * registers a style applier function into this [KoshianStyle].
 *
 * Styles can be applied via [applyKoshian]
 */
@Suppress("FunctionName")
fun KoshianStyle.TextView(
      styleAction: ViewBuilder<TextView, Nothing, KoshianMode.Applier>.() -> Unit
) {
   TextViewConstructor.styleAction = styleAction
}

var TextView.textColor: Int
   @Deprecated(message = "The getter always throws an Exception", level = DeprecationLevel.ERROR)
   get() = throw UnsupportedOperationException()
   inline set(value) = setTextColor(value)

var TextView.textSizePx: Int
   @Deprecated(message = "The getter always throws an Exception", level = DeprecationLevel.ERROR)
   get() = throw UnsupportedOperationException()
   inline set(value) = setTextSize(TypedValue.COMPLEX_UNIT_PX, value.toFloat())

var TextView.textSizeDip: Int
   @Deprecated(message = "The getter always throws an Exception", level = DeprecationLevel.ERROR)
   get() = throw UnsupportedOperationException()
   inline set(value) = setTextSize(TypedValue.COMPLEX_UNIT_DIP, value.toFloat())

var TextView.textSizeSp: Int
   @Deprecated(message = "The getter always throws an Exception", level = DeprecationLevel.ERROR)
   get() = throw UnsupportedOperationException()
   inline set(value) = setTextSize(TypedValue.COMPLEX_UNIT_SP, value.toFloat())

val KoshianExt<TextView, *>.DEFAULT_TYPEFACE: Typeface inline get() = Typeface.DEFAULT
val KoshianExt<TextView, *>.BOLD:             Typeface inline get() = Typeface.DEFAULT_BOLD
val KoshianExt<TextView, *>.MONOSPACE:        Typeface inline get() = Typeface.MONOSPACE

val KoshianExt<TextView, *>.TRUNCATE_AT_START:   TextUtils.TruncateAt inline get() = TextUtils.TruncateAt.START
val KoshianExt<TextView, *>.TRUNCATE_AT_MARQUEE: TextUtils.TruncateAt inline get() = TextUtils.TruncateAt.MARQUEE
val KoshianExt<TextView, *>.TRUNCATE_AT_MIDDLE:  TextUtils.TruncateAt inline get() = TextUtils.TruncateAt.MIDDLE
val KoshianExt<TextView, *>.TRUNCATE_AT_END:     TextUtils.TruncateAt inline get() = TextUtils.TruncateAt.END
