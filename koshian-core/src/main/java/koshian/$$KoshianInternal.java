package koshian;

import android.annotation.*;
import android.content.*;
import android.view.*;

public final class $$KoshianInternal {
   @SuppressLint("StaticFieldLeak")
   public static Context context = null;

   @SuppressWarnings("rawtypes")
   public static KoshianViewGroupConstructor parentViewConstructor = null;

   public static int applyingIndex = -1;

   public static <V extends View>
         V addNewView(final ViewManager parentView,
                      final KoshianViewConstructor<V> childConstructor)
   {
      final V child = childConstructor.instantiate(context);

      if (parentView instanceof ViewGroup) {
         final ViewGroup parentViewGroup = (ViewGroup) parentView;
         parentViewGroup.addView(child, applyingIndex++,
               parentViewConstructor.instantiateLayoutParams());
      } else {
         parentView.addView(child, parentViewConstructor.instantiateLayoutParams());
      }

      return child;
   }

   public static <V> V findView(final ViewManager parent,
                                final Class<V> viewClass)
   {
      if (parent instanceof ViewGroup) {
         return findView((ViewGroup) parent, viewClass);
      } else {
         throw new IllegalStateException();
      }
   }

   private static <V> V findView(final ViewGroup parent,
                                 final Class<V> viewClass)
   {
      int applyingIndex = $$KoshianInternal.applyingIndex;

      if (applyingIndex >= parent.getChildCount()) { return null; }

      final View child = parent.getChildAt(applyingIndex);
      if (!child.getClass().equals(viewClass)) { return null; }

      $$KoshianInternal.applyingIndex = applyingIndex + 1;

      @SuppressWarnings("unchecked")
      final V casted = (V) child;

      return casted;
   }
}
