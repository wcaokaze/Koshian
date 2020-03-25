package koshian;

import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Map;

import kotlin.jvm.functions.Function1;

public final class $$StyleInternal {
   private static final Map<Class<? extends View>, KoshianViewConstructor<?>>
         viewConstructorMap = new HashMap<>();

   static <V extends View> void addViewConstructor(
         final Class<V> targetViewClass,
         final KoshianViewConstructor<V> viewConstructor
   ) {
      viewConstructorMap.put(targetViewClass, viewConstructor);
   }

   public static void applyStyle(final View view) {
      apply(view);

      if (view instanceof ViewGroup) {
         final ViewGroup viewGroup = (ViewGroup) view;

         for (int i = 0; i < viewGroup.getChildCount(); i++) {
            final View child = viewGroup.getChildAt(i);
            applyStyle(child);
         }
      }
   }

   private static <V extends View> void apply(final V view) {
      @SuppressWarnings("unchecked")
      final KoshianViewConstructor<V> viewConstructor
            = (KoshianViewConstructor<V>) viewConstructorMap.get(view.getClass());

      if (viewConstructor == null) { return; }
      final Function1<V, Void> styleAction = viewConstructor.getStyleAction();
      if (styleAction == null) { return; }
      styleAction.invoke(view);
   }
}
