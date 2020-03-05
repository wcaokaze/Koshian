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

   public static void assertNextView(final Object parent, final View view) {
      if (!(parent instanceof ViewGroup)) {
         throw new IllegalStateException("A View(" + view + ") was specified " +
               "but there is no ViewGroup in current Koshian.");
      }

      final ViewGroup viewGroup = (ViewGroup) parent;
      final int applyingIndex = $$KoshianInternal.applyingIndex;

      if (applyingIndex == -1) {
         throw new IllegalStateException("A View (" + view + ") was specified " +
               "but it seems that current Koshian is not in Applier context.");
      }

      if (applyingIndex >= viewGroup.getChildCount()) {
         throw new AssertionError("A View (" + view + ") was specified " +
               "but current Koshian has children no longer.");
      }

      final View nextView = viewGroup.getChildAt(applyingIndex);

      if (view != nextView) {
         throw new AssertionError("A View (" + view + ") was specified " +
               "but it does not match with the next View. " +
               "(Information: the next View was " + nextView + ")");
      }
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
