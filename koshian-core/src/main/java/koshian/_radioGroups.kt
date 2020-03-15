@file:Suppress("UNUSED")
package koshian

import android.content.Context
import android.view.ViewGroup
import android.widget.RadioGroup
import kotlin.contracts.*

object RadioGroupConstructor : KoshianViewGroupConstructor<RadioGroup, RadioGroup.LayoutParams> {
   override fun instantiate(context: Context?) = RadioGroup(context)
   override fun instantiateLayoutParams() = RadioGroup.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
}

/**
 * adds Views into this RadioGroup.
 */
@ExperimentalContracts
inline fun <R> RadioGroup.addView(
      buildAction: ViewGroupBuilder<RadioGroup, Nothing, RadioGroup.LayoutParams, KoshianMode.Creator>.() -> R
): R {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return addView(RadioGroupConstructor, buildAction)
}

/**
 * creates a new RadioGroup and adds it into this ViewGroup.
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Creator>.RadioGroup(
      buildAction: ViewGroupBuilder<RadioGroup, L, RadioGroup.LayoutParams, KoshianMode.Creator>.() -> Unit
): RadioGroup {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return create(RadioGroupConstructor, buildAction)
}

/**
 * creates a new RadioGroup with name, and adds it into this ViewGroup.
 *
 * The name can be referenced in [applyKoshian]
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Creator>.RadioGroup(
      name: String,
      buildAction: ViewGroupBuilder<RadioGroup, L, RadioGroup.LayoutParams, KoshianMode.Creator>.() -> Unit
): RadioGroup {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return create(name, RadioGroupConstructor, buildAction)
}

/**
 * finds Views that are already added in this RadioGroup,
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
inline fun RadioGroup.applyKoshian(
      applyAction: ViewGroupBuilder<RadioGroup, ViewGroup.LayoutParams, RadioGroup.LayoutParams, KoshianMode.Applier>.() -> Unit
) {
   applyKoshian(RadioGroupConstructor, applyAction)
}

/**
 * If the next View is a RadioGroup, applies Koshian to it.
 *
 * Otherwise, creates a new RadioGroup and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Applier>.RadioGroup(
      buildAction: ViewGroupBuilder<RadioGroup, L, RadioGroup.LayoutParams, KoshianMode.Applier>.() -> Unit
) {
   apply(RadioGroupConstructor, buildAction)
}

/**
 * Applies Koshian to all RadioGroups that are named the specified in this ViewGroup.
 * If there are no RadioGroups named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Applier>.RadioGroup(
      name: String,
      buildAction: ViewGroupBuilder<RadioGroup, L, RadioGroup.LayoutParams, KoshianMode.Applier>.() -> Unit
) {
   apply(name, RadioGroupConstructor, buildAction)
}
