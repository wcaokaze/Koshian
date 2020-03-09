@file:Suppress("UNUSED")
package koshian

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.annotation.RequiresApi
import kotlin.contracts.*

object RelativeLayoutConstructor : KoshianViewGroupConstructor<RelativeLayout, RelativeLayout.LayoutParams> {
   override fun instantiate(context: Context?) = RelativeLayout(context)
   override fun instantiateLayoutParams() = RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
}

/**
 * adds Views into this RelativeLayout.
 */
@ExperimentalContracts
inline fun <R> RelativeLayout.addView(
      buildAction: ViewGroupBuilder<RelativeLayout, Nothing, RelativeLayout.LayoutParams, KoshianMode.Creator>.() -> R
): R {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return addView(RelativeLayoutConstructor, buildAction)
}

/**
 * creates a new RelativeLayout and adds it into this ViewGroup.
 */
@ExperimentalContracts
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Creator>.RelativeLayout(
      buildAction: ViewGroupBuilder<RelativeLayout, L, RelativeLayout.LayoutParams, KoshianMode.Creator>.() -> Unit
): RelativeLayout {
   contract { callsInPlace(buildAction, InvocationKind.EXACTLY_ONCE) }
   return create(RelativeLayoutConstructor, buildAction)
}

/**
 * finds Views that are already added in this RelativeLayout,
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
 */
inline fun RelativeLayout.applyKoshian(
      applyAction: ViewGroupBuilder<RelativeLayout, ViewGroup.LayoutParams, RelativeLayout.LayoutParams, KoshianMode.Applier>.() -> Unit
) {
   applyKoshian(RelativeLayoutConstructor, applyAction)
}

/**
 * If the next View is a RelativeLayout, applies Koshian to it.
 *
 * Otherwise, creates a new RelativeLayout and inserts it to the current position.
 *
 * @see applyKoshian
 */
@Suppress("FunctionName")
inline fun <L> KoshianParent<L, KoshianMode.Applier>.RelativeLayout(
      buildAction: ViewGroupBuilder<RelativeLayout, L, RelativeLayout.LayoutParams, KoshianMode.Applier>.() -> Unit
) {
   apply(RelativeLayoutConstructor, buildAction)
}

var RelativeLayout.LayoutParams.alignParentStart: Boolean
   @Deprecated("this getter always throws", level = DeprecationLevel.ERROR)
   get() = throw UnsupportedOperationException()
   @RequiresApi(17)
   set(value) {
      if (value) {
         addRule(RelativeLayout.ALIGN_PARENT_START, RelativeLayout.TRUE)
      } else {
         removeRule(RelativeLayout.ALIGN_PARENT_START)
      }
   }

var RelativeLayout.LayoutParams.alignParentTop: Boolean
   @Deprecated("this getter always throws", level = DeprecationLevel.ERROR)
   get() = throw UnsupportedOperationException()
   @RequiresApi(17)
   set(value) {
      if (value) {
         addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE)
      } else {
         removeRule(RelativeLayout.ALIGN_PARENT_TOP)
      }
   }

var RelativeLayout.LayoutParams.alignParentEnd: Boolean
   @Deprecated("this getter always throws", level = DeprecationLevel.ERROR)
   get() = throw UnsupportedOperationException()
   @RequiresApi(17)
   set(value) {
      if (value) {
         addRule(RelativeLayout.ALIGN_PARENT_END, RelativeLayout.TRUE)
      } else {
         removeRule(RelativeLayout.ALIGN_PARENT_END)
      }
   }

var RelativeLayout.LayoutParams.alignParentBottom: Boolean
   @Deprecated("this getter always throws", level = DeprecationLevel.ERROR)
   get() = throw UnsupportedOperationException()
   @RequiresApi(17)
   set(value) {
      if (value) {
         addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE)
      } else {
         removeRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
      }
   }

var RelativeLayout.LayoutParams.centerHorizontal: Boolean
   @Deprecated("this getter always throws", level = DeprecationLevel.ERROR)
   get() = throw UnsupportedOperationException()
   @RequiresApi(17)
   set(value) {
      if (value) {
         addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE)
      } else {
         removeRule(RelativeLayout.CENTER_HORIZONTAL)
      }
   }

var RelativeLayout.LayoutParams.centerVertical: Boolean
   @Deprecated("this getter always throws", level = DeprecationLevel.ERROR)
   get() = throw UnsupportedOperationException()
   @RequiresApi(17)
   set(value) {
      if (value) {
         addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE)
      } else {
         removeRule(RelativeLayout.CENTER_VERTICAL)
      }
   }

var RelativeLayout.LayoutParams.centerInParent: Boolean
   @Deprecated("this getter always throws", level = DeprecationLevel.ERROR)
   get() = throw UnsupportedOperationException()
   @RequiresApi(17)
   set(value) {
      if (value) {
         addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE)
      } else {
         removeRule(RelativeLayout.CENTER_IN_PARENT)
      }
   }

@RequiresApi(17)
fun RelativeLayout.LayoutParams.above(view: View) {
   addRule(RelativeLayout.ABOVE, view.id)
}

@RequiresApi(17)
fun RelativeLayout.LayoutParams.below(view: View) {
   addRule(RelativeLayout.BELOW, view.id)
}

@RequiresApi(17)
fun RelativeLayout.LayoutParams.startOf(view: View) {
   addRule(RelativeLayout.START_OF, view.id)
}

@RequiresApi(17)
fun RelativeLayout.LayoutParams.endOf(view: View) {
   addRule(RelativeLayout.END_OF, view.id)
}
