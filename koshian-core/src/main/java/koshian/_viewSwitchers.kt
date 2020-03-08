package koshian

import android.content.Context
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ViewSwitcher
import kotlin.contracts.*

object ViewSwitcherConstructor : KoshianViewGroupConstructor<ViewSwitcher, FrameLayout.LayoutParams> {
   override fun instantiate(context: Context?) = ViewSwitcher(context)
   override fun instantiateLayoutParams() = FrameLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
}

/**
 * adds Views into this ViewSwitcher.
 */
@ExperimentalContracts
inline fun <R> ViewSwitcher.addView(
      buildAction: ViewGroupBuilder<ViewSwitcher, Nothing, FrameLayout.LayoutParams, KoshianMode.Creator>.() -> R
): R {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return addView(ViewSwitcherConstructor, buildAction)
}

/**
 * creates a new ViewSwitcher and adds it into this ViewGroup.
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Creator>.ViewSwitcher(
      buildAction: ViewGroupBuilder<ViewSwitcher, L, FrameLayout.LayoutParams, KoshianMode.Creator>.() -> Unit
): ViewSwitcher {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return create(ViewSwitcherConstructor, buildAction)
}

/**
 * finds Views that are already added in this ViewSwitcher,
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
inline fun ViewSwitcher.applyKoshian(
      applyAction: ViewGroupBuilder<ViewSwitcher, ViewGroup.LayoutParams, FrameLayout.LayoutParams, KoshianMode.Applier>.() -> Unit
) {
   applyKoshian(ViewSwitcherConstructor, applyAction)
}

/**
 * If the next View is a ViewSwitcher, applies Koshian to it.
 *
 * Otherwise, creates a new ViewSwitcher and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Applier>.ViewSwitcher(
      buildAction: ViewGroupBuilder<ViewSwitcher, L, FrameLayout.LayoutParams, KoshianMode.Applier>.() -> Unit
) {
   apply(ViewSwitcherConstructor, buildAction)
}
