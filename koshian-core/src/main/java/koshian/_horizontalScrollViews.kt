@file:Suppress("UNUSED")
package koshian

import android.content.Context
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.HorizontalScrollView
import kotlin.contracts.*

object HorizontalScrollViewConstructor : KoshianViewGroupConstructor<HorizontalScrollView, FrameLayout.LayoutParams> {
   override fun instantiate(context: Context?) = HorizontalScrollView(context)
   override fun instantiateLayoutParams() = FrameLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
}

/**
 * adds Views into this HorizontalScrollView.
 */
@ExperimentalContracts
inline fun <R> HorizontalScrollView.addView(
      buildAction: ViewGroupBuilder<HorizontalScrollView, Nothing, FrameLayout.LayoutParams, KoshianMode.Creator>.() -> R
): R {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return addView(HorizontalScrollViewConstructor, buildAction)
}

/**
 * creates a new HorizontalScrollView and adds it into this ViewGroup.
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Creator>.HorizontalScrollView(
      buildAction: ViewGroupBuilder<HorizontalScrollView, L, FrameLayout.LayoutParams, KoshianMode.Creator>.() -> Unit
): HorizontalScrollView {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return create(HorizontalScrollViewConstructor, buildAction)
}

/**
 * creates a new HorizontalScrollView with name, and adds it into this ViewGroup.
 *
 * The name can be referenced in [applyKoshian]
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Creator>.HorizontalScrollView(
      name: String,
      buildAction: ViewGroupBuilder<HorizontalScrollView, L, FrameLayout.LayoutParams, KoshianMode.Creator>.() -> Unit
): HorizontalScrollView {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return create(name, HorizontalScrollViewConstructor, buildAction)
}

/**
 * finds Views that are already added in this HorizontalScrollView,
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
 *
 * Also, naming View is a good way.
 *
 * ![](https://raw.github.com/wcaokaze/Koshian/master/imgs/applier_named.svg?sanitize=true)
 *
 * Koshian specifying a name doesn't affect the cursor.
 * Koshian not specifying a name ignores named Views.
 * Named Views and non-named Views are simply in other worlds.
 *
 * ![](https://raw.github.com/wcaokaze/Koshian/master/imgs/applier_mixing_named_and_non_named.svg?sanitize=true)
 *
 * For readability, it is recommended to put named Views
 * as synchronized with the cursor.
 *
 * ![](https://raw.github.com/wcaokaze/Koshian/master/imgs/applier_readable_mixing.svg?sanitize=true)
 */
inline fun HorizontalScrollView.applyKoshian(
      applyAction: ViewGroupBuilder<HorizontalScrollView, ViewGroup.LayoutParams, FrameLayout.LayoutParams, KoshianMode.Applier>.() -> Unit
) {
   applyKoshian(HorizontalScrollViewConstructor, applyAction)
}

/**
 * If the next View is a HorizontalScrollView, applies Koshian to it.
 *
 * Otherwise, creates a new HorizontalScrollView and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Applier>.HorizontalScrollView(
      buildAction: ViewGroupBuilder<HorizontalScrollView, L, FrameLayout.LayoutParams, KoshianMode.Applier>.() -> Unit
) {
   apply(HorizontalScrollViewConstructor, buildAction)
}

/**
 * Applies Koshian to all HorizontalScrollViews that are named the specified in this ViewGroup.
 * If there are no HorizontalScrollViews named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Applier>.HorizontalScrollView(
      name: String,
      buildAction: ViewGroupBuilder<HorizontalScrollView, L, FrameLayout.LayoutParams, KoshianMode.Applier>.() -> Unit
) {
   apply(name, HorizontalScrollViewConstructor, buildAction)
}
