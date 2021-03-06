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
import android.widget.RadioGroup
import kotlin.contracts.*

object RadioGroupConstructor : KoshianViewGroupConstructor<RadioGroup, RadioGroup.LayoutParams> {
   override fun instantiate(context: Context?) = RadioGroup(context)
   override fun instantiateLayoutParams() = RadioGroup.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
}

/**
 * adds Views into this RadioGroup.
 */
@KoshianMarker
@ExperimentalContracts
inline fun <R> RadioGroup.addView(
      creatorAction: ViewGroupCreator<RadioGroup, Nothing, RadioGroup.LayoutParams>.() -> R
): R {
   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }
   return addView(RadioGroupConstructor, creatorAction)
}

/**
 * creates a new RadioGroup and adds it into this ViewGroup.
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> CreatorParent<L>.RadioGroup(
      creatorAction: ViewGroupCreator<RadioGroup, L, RadioGroup.LayoutParams>.() -> Unit
): RadioGroup {
   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }
   return create(RadioGroupConstructor, creatorAction)
}

/**
 * creates a new RadioGroup with name, and adds it into this ViewGroup.
 *
 * The name can be referenced in [applyKoshian]
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> CreatorParent<L>.RadioGroup(
      name: String,
      creatorAction: ViewGroupCreator<RadioGroup, L, RadioGroup.LayoutParams>.() -> Unit
): RadioGroup {
   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }
   return create(name, RadioGroupConstructor, creatorAction)
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
@KoshianMarker
inline fun RadioGroup.applyKoshian(
      applierAction: ViewGroupApplier<RadioGroup, ViewGroup.LayoutParams, RadioGroup.LayoutParams, Nothing>.() -> Unit
) {
   applyKoshian(RadioGroupConstructor, applierAction)
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
@KoshianMarker
inline fun <S : KoshianStyle> RadioGroup.applyKoshian(
      style: S,
      applierAction: ViewGroupApplier<RadioGroup, ViewGroup.LayoutParams, RadioGroup.LayoutParams, S>.() -> Unit
) {
   applyKoshian(style, RadioGroupConstructor, applierAction)
}

/**
 * If the next View is a RadioGroup, applies Koshian to it.
 *
 * Otherwise, creates a new RadioGroup and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L, S : KoshianStyle>
      ApplierParent<L, S>.RadioGroup(
            applierAction: ViewGroupApplier<RadioGroup, L, RadioGroup.LayoutParams, S>.() -> Unit
      )
{
   apply(RadioGroupConstructor, applierAction)
}

/**
 * If the next View is a RadioGroup, applies Koshian to it.
 *
 * Otherwise, creates a new RadioGroup and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L, S : KoshianStyle>
      ApplierParent<L, S>.RadioGroup(
            styleElement: KoshianStyle.StyleElement<RadioGroup>,
            applierAction: ViewGroupApplier<RadioGroup, L, RadioGroup.LayoutParams, S>.() -> Unit
      )
{
   apply(RadioGroupConstructor, styleElement, applierAction)
}

/**
 * Applies Koshian to all RadioGroups that are named the specified in this ViewGroup.
 * If there are no RadioGroups named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L, S : KoshianStyle>
      ApplierParent<L, S>.RadioGroup(
            name: String,
            applierAction: ViewGroupApplier<RadioGroup, L, RadioGroup.LayoutParams, S>.() -> Unit
      )
{
   apply(name, RadioGroupConstructor, applierAction)
}

/**
 * Applies Koshian to all RadioGroups that are named the specified in this ViewGroup.
 * If there are no RadioGroups named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L, S : KoshianStyle>
      ApplierParent<L, S>.RadioGroup(
            name: String,
            styleElement: KoshianStyle.StyleElement<RadioGroup>,
            applierAction: ViewGroupApplier<RadioGroup, L, RadioGroup.LayoutParams, S>.() -> Unit
      )
{
   apply(name, RadioGroupConstructor, styleElement, applierAction)
}

/**
 * registers a style applier function into this [KoshianStyle].
 *
 * Styles can be applied via [applyKoshian]
 */
@Suppress("FunctionName")
inline fun KoshianStyle.RadioGroup(
      crossinline styleAction: ViewStyle<RadioGroup>.() -> Unit
): KoshianStyle.StyleElement<RadioGroup> {
   return createStyleElement(styleAction)
}
