package koshian

import android.content.*
import android.view.*

inline fun <R> koshian(context: Context, koshianBuilder: Koshian<Nothing>.() -> R): R {
   val oldContext = `$$KoshianInternal`.context
   `$$KoshianInternal`.context = context

   try {
      val koshian = Koshian<Nothing>(KoshianRoot.INSTANCE)
      return koshian.koshianBuilder()
   } finally {
      `$$KoshianInternal`.context = oldContext
   }
}

inline class KoshianViewConstructor<out V : View>(val constructor: (Context) -> V)

@KoshianMarker
inline class Koshian<out V>(val `$$koshianInternal$view`: Any?) {
   inline operator fun <C : View>
         KoshianViewConstructor<C>.invoke(koshianViewBuilder: Koshian<C>.() -> Unit): C
   {
      /*
       * NOTE: This is an inline fun for an inline class,
       * but the constructor is not inlined.
       *
       *     textView {
       *        view.text = ""
       *     }
       *
       * This will be compiled like the follow:
       *
       *     public final class TextViewConstructor implements (Context) -> TextView {
       *        public static final TextViewConstructor INSTANCE = new TextViewConstructor();
       *
       *        @Override
       *        public TextView invoke(final Context context) {
       *           return new TextView(context);
       *        }
       *     }
       *
       *     val provider = TextViewConstructor.INSTANCE
       *           as (Context) -> TextView // ??? Compiler inserts a cast
       *
       *     provider.constructor() // invoked in spite of nop
       *
       *     val view = provider(`$$KoshianInternal`.context)
       *
       *     val koshian = view
       *     koshian.constructor()
       *
       *     (koshian.`$$koshianInternal$view` as TextView).text = ""
       *
       * Not the follow:
       *
       *     val view = TextView(`$$KoshianInternal`.context)
       *     view.text = ""
       */
      val view = constructor(`$$KoshianInternal`.context)

      val parent = `$$koshianInternal$view` as ViewManager

      parent.addView(view,
            ViewGroup.LayoutParams(
                  ViewGroup.LayoutParams.WRAP_CONTENT,
                  ViewGroup.LayoutParams.WRAP_CONTENT)
      )

      val koshian = Koshian<C>(view)
      koshian.koshianViewBuilder()
      return view
   }
}

inline val <V : View> Koshian<V>.view: V get() {
   @Suppress("UNCHECKED_CAST")
   return `$$koshianInternal$view` as V
}

inline val Koshian<View>.layout: ViewGroup.LayoutParams get() {
   return (`$$koshianInternal$view` as View).layoutParams
}
