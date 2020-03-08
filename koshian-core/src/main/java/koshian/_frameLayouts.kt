package koshian

import android.content.*
import android.view.*
import android.widget.*
import kotlin.contracts.*

object FrameLayoutConstructor : KoshianViewGroupConstructor<FrameLayout, FrameLayout.LayoutParams> {
   override fun instantiate(context: Context) = FrameLayout(context)
   override fun instantiateLayoutParams() = FrameLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
}

/**
 * adds Views into this FrameLayout.
 */
@ExperimentalContracts
inline fun <R> FrameLayout.addView(
      buildAction: ViewGroupBuilder<FrameLayout, Nothing, FrameLayout.LayoutParams, KoshianMode.Creator>.() -> R
): R {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return addView(FrameLayoutConstructor, buildAction)
}

/**
 * creates a new FrameLayout and adds it into this ViewGroup.
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Creator>.FrameLayout(
      buildAction: ViewGroupBuilder<FrameLayout, L, FrameLayout.LayoutParams, KoshianMode.Creator>.() -> Unit
): FrameLayout {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return create(FrameLayoutConstructor, buildAction)
}

/**
 * finds Views that are already added in this FrameLayout,
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
inline fun FrameLayout.applyKoshian(
      applyAction: ViewGroupBuilder<FrameLayout, ViewGroup.LayoutParams, FrameLayout.LayoutParams, KoshianMode.Applier>.() -> Unit
) {
   applyKoshian(FrameLayoutConstructor, applyAction)
}

/**
 * If the next View is a FrameLayout, applies Koshian to it.
 *
 * Otherwise, creates a new FrameLayout and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Applier>.FrameLayout(
      buildAction: ViewGroupBuilder<FrameLayout, L, FrameLayout.LayoutParams, KoshianMode.Applier>.() -> Unit
) {
   apply(FrameLayoutConstructor, buildAction)
}
