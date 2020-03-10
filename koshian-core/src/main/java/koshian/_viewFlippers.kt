@file:Suppress("UNUSED")
package koshian

import android.content.Context
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ViewFlipper
import kotlin.contracts.*

object ViewFlipperConstructor : KoshianViewGroupConstructor<ViewFlipper, FrameLayout.LayoutParams> {
   override fun instantiate(context: Context?) = ViewFlipper(context)
   override fun instantiateLayoutParams() = FrameLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
}

/**
 * adds Views into this ViewFlipper.
 */
@ExperimentalContracts
inline fun <R> ViewFlipper.addView(
      buildAction: ViewGroupBuilder<ViewFlipper, Nothing, FrameLayout.LayoutParams, KoshianMode.Creator>.() -> R
): R {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return addView(ViewFlipperConstructor, buildAction)
}

/**
 * creates a new ViewFlipper and adds it into this ViewGroup.
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Creator>.ViewFlipper(
      buildAction: ViewGroupBuilder<ViewFlipper, L, FrameLayout.LayoutParams, KoshianMode.Creator>.() -> Unit
): ViewFlipper {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return create(ViewFlipperConstructor, buildAction)
}

/**
 * finds Views that are already added in this ViewFlipper,
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
inline fun ViewFlipper.applyKoshian(
      applyAction: ViewGroupBuilder<ViewFlipper, ViewGroup.LayoutParams, FrameLayout.LayoutParams, KoshianMode.Applier>.() -> Unit
) {
   applyKoshian(ViewFlipperConstructor, applyAction)
}

/**
 * If the next View is a ViewFlipper, applies Koshian to it.
 *
 * Otherwise, creates a new ViewFlipper and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Applier>.ViewFlipper(
      buildAction: ViewGroupBuilder<ViewFlipper, L, FrameLayout.LayoutParams, KoshianMode.Applier>.() -> Unit
) {
   apply(ViewFlipperConstructor, buildAction)
}
