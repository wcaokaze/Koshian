package koshian

import android.content.*

inline class KoshianViewProvider<out V>(val constructor: (Context) -> V) {
   inline operator fun invoke(koshianViewBuilder: Koshian<V>.() -> Unit): V {
      /*
       * NOTE: This is an inline fun in an inline class,
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

      val koshian = Koshian<V>(view)
      koshian.koshianViewBuilder()
      return view
   }
}
