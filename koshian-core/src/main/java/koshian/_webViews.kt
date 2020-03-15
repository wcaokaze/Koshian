@file:Suppress("UNUSED")
package koshian

import android.content.Context
import android.webkit.WebView
import kotlin.contracts.*

object WebViewConstructor : KoshianViewConstructor<WebView> {
   override fun instantiate(context: Context?) = WebView(context)
}

/**
 * creates a new WebView and adds it into this ViewGroup.
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Creator>.WebView(
      buildAction: ViewBuilder<WebView, L, KoshianMode.Creator>.() -> Unit
): WebView {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return create(WebViewConstructor, buildAction)
}

/**
 * creates a new WebView with name, and adds it into this ViewGroup.
 *
 * The name can be referenced in [applyKoshian]
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Creator>.WebView(
      name: String,
      buildAction: ViewBuilder<WebView, L, KoshianMode.Creator>.() -> Unit
): WebView {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return create(name, WebViewConstructor, buildAction)
}

/**
 * If the next View is a WebView, applies Koshian to it.
 *
 * Otherwise, creates a new WebView and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Applier>.WebView(
      buildAction: ViewBuilder<WebView, L, KoshianMode.Applier>.() -> Unit
) {
   apply(WebViewConstructor, buildAction)
}

/**
 * Applies Koshian to all WebViews that are named the specified in this ViewGroup.
 * If there are no WebViews named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Applier>.WebView(
      name: String,
      buildAction: ViewBuilder<WebView, L, KoshianMode.Applier>.() -> Unit
) {
   apply(name, buildAction)
}