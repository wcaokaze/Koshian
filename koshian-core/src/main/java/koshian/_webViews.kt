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
import android.webkit.WebView
import kotlin.contracts.*

object WebViewConstructor : KoshianViewConstructor<WebView> {
   override fun instantiate(context: Context) = WebView(context)
}

/**
 * creates a new WebView and adds it into this ViewGroup.
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> CreatorParent<L>.WebView(
      creatorAction: ViewCreator<WebView, L>.() -> Unit
): WebView {
   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }
   return create(WebViewConstructor, creatorAction)
}

/**
 * creates a new WebView with name, and adds it into this ViewGroup.
 *
 * The name can be referenced in [applyKoshian]
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> CreatorParent<L>.WebView(
      name: String,
      creatorAction: ViewCreator<WebView, L>.() -> Unit
): WebView {
   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }
   return create(name, WebViewConstructor, creatorAction)
}

/**
 * If the next View is a WebView, applies Koshian to it.
 *
 * Otherwise, creates a new WebView and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L, S : KoshianStyle>
      ApplierParent<L, S>.WebView(
            applierAction: ViewApplier<WebView, L, S>.() -> Unit
      )
{
   apply(WebViewConstructor, applierAction)
}

/**
 * If the next View is a WebView, applies Koshian to it.
 *
 * Otherwise, creates a new WebView and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L, S : KoshianStyle>
      ApplierParent<L, S>.WebView(
            styleElement: KoshianStyle.StyleElement<WebView>,
            applierAction: ViewApplier<WebView, L, S>.() -> Unit
      )
{
   apply(WebViewConstructor, styleElement, applierAction)
}

/**
 * Applies Koshian to all WebViews that are named the specified in this ViewGroup.
 * If there are no WebViews named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L, S : KoshianStyle>
      ApplierParent<L, S>.WebView(
            name: String,
            applierAction: ViewApplier<WebView, L, S>.() -> Unit
      )
{
   apply(name, applierAction)
}

/**
 * Applies Koshian to all WebViews that are named the specified in this ViewGroup.
 * If there are no WebViews named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L, S : KoshianStyle>
      ApplierParent<L, S>.WebView(
            name: String,
            styleElement: KoshianStyle.StyleElement<WebView>,
            applierAction: ViewApplier<WebView, L, S>.() -> Unit
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
inline fun KoshianStyle.WebView(
      crossinline styleAction: ViewStyle<WebView>.() -> Unit
): KoshianStyle.StyleElement<WebView> {
   return createStyleElement(styleAction)
}
