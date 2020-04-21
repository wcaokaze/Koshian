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
import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import kotlin.contracts.*

object ViewConstructor : KoshianViewConstructor<View> {
   override fun instantiate(context: Context?) = View(context)
}

/**
 * creates a new View and adds it into this ViewGroup.
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> CreatorParent<L>.View(
      creatorAction: ViewCreator<View, L>.() -> Unit
): View {
   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }
   return create(ViewConstructor, creatorAction)
}

/**
 * creates a new View with name, and adds it into this ViewGroup.
 *
 * The name can be referenced in [applyKoshian]
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> CreatorParent<L>.View(
      name: String,
      creatorAction: ViewCreator<View, L>.() -> Unit
): View {
   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }
   return create(name, ViewConstructor, creatorAction)
}

/**
 * If the next View is a View, applies Koshian to it.
 *
 * Otherwise, creates a new View and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L, S : KoshianStyle>
      ApplierParent<L, S>.View(
            applierAction: ViewApplier<View, L, S>.() -> Unit
      )
{
   apply(ViewConstructor, applierAction)
}

/**
 * If the next View is a View, applies Koshian to it.
 *
 * Otherwise, creates a new View and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L, S : KoshianStyle>
      ApplierParent<L, S>.View(
            styleElement: KoshianStyle.StyleElement<View>,
            applierAction: ViewApplier<View, L, S>.() -> Unit
      )
{
   apply(ViewConstructor, styleElement, applierAction)
}

/**
 * Applies Koshian to all Views that are named the specified in this ViewGroup.
 * If there are no Views named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L, S : KoshianStyle>
      ApplierParent<L, S>.View(
            name: String,
            applierAction: ViewApplier<View, L, S>.() -> Unit
      )
{
   apply(name, applierAction)
}

/**
 * Applies Koshian to all Views that are named the specified in this ViewGroup.
 * If there are no Views named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L, S : KoshianStyle>
      ApplierParent<L, S>.View(
            name: String,
            styleElement: KoshianStyle.StyleElement<View>,
            applierAction: ViewApplier<View, L, S>.() -> Unit
      )
{
   apply(name, styleElement, applierAction)
}

/**
 * registers a style applier function into this [KoshianStyle].
 *
 * Styles can be applied via [applyKoshian]
 */
@Suppress("FunctionName")
inline fun KoshianStyle.View(
      crossinline styleAction: ViewStyle<View>.() -> Unit
): KoshianStyle.StyleElement<View> {
   return createStyleElement(styleAction)
}

var View.backgroundColor: Int
   @Deprecated(message = "The getter always throws an Exception", level = DeprecationLevel.ERROR)
   get() = throw UnsupportedOperationException()
   inline set(value) = setBackgroundColor(value)

var View.padding: Int
   @Deprecated(message = "The getter always throws an Exception", level = DeprecationLevel.ERROR)
   get() = throw UnsupportedOperationException()
   set(value) {
      if (Build.VERSION.SDK_INT >= 17) {
         setPaddingRelative(value, value, value, value)
      } else {
         setPadding(value, value, value, value)
      }
   }

fun View.setPadding(horizontal: Int, vertical: Int) {
   if (Build.VERSION.SDK_INT >= 17) {
      setPaddingRelative(horizontal, vertical, horizontal, vertical)
   } else {
      setPadding(horizontal, vertical, horizontal, vertical)
   }
}

val KoshianExt<View, *>.LAYOUT_DIRECTION_LTR: Int @RequiresApi(17) inline get() = View.LAYOUT_DIRECTION_LTR
val KoshianExt<View, *>.LAYOUT_DIRECTION_RTL: Int @RequiresApi(17) inline get() = View.LAYOUT_DIRECTION_RTL

val KoshianExt<View, *>.VISIBLE:   Int inline get() = View.VISIBLE
val KoshianExt<View, *>.INVISIBLE: Int inline get() = View.INVISIBLE
val KoshianExt<View, *>.GONE:      Int inline get() = View.GONE
