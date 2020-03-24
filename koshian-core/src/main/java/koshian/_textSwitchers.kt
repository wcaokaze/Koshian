/*
 * Copyright 2020 wcaokaze
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@file:Suppress("UNUSED")
package koshian

import android.content.Context
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextSwitcher
import kotlin.contracts.*

object TextSwitcherConstructor : KoshianViewGroupConstructor<TextSwitcher, FrameLayout.LayoutParams>(TextSwitcher::class.java) {
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
 * creates a new TextSwitcher with name, and adds it into this ViewGroup.
 *
 * The name can be referenced in [applyKoshian]
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Creator>.TextSwitcher(
      name: String,
      buildAction: ViewGroupBuilder<TextSwitcher, L, FrameLayout.LayoutParams, KoshianMode.Creator>.() -> Unit
): TextSwitcher {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return create(name, TextSwitcherConstructor, buildAction)
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

/**
 * Applies Koshian to all TextSwitchers that are named the specified in this ViewGroup.
 * If there are no TextSwitchers named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Applier>.TextSwitcher(
      name: String,
      buildAction: ViewGroupBuilder<TextSwitcher, L, FrameLayout.LayoutParams, KoshianMode.Applier>.() -> Unit
) {
   apply(name, TextSwitcherConstructor, buildAction)
}
