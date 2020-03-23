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
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import kotlin.contracts.*

object ImageViewConstructor : KoshianViewConstructor<ImageView>() {
   override fun instantiate(context: Context?) = ImageView(context)
}

/**
 * creates a new ImageView and adds it into this ViewGroup.
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Creator>.ImageView(
      buildAction: ViewBuilder<ImageView, L, KoshianMode.Creator>.() -> Unit
): ImageView {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return create(ImageViewConstructor, buildAction)
}

/**
 * creates a new ImageView with name, and adds it into this ViewGroup.
 *
 * The name can be referenced in [applyKoshian]
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Creator>.ImageView(
      name: String,
      buildAction: ViewBuilder<ImageView, L, KoshianMode.Creator>.() -> Unit
): ImageView {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return create(name, ImageViewConstructor, buildAction)
}

/**
 * If the next View is a ImageView, applies Koshian to it.
 *
 * Otherwise, creates a new ImageView and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Applier>.ImageView(
      buildAction: ViewBuilder<ImageView, L, KoshianMode.Applier>.() -> Unit
) {
   apply(ImageViewConstructor, buildAction)
}

/**
 * Applies Koshian to all ImageViews that are named the specified in this ViewGroup.
 * If there are no ImageViews named the specified, do nothing.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Applier>.ImageView(
      name: String,
      buildAction: ViewBuilder<ImageView, L, KoshianMode.Applier>.() -> Unit
) {
   apply(name, buildAction)
}

val KoshianExt<ImageView, *>.SCALE_TYPE_CENTER inline get() = ImageView.ScaleType.CENTER
val KoshianExt<ImageView, *>.CENTER_CROP       inline get() = ImageView.ScaleType.CENTER_CROP
val KoshianExt<ImageView, *>.CENTER_INSIDE     inline get() = ImageView.ScaleType.CENTER_INSIDE
val KoshianExt<ImageView, *>.FIT_CENTER        inline get() = ImageView.ScaleType.FIT_CENTER
val KoshianExt<ImageView, *>.FIT_END           inline get() = ImageView.ScaleType.FIT_END
val KoshianExt<ImageView, *>.FIT_START         inline get() = ImageView.ScaleType.FIT_START
val KoshianExt<ImageView, *>.FIT_XY            inline get() = ImageView.ScaleType.FIT_XY
val KoshianExt<ImageView, *>.MATRIX            inline get() = ImageView.ScaleType.MATRIX

var ImageView.image: Drawable?
   inline get() = drawable
   inline set(value) = setImageDrawable(value)

var ImageView.bitmap: Bitmap?
   @Deprecated("This getter always throws an Exception", level = DeprecationLevel.ERROR)
   get() = throw UnsupportedOperationException()
   inline set(value) = setImageBitmap(value)
