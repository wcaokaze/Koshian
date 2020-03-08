package koshian

import android.content.Context
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TabHost
import kotlin.contracts.*

object TabHostConstructor : KoshianViewGroupConstructor<TabHost, FrameLayout.LayoutParams> {
   override fun instantiate(context: Context?) = TabHost(context)
   override fun instantiateLayoutParams() = FrameLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
}

/**
 * adds Views into this TabHost.
 */
@ExperimentalContracts
inline fun <R> TabHost.addView(
      buildAction: ViewGroupBuilder<TabHost, Nothing, FrameLayout.LayoutParams, KoshianMode.Creator>.() -> R
): R {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return addView(TabHostConstructor, buildAction)
}

/**
 * creates a new TabHost and adds it into this ViewGroup.
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Creator>.TabHost(
      buildAction: ViewGroupBuilder<TabHost, L, FrameLayout.LayoutParams, KoshianMode.Creator>.() -> Unit
): TabHost {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return create(TabHostConstructor, buildAction)
}

/**
 * finds Views that are already added in this TabHost,
 * and applies Koshian DSL to them.
 *
 * ![](https://raw.github.com/wcaokaze/Koshian/master/imgs/applier.svg?sanitize=true)
 *
 * The following 2 snippets are equivalent.
 *
 * 1.
 *     ```kotlin
 *     val contentView = koshian(context) {
 *        LinearLayout {
 *           TextView {
 *              view.text = "hello"
 *              view.textColor = 0xffffff opacity 0.8
 *           }
 *        }
 *     }
 *     ```
 *
 * 2.
 *     ```kotlin
 *     val contentView = koshian(context) {
 *        LinearLayout {
 *           TextView {
 *              view.text = "hello"
 *           }
 *        }
 *     }
 *
 *     contentView.applyKoshian {
 *        TextView {
 *           view.textColor = 0xffffff opacity 0.8
 *        }
 *     }
 *     ```
 *
 * When mismatched View is specified, Koshian creates a new View and inserts it.
 *
 * ![](https://raw.github.com/wcaokaze/Koshian/master/imgs/applier_insertion.svg?sanitize=true)
 */
inline fun TabHost.applyKoshian(
      applyAction: ViewGroupBuilder<TabHost, ViewGroup.LayoutParams, FrameLayout.LayoutParams, KoshianMode.Applier>.() -> Unit
) {
   applyKoshian(TabHostConstructor, applyAction)
}

/**
 * If the next View is a TabHost, applies Koshian to it.
 *
 * Otherwise, creates a new TabHost and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Applier>.TabHost(
      buildAction: ViewGroupBuilder<TabHost, L, FrameLayout.LayoutParams, KoshianMode.Applier>.() -> Unit
) {
   apply(TabHostConstructor, buildAction)
}
