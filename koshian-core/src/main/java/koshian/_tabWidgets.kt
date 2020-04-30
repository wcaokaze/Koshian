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
@KoshianMarker
@ExperimentalContracts
inline fun <R> TabWidget.addView(
      creatorAction: ViewGroupCreator<TabWidget, Nothing, LinearLayout.LayoutParams>.() -> R
): R {
   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }
   return addView(TabWidgetConstructor, creatorAction)
}

/**
 * creates a new TabWidget and adds it into this ViewGroup.
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> CreatorParent<L>.TabWidget(
      creatorAction: ViewGroupCreator<TabWidget, L, LinearLayout.LayoutParams>.() -> Unit
): TabWidget {
   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }
   return create(TabWidgetConstructor, creatorAction)
}

/**
 * creates a new TabWidget with name, and adds it into this ViewGroup.
 *
 * The name can be referenced in [applyKoshian]
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> CreatorParent<L>.TabWidget(
      name: String,
      creatorAction: ViewGroupCreator<TabWidget, L, LinearLayout.LayoutParams>.() -> Unit
): TabWidget {
   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }
   return create(name, TabWidgetConstructor, creatorAction)
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
@KoshianMarker
inline fun TabWidget.applyKoshian(
      applierAction: ViewGroupApplier<TabWidget, ViewGroup.LayoutParams, LinearLayout.LayoutParams, Nothing>.() -> Unit
) {
   applyKoshian(TabWidgetConstructor, applierAction)
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
@KoshianMarker
inline fun <S : KoshianStyle> TabWidget.applyKoshian(
      style: S,
      applierAction: ViewGroupApplier<TabWidget, ViewGroup.LayoutParams, LinearLayout.LayoutParams, S>.() -> Unit
) {
   applyKoshian(style, TabWidgetConstructor, applierAction)
}

/**
 * If the next View is a TabWidget, applies Koshian to it.
 *
 * Otherwise, creates a new TabWidget and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L, S : KoshianStyle>
      ApplierParent<L, S>.TabWidget(
            applierAction: ViewGroupApplier<TabWidget, L, LinearLayout.LayoutParams, S>.() -> Unit
      )
{
   apply(TabWidgetConstructor, applierAction)
}

/**
 * If the next View is a TabWidget, applies Koshian to it.
 *
 * Otherwise, creates a new TabWidget and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L, S : KoshianStyle>
      ApplierParent<L, S>.TabWidget(
            styleElement: KoshianStyle.StyleElement<TabWidget>,
            applierAction: ViewGroupApplier<TabWidget, L, LinearLayout.LayoutParams, S>.() -> Unit
      )
{
   apply(TabWidgetConstructor, styleElement, applierAction)
}

/**
 * Applies Koshian to all TabWidgets that are named the specified in this ViewGroup.
 * If there are no TabWidgets named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L, S : KoshianStyle>
      ApplierParent<L, S>.TabWidget(
            name: String,
            applierAction: ViewGroupApplier<TabWidget, L, LinearLayout.LayoutParams, S>.() -> Unit
      )
{
   apply(name, TabWidgetConstructor, applierAction)
}

/**
 * Applies Koshian to all TabWidgets that are named the specified in this ViewGroup.
 * If there are no TabWidgets named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L, S : KoshianStyle>
      ApplierParent<L, S>.TabWidget(
            name: String,
            styleElement: KoshianStyle.StyleElement<TabWidget>,
            applierAction: ViewGroupApplier<TabWidget, L, LinearLayout.LayoutParams, S>.() -> Unit
      )
{
   apply(name, TabWidgetConstructor, styleElement, applierAction)
}

/**
 * registers a style applier function into this [KoshianStyle].
 *
 * Styles can be applied via [applyKoshian]
 */
@Suppress("FunctionName")
inline fun KoshianStyle.TabWidget(
      crossinline styleAction: ViewStyle<TabWidget>.() -> Unit
): KoshianStyle.StyleElement<TabWidget> {
   return createStyleElement(styleAction)
}
