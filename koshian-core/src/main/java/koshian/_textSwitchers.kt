package koshian

import android.content.Context
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextSwitcher
import kotlin.contracts.*

object TextSwitcherConstructor : KoshianViewGroupConstructor<TextSwitcher, FrameLayout.LayoutParams> {
   override fun instantiate(context: Context?) = TextSwitcher(context)
   override fun instantiateLayoutParams() = FrameLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
}

/**
 * adds Views into this TextSwitcher.
 */
@ExperimentalContracts
inline fun <R> TextSwitcher.addView(
      buildAction: ViewGroupBuilder<TextSwitcher, Nothing, FrameLayout.LayoutParams, KoshianMode.Creator>.() -> R
): R {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return addView(TextSwitcherConstructor, buildAction)
}

/**
 * creates a new TextSwitcher and adds it into this ViewGroup.
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Creator>.TextSwitcher(
      buildAction: ViewGroupBuilder<TextSwitcher, L, FrameLayout.LayoutParams, KoshianMode.Creator>.() -> Unit
): TextSwitcher {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return create(TextSwitcherConstructor, buildAction)
}

/**
 * finds Views that are already added in this TextSwitcher,
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
inline fun TextSwitcher.applyKoshian(
      applyAction: ViewGroupBuilder<TextSwitcher, ViewGroup.LayoutParams, FrameLayout.LayoutParams, KoshianMode.Applier>.() -> Unit
) {
   applyKoshian(TextSwitcherConstructor, applyAction)
}

/**
 * If the next View is a TextSwitcher, applies Koshian to it.
 *
 * Otherwise, creates a new TextSwitcher and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Applier>.TextSwitcher(
      buildAction: ViewGroupBuilder<TextSwitcher, L, FrameLayout.LayoutParams, KoshianMode.Applier>.() -> Unit
) {
   apply(TextSwitcherConstructor, buildAction)
}
