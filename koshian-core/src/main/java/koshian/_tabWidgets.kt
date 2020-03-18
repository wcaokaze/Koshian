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
import android.widget.LinearLayout
import android.widget.TabWidget
import kotlin.contracts.*

object TabWidgetConstructor : KoshianViewGroupConstructor<TabWidget, LinearLayout.LayoutParams> {
   override fun instantiate(context: Context?) = TabWidget(context)
   override fun instantiateLayoutParams() = LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
}

/**
 * adds Views into this TabWidget.
 */
@ExperimentalContracts
inline fun <R> TabWidget.addView(
      buildAction: ViewGroupBuilder<TabWidget, Nothing, LinearLayout.LayoutParams, KoshianMode.Creator>.() -> R
): R {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return addView(TabWidgetConstructor, buildAction)
}

/**
 * creates a new TabWidget and adds it into this ViewGroup.
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Creator>.TabWidget(
      buildAction: ViewGroupBuilder<TabWidget, L, LinearLayout.LayoutParams, KoshianMode.Creator>.() -> Unit
): TabWidget {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return create(TabWidgetConstructor, buildAction)
}

/**
 * creates a new TabWidget with name, and adds it into this ViewGroup.
 *
 * The name can be referenced in [applyKoshian]
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Creator>.TabWidget(
      name: String,
      buildAction: ViewGroupBuilder<TabWidget, L, LinearLayout.LayoutParams, KoshianMode.Creator>.() -> Unit
): TabWidget {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return create(name, TabWidgetConstructor, buildAction)
}

/**
 * finds Views that are already added in this TabWidget,
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
inline fun TabWidget.applyKoshian(
      applyAction: ViewGroupBuilder<TabWidget, ViewGroup.LayoutParams, LinearLayout.LayoutParams, KoshianMode.Applier>.() -> Unit
) {
   applyKoshian(TabWidgetConstructor, applyAction)
}

/**
 * If the next View is a TabWidget, applies Koshian to it.
 *
 * Otherwise, creates a new TabWidget and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Applier>.TabWidget(
      buildAction: ViewGroupBuilder<TabWidget, L, LinearLayout.LayoutParams, KoshianMode.Applier>.() -> Unit
) {
   apply(TabWidgetConstructor, buildAction)
}

/**
 * Applies Koshian to all TabWidgets that are named the specified in this ViewGroup.
 * If there are no TabWidgets named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Applier>.TabWidget(
      name: String,
      buildAction: ViewGroupBuilder<TabWidget, L, LinearLayout.LayoutParams, KoshianMode.Applier>.() -> Unit
) {
   apply(name, TabWidgetConstructor, buildAction)
}
