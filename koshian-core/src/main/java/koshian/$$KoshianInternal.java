package koshian;

import android.annotation.*;
import android.content.*;
import android.util.DisplayMetrics;
import android.view.*;

import com.wcaokaze.koshian.R;

import java.util.Iterator;

public final class $$KoshianInternal {
   private static float displayDensity = 0.0f;
   private static float scaledDensity  = 0.0f;

   @SuppressLint("StaticFieldLeak")
   public static Context context = null;

   @SuppressWarnings("rawtypes")
   public static KoshianViewGroupConstructor parentViewConstructor = null;

   public static int applyingIndex = -1;

   public static void init(final Context context) {
      final DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
      displayDensity = displayMetrics.density;
      scaledDensity  = displayMetrics.scaledDensity;
   }

   public static int dipToPx(final int dipValue) {
      final int pxValue = (int) (dipValue * displayDensity);

      if (pxValue != 0) {
         return pxValue;
      } else if (dipValue > 0) {
         return 1;
      } else {
         return -1;
      }
   }

   public static int dipToPx(final float dipValue) {
      final int pxValue = (int) (dipValue * displayDensity);

      if (pxValue != 0) {
         return pxValue;
      } else if (dipValue > 0) {
         return 1;
      } else {
         return -1;
      }
   }

   public static int dipToPx(final double dipValue) {
      final int pxValue = (int) (dipValue * displayDensity);

      if (pxValue != 0) {
         return pxValue;
      } else if (dipValue > 0) {
         return 1;
      } else {
         return -1;
      }
   }

   public static float spToPx(final int spValue) {
      return spValue * scaledDensity;
   }

   public static float spToPx(final float spValue) {
      return spValue * scaledDensity;
   }

   public static float spToPx(final double spValue) {
      return (float) (spValue * scaledDensity);
   }

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

   public static <V> Iterator<V>
         findViewByName(final ViewManager parent,
                        final String name,
                        final Class<V> viewClass)
   {
      if (parent instanceof ViewGroup) {
         return findViewByName((ViewGroup) parent, name, viewClass);
      } else {
         throw new IllegalStateException();
      }
   }

   private static <V> V findView(final ViewGroup parent,
                                 final Class<V> viewClass)
   {
      int applyingIndex = $$KoshianInternal.applyingIndex;

      for (; applyingIndex < parent.getChildCount(); applyingIndex++) {
         final View child = parent.getChildAt(applyingIndex);
         final Object childName = child.getTag(R.id.view_tag_name);

         if (childName != null) { continue; }
         if (!child.getClass().equals(viewClass)) { return null; }

         $$KoshianInternal.applyingIndex = applyingIndex + 1;

         @SuppressWarnings("unchecked")
         final V casted = (V) child;

         return casted;
      }

      $$KoshianInternal.applyingIndex = applyingIndex;
      return null;
   }

   private static <V> Iterator<V>
         findViewByName(final ViewGroup parent,
                        final String name,
                        final Class<V> viewClass)
   {
      return new ViewNameFilterIterator<>(parent, name, viewClass);
   }

   private static final class ViewNameFilterIterator<V> implements Iterator<V> {
      private final ViewGroup mParent;
      private final String mName;
      private final Class<V> mViewClass;

      private View mNextView;
      private int mNextViewIndex;

      ViewNameFilterIterator(final ViewGroup parent,
                             final String name,
                             final Class<V> viewClass)
      {
         mParent = parent;
         mName = name;
         mViewClass = viewClass;

         for (int i = 0; i < parent.getChildCount(); i++) {
            final View child = parent.getChildAt(i);
            final String childName = (String) child.getTag(R.id.view_tag_name);

            if (childName == null) { continue; }
            if (!childName.equals(name)) { continue; }
            if (!child.getClass().equals(mViewClass)) { continue; }

            mNextView = child;
            mNextViewIndex = i;

            return;
         }
      }

      @Override
      public boolean hasNext() {
         return mNextView != null;
      }

      @Override
      public V next() {
         final ViewGroup parent = mParent;

         @SuppressWarnings("unchecked")
         final V next = (V) mNextView;

         for (int i = mNextViewIndex + 1; i < parent.getChildCount(); i++) {
            final View child = parent.getChildAt(i);
            final String childName = (String) child.getTag(R.id.view_tag_name);

            if (childName == null) { continue; }
            if (!childName.equals(mName)) { continue; }
            if (!child.getClass().equals(mViewClass)) { continue; }

            mNextView = child;
            mNextViewIndex = i;

            return next;
         }

         mNextView = null;
         mNextViewIndex = parent.getChildCount();

         return next;
      }
   };
}
