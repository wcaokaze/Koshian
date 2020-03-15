@file:Suppress("UNUSED")
package koshian

import android.content.Context
import android.view.ViewGroup
import android.widget.TableLayout
import kotlin.contracts.*

object TableLayoutConstructor : KoshianViewGroupConstructor<TableLayout, TableLayout.LayoutParams> {
   override fun instantiate(context: Context?) = TableLayout(context)
   override fun instantiateLayoutParams() = TableLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
}

/**
 * adds Views into this TableLayout.
 */
@ExperimentalContracts
inline fun <R> TableLayout.addView(
      buildAction: ViewGroupBuilder<TableLayout, Nothing, TableLayout.LayoutParams, KoshianMode.Creator>.() -> R
): R {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return addView(TableLayoutConstructor, buildAction)
}

/**
 * creates a new TableLayout and adds it into this ViewGroup.
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Creator>.TableLayout(
      buildAction: ViewGroupBuilder<TableLayout, L, TableLayout.LayoutParams, KoshianMode.Creator>.() -> Unit
): TableLayout {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return create(TableLayoutConstructor, buildAction)
}

/**
 * creates a new TableLayout with name, and adds it into this ViewGroup.
 *
 * The name can be referenced in [applyKoshian]
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Creator>.TableLayout(
      name: String,
      buildAction: ViewGroupBuilder<TableLayout, L, TableLayout.LayoutParams, KoshianMode.Creator>.() -> Unit
): TableLayout {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return create(name, TableLayoutConstructor, buildAction)
}

/**
 * finds Views that are already added in this TableLayout,
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
inline fun TableLayout.applyKoshian(
      applyAction: ViewGroupBuilder<TableLayout, ViewGroup.LayoutParams, TableLayout.LayoutParams, KoshianMode.Applier>.() -> Unit
) {
   applyKoshian(TableLayoutConstructor, applyAction)
}

/**
 * If the next View is a TableLayout, applies Koshian to it.
 *
 * Otherwise, creates a new TableLayout and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Applier>.TableLayout(
      buildAction: ViewGroupBuilder<TableLayout, L, TableLayout.LayoutParams, KoshianMode.Applier>.() -> Unit
) {
   apply(TableLayoutConstructor, buildAction)
}

/**
 * Applies Koshian to all TableLayouts that are named the specified in this ViewGroup.
 * If there are no TableLayouts named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Applier>.TableLayout(
      name: String,
      buildAction: ViewGroupBuilder<TableLayout, L, TableLayout.LayoutParams, KoshianMode.Applier>.() -> Unit
) {
   apply(name, TableLayoutConstructor, buildAction)
}
