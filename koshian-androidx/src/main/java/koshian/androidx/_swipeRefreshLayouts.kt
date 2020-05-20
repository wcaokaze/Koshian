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
package koshian.androidx

import android.content.Context
import android.view.ViewGroup
import androidx.swiperefreshlayout.widget.*
import koshian.*
import koshian.androidx.SwipeRefreshLayout
import kotlin.contracts.*

object SwipeRefreshLayoutConstructor : KoshianViewGroupConstructor<SwipeRefreshLayout, ViewGroup.LayoutParams> {
   override fun instantiate(context: Context) = SwipeRefreshLayout(context)
   override fun instantiateLayoutParams() = ViewGroup.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
}

/**
 * adds Views into this SwipeRefreshLayout.
 */
@KoshianMarker
@ExperimentalContracts
inline fun <R> SwipeRefreshLayout.addView(
      creatorAction: ViewGroupCreator<SwipeRefreshLayout, Nothing, ViewGroup.LayoutParams>.() -> R
): R {
   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }
   return addView(SwipeRefreshLayoutConstructor, creatorAction)
}

/**
 * creates a new SwipeRefreshLayout and adds it into this ViewGroup.
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> CreatorParent<L>.SwipeRefreshLayout(
      creatorAction: ViewGroupCreator<SwipeRefreshLayout, L, ViewGroup.LayoutParams>.() -> Unit
): SwipeRefreshLayout {
   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }
   return create(SwipeRefreshLayoutConstructor, creatorAction)
}

/**
 * creates a new SwipeRefreshLayout with name, and adds it into this ViewGroup.
 *
 * The name can be referenced in [applyKoshian]
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> CreatorParent<L>.SwipeRefreshLayout(
      name: String,
      creatorAction: ViewGroupCreator<SwipeRefreshLayout, L, ViewGroup.LayoutParams>.() -> Unit
): SwipeRefreshLayout {
   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }
   return create(name, SwipeRefreshLayoutConstructor, creatorAction)
}

/**
 * finds Views that are already added in this SwipeRefreshLayout,
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
inline fun SwipeRefreshLayout.applyKoshian(
      applierAction: ViewGroupApplier<SwipeRefreshLayout, ViewGroup.LayoutParams, ViewGroup.LayoutParams, Nothing>.() -> Unit
) {
   applyKoshian(SwipeRefreshLayoutConstructor, applierAction)
}

/**
 * finds Views that are already added in this SwipeRefreshLayout,
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
inline fun <S : KoshianStyle> SwipeRefreshLayout.applyKoshian(
      style: S,
      applierAction: ViewGroupApplier<SwipeRefreshLayout, ViewGroup.LayoutParams, ViewGroup.LayoutParams, S>.() -> Unit
) {
   applyKoshian(style, SwipeRefreshLayoutConstructor, applierAction)
}

/**
 * If the next View is a SwipeRefreshLayout, applies Koshian to it.
 *
 * Otherwise, creates a new SwipeRefreshLayout and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L, S : KoshianStyle>
      ApplierParent<L, S>.SwipeRefreshLayout(
            applierAction: ViewGroupApplier<SwipeRefreshLayout, L, ViewGroup.LayoutParams, S>.() -> Unit
      )
{
   apply(SwipeRefreshLayoutConstructor, applierAction)
}

/**
 * If the next View is a SwipeRefreshLayout, applies Koshian to it.
 *
 * Otherwise, creates a new SwipeRefreshLayout and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L, S : KoshianStyle>
      ApplierParent<L, S>.SwipeRefreshLayout(
      styleElement: KoshianStyle.StyleElement<SwipeRefreshLayout>,
            applierAction: ViewGroupApplier<SwipeRefreshLayout, L, ViewGroup.LayoutParams, S>.() -> Unit
      )
{
   apply(SwipeRefreshLayoutConstructor, styleElement, applierAction)
}

/**
 * Applies Koshian to all SwipeRefreshLayouts that are named the specified in this ViewGroup.
 * If there are no SwipeRefreshLayouts named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L, S : KoshianStyle>
      ApplierParent<L, S>.SwipeRefreshLayout(
            name: String,
            applierAction: ViewGroupApplier<SwipeRefreshLayout, L, ViewGroup.LayoutParams, S>.() -> Unit
      )
{
   apply(name, SwipeRefreshLayoutConstructor, applierAction)
}

/**
 * Applies Koshian to all SwipeRefreshLayouts that are named the specified in this ViewGroup.
 * If there are no SwipeRefreshLayouts named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L, S : KoshianStyle>
      ApplierParent<L, S>.SwipeRefreshLayout(
            name: String,
            styleElement: KoshianStyle.StyleElement<SwipeRefreshLayout>,
            applierAction: ViewGroupApplier<SwipeRefreshLayout, L, ViewGroup.LayoutParams, S>.() -> Unit
      )
{
   apply(name, SwipeRefreshLayoutConstructor, styleElement, applierAction)
}

/**
 * registers a style applier function into this [KoshianStyle].
 *
 * Styles can be applied via [applyKoshian]
 */
@Suppress("FunctionName")
inline fun KoshianStyle.SwipeRefreshLayout(
      crossinline styleAction: ViewStyle<SwipeRefreshLayout>.() -> Unit
): KoshianStyle.StyleElement<SwipeRefreshLayout> {
   return createStyleElement(styleAction)
}
