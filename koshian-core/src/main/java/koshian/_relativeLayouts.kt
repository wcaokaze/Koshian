package koshian

import android.content.Context
import android.view.ViewGroup
import android.widget.RelativeLayout
import kotlin.contracts.*

object RelativeLayoutConstructor : KoshianViewGroupConstructor<RelativeLayout, RelativeLayout.LayoutParams> {
   override fun instantiate(context: Context?) = RelativeLayout(context)
   override fun instantiateLayoutParams() = RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
}

/**
 * adds Views into this RelativeLayout.
 */
@ExperimentalContracts
inline fun <R> RelativeLayout.addView(
      buildAction: ViewGroupBuilder<RelativeLayout, Nothing, RelativeLayout.LayoutParams, KoshianMode.Creator>.() -> R
): R {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return addView(RelativeLayoutConstructor, buildAction)
}

/**
 * creates a new RelativeLayout and adds it into this ViewGroup.
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Creator>.RelativeLayout(
      buildAction: ViewGroupBuilder<RelativeLayout, L, RelativeLayout.LayoutParams, KoshianMode.Creator>.() -> Unit
): RelativeLayout {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return create(RelativeLayoutConstructor, buildAction)
}

/**
 * finds Views that are already added in this RelativeLayout,
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
inline fun RelativeLayout.applyKoshian(
      applyAction: ViewGroupBuilder<RelativeLayout, ViewGroup.LayoutParams, RelativeLayout.LayoutParams, KoshianMode.Applier>.() -> Unit
) {
   applyKoshian(RelativeLayoutConstructor, applyAction)
}

/**
 * If the next View is a RelativeLayout, applies Koshian to it.
 *
 * Otherwise, creates a new RelativeLayout and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Applier>.RelativeLayout(
      buildAction: ViewGroupBuilder<RelativeLayout, L, RelativeLayout.LayoutParams, KoshianMode.Applier>.() -> Unit
) {
   apply(RelativeLayoutConstructor, buildAction)
}
