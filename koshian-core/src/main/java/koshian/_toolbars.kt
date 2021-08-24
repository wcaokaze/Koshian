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
import android.widget.Toolbar
import androidx.annotation.RequiresApi
import kotlin.contracts.*

@RequiresApi(21)
object ToolbarConstructor : KoshianViewConstructor<Toolbar, Toolbar.LayoutParams> {
   override fun instantiate(context: Context?) = Toolbar(context)
   override fun instantiateLayoutParams() = Toolbar.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
}

/**
 * adds Views into this Toolbar.
 */
@KoshianMarker
@ExperimentalContracts
@RequiresApi(21)
inline fun <R> Toolbar.addView(
      creatorAction: ViewGroupCreator<Toolbar, Nothing, Toolbar.LayoutParams>.() -> R
): R {
   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }
   return addView(ToolbarConstructor, creatorAction)
}

/**
 * creates a new Toolbar and adds it into this ViewGroup.
 */
@ExperimentalContracts
@RequiresApi(21)
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams> CreatorParent<L>.Toolbar(
      creatorAction: ViewGroupCreator<Toolbar, L, Toolbar.LayoutParams>.() -> Unit
): Toolbar {
   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }
   return create(ToolbarConstructor, creatorAction)
}

/**
 * creates a new Toolbar with name, and adds it into this ViewGroup.
 *
 * The name can be referenced in [applyKoshian]
 */
@ExperimentalContracts
@RequiresApi(21)
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams> CreatorParent<L>.Toolbar(
      name: String,
      creatorAction: ViewGroupCreator<Toolbar, L, Toolbar.LayoutParams>.() -> Unit
): Toolbar {
   contract { callsInPlace(creatorAction, InvocationKind.EXACTLY_ONCE) }
   return create(name, ToolbarConstructor, creatorAction)
}

/**
 * finds Views that are already added in this Toolbar,
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
@RequiresApi(21)
inline fun Toolbar.applyKoshian(
      applierAction: ViewGroupApplier<Toolbar, ViewGroup.LayoutParams, Toolbar.LayoutParams, Nothing>.() -> Unit
) {
   applyKoshian(ToolbarConstructor, applierAction)
}

/**
 * finds Views that are already added in this Toolbar,
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
@RequiresApi(21)
inline fun <S : KoshianStyle> Toolbar.applyKoshian(
      style: S,
      applierAction: ViewGroupApplier<Toolbar, ViewGroup.LayoutParams, Toolbar.LayoutParams, S>.() -> Unit
) {
   applyKoshian(style, ToolbarConstructor, applierAction)
}

/**
 * If the next View is a Toolbar, applies Koshian to it.
 *
 * Otherwise, creates a new Toolbar and inserts it to the current position.
 *
 * @see applyKoshian
 */
@RequiresApi(21)
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams, S : KoshianStyle>
      ApplierParent<L, S>.Toolbar(
            applierAction: ViewGroupApplier<Toolbar, L, Toolbar.LayoutParams, S>.() -> Unit
      )
{
   applyViewGroup(ToolbarConstructor, applierAction)
}

/**
 * If the next View is a Toolbar, applies Koshian to it.
 *
 * Otherwise, creates a new Toolbar and inserts it to the current position.
 *
 * @see applyKoshian
 */
@RequiresApi(21)
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams, S : KoshianStyle>
      ApplierParent<L, S>.Toolbar(
            styleElement: KoshianStyle.StyleElement<Toolbar>,
            applierAction: ViewGroupApplier<Toolbar, L, Toolbar.LayoutParams, S>.() -> Unit
      )
{
   applyViewGroup(ToolbarConstructor, styleElement, applierAction)
}

/**
 * Applies Koshian to all Toolbars that are named the specified in this ViewGroup.
 * If there are no Toolbars named the specified, do nothing.
 *
 * @see applyKoshian
 */
@RequiresApi(21)
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams, S : KoshianStyle>
      ApplierParent<L, S>.Toolbar(
            name: String,
            applierAction: ViewGroupApplier<Toolbar, L, Toolbar.LayoutParams, S>.() -> Unit
      )
{
   apply(name, ToolbarConstructor, applierAction)
}

/**
 * Applies Koshian to all Toolbars that are named the specified in this ViewGroup.
 * If there are no Toolbars named the specified, do nothing.
 *
 * @see applyKoshian
 */
@RequiresApi(21)
@Suppress("FunctionName")
inline fun <L : ViewGroup.LayoutParams, S : KoshianStyle>
      ApplierParent<L, S>.Toolbar(
            name: String,
            styleElement: KoshianStyle.StyleElement<Toolbar>,
            applierAction: ViewGroupApplier<Toolbar, L, Toolbar.LayoutParams, S>.() -> Unit
      )
{
   apply(name, ToolbarConstructor, styleElement, applierAction)
}

/**
 * registers a style applier function into this [KoshianStyle].
 *
 * Styles can be applied via [applyKoshian]
 */
@RequiresApi(21)
@Suppress("FunctionName")
inline fun KoshianStyle.Toolbar(
      crossinline styleAction: ViewStyle<Toolbar>.() -> Unit
): KoshianStyle.StyleElement<Toolbar> {
   return createStyleElement(styleAction)
}

var Toolbar.titleTextColor: Int
   @Deprecated(message = "The getter always throws an Exception", level = DeprecationLevel.ERROR)
   get() = throw UnsupportedOperationException()
   @RequiresApi(21)
   inline set(value) = setTitleTextColor(value)
