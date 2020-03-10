@file:Suppress("UNUSED")
package koshian

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import kotlin.contracts.*

object ImageViewConstructor : KoshianViewConstructor<ImageView> {
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
