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
import android.view.ViewGroup
import android.widget.QuickContactBadge
import kotlin.contracts.*

object QuickContactBadgeConstructor : KoshianViewConstructor<QuickContactBadge, Nothing> {
   override fun instantiate(context: Context?) = QuickContactBadge(context)
   override fun instantiateLayoutParams(): Nothing = throw UnsupportedOperationException()
}

/**
 * creates a new QuickContactBadge and adds it into this ViewGroup.
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams> CreatorParent<L>.QuickContactBadge(
      creatorAction: ViewCreator<QuickContactBadge, L>.() -> Unit
): QuickContactBadge {
   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }
   return create(QuickContactBadgeConstructor, creatorAction)
}

/**
 * creates a new QuickContactBadge with name, and adds it into this ViewGroup.
 *
 * The name can be referenced in [applyKoshian]
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams> CreatorParent<L>.QuickContactBadge(
      name: String,
      creatorAction: ViewCreator<QuickContactBadge, L>.() -> Unit
): QuickContactBadge {
   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }
   return create(name, QuickContactBadgeConstructor, creatorAction)
}

/**
 * If the next View is a QuickContactBadge, applies Koshian to it.
 *
 * Otherwise, creates a new QuickContactBadge and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams, S : KoshianStyle>
      ApplierParent<L, S>.QuickContactBadge(
            applierAction: ViewApplier<QuickContactBadge, L, S>.() -> Unit
      )
{
   apply(QuickContactBadgeConstructor, applierAction)
}

/**
 * If the next View is a QuickContactBadge, applies Koshian to it.
 *
 * Otherwise, creates a new QuickContactBadge and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams, S : KoshianStyle>
      ApplierParent<L, S>.QuickContactBadge(
            styleElement: KoshianStyle.StyleElement<QuickContactBadge>,
            applierAction: ViewApplier<QuickContactBadge, L, S>.() -> Unit
      )
{
   apply(QuickContactBadgeConstructor, styleElement, applierAction)
}

/**
 * Applies Koshian to all QuickContactBadges that are named the specified in this ViewGroup.
 * If there are no QuickContactBadges named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams, S : KoshianStyle>
      ApplierParent<L, S>.QuickContactBadge(
            name: String,
            applierAction: ViewApplier<QuickContactBadge, L, S>.() -> Unit
      )
{
   apply(QuickContactBadgeConstructor, name, applierAction)
}

/**
 * Applies Koshian to all QuickContactBadges that are named the specified in this ViewGroup.
 * If there are no QuickContactBadges named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams, S : KoshianStyle>
      ApplierParent<L, S>.QuickContactBadge(
            name: String,
            styleElement: KoshianStyle.StyleElement<QuickContactBadge>,
            applierAction: ViewApplier<QuickContactBadge, L, S>.() -> Unit
      )
{
   apply(QuickContactBadgeConstructor, name, styleElement, applierAction)
}

/**
 * registers a style applier function into this [KoshianStyle].
 *
 * Styles can be applied via [applyKoshian]
 */
@Suppress("FunctionName")
inline fun KoshianStyle.QuickContactBadge(
      crossinline styleAction: ViewStyle<QuickContactBadge>.() -> Unit
): KoshianStyle.StyleElement<QuickContactBadge> {
   return createStyleElement(styleAction)
}
