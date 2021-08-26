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
import androidx.viewpager.widget.ViewPager
import koshian.*
import kotlin.contracts.*

object ViewPagerConstructor : KoshianViewConstructor<ViewPager, ViewPager.LayoutParams> {
   override fun instantiate(context: Context) = ViewPager(context)
   override fun instantiateLayoutParams() = ViewPager.LayoutParams()
}

/**
 * adds Views into this ViewPager.
 */
@KoshianMarker
@ExperimentalContracts
inline fun <R> ViewPager.addView(
      creatorAction: ViewGroupCreator<ViewPager, Nothing, ViewPager.LayoutParams>.() -> R
): R {
   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }
   return addView(ViewPagerConstructor, creatorAction)
}

/**
 * creates a new ViewPager and adds it into this ViewGroup.
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams> CreatorParent<L>.ViewPager(
      creatorAction: ViewGroupCreator<ViewPager, L, ViewPager.LayoutParams>.() -> Unit
): ViewPager {
   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }
   return create(ViewPagerConstructor, creatorAction)
}

/**
 * creates a new ViewPager with name, and adds it into this ViewGroup.
 *
 * The name can be referenced in [applyKoshian]
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams> CreatorParent<L>.ViewPager(
      name: String,
      creatorAction: ViewGroupCreator<ViewPager, L, ViewPager.LayoutParams>.() -> Unit
): ViewPager {
   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }
   return create(name, ViewPagerConstructor, creatorAction)
}

/**
 * finds Views that are already added in this ViewPager,
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
inline fun ViewPager.applyKoshian(
      applierAction: ViewGroupApplier<ViewPager, ViewGroup.LayoutParams, ViewPager.LayoutParams, Nothing>.() -> Unit
) {
   applyKoshian(ViewPagerConstructor, applierAction)
}

/**
 * finds Views that are already added in this ViewPager,
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
inline fun <S : KoshianStyle> ViewPager.applyKoshian(
      style: S,
      applierAction: ViewGroupApplier<ViewPager, ViewGroup.LayoutParams, ViewPager.LayoutParams, S>.() -> Unit
) {
   applyKoshian(style, ViewPagerConstructor, applierAction)
}

/**
 * If the next View is a ViewPager, applies Koshian to it.
 *
 * Otherwise, creates a new ViewPager and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams, S : KoshianStyle>
      ApplierParent<L, S>.ViewPager(
            applierAction: ViewGroupApplier<ViewPager, L, ViewPager.LayoutParams, S>.() -> Unit
      )
{
   applyViewGroup(ViewPagerConstructor, applierAction)
}

/**
 * If the next View is a ViewPager, applies Koshian to it.
 *
 * Otherwise, creates a new ViewPager and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams, S : KoshianStyle>
      ApplierParent<L, S>.ViewPager(
            styleElement: KoshianStyle.StyleElement<ViewPager>,
            applierAction: ViewGroupApplier<ViewPager, L, ViewPager.LayoutParams, S>.() -> Unit
      )
{
   applyViewGroup(ViewPagerConstructor, styleElement, applierAction)
}

/**
 * Applies Koshian to all ViewPagers that are named the specified in this ViewGroup.
 * If there are no ViewPagers named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams, S : KoshianStyle>
      ApplierParent<L, S>.ViewPager(
            name: String,
            applierAction: ViewGroupApplier<ViewPager, L, ViewPager.LayoutParams, S>.() -> Unit
      )
{
   apply(name, ViewPagerConstructor, applierAction)
}

/**
 * Applies Koshian to all ViewPagers that are named the specified in this ViewGroup.
 * If there are no ViewPagers named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams, S : KoshianStyle>
      ApplierParent<L, S>.ViewPager(
            name: String,
            styleElement: KoshianStyle.StyleElement<ViewPager>,
            applierAction: ViewGroupApplier<ViewPager, L, ViewPager.LayoutParams, S>.() -> Unit
      )
{
   apply(name, ViewPagerConstructor, styleElement, applierAction)
}

/**
 * registers a style applier function into this [KoshianStyle].
 *
 * Styles can be applied via [applyKoshian]
 */
@Suppress("FunctionName")
inline fun KoshianStyle.ViewPager(
      crossinline styleAction: ViewStyle<ViewPager>.() -> Unit
): KoshianStyle.StyleElement<ViewPager> {
   return createStyleElement(styleAction)
}
