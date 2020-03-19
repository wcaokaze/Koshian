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

   public static void invokeViewInKoshian(final Object parent, final View view) {
      if (applyingIndex == -1) {
         if (view.getParent() != null) {
            throw new IllegalStateException("A View(" + view + ") was specified in a Creator, " +
                  "but the specified view was already added to another ViewGroup.");
         }

         addView(parent, view);
      } else {
         if (view.getParent() == null) {
            insertView(parent, view);
         } else {
            assertNextView(parent, view);
         }
      }
   }

   private static void addView(final Object parent, final View view) {
      if (!(parent instanceof ViewGroup)) {
         throw new IllegalStateException("A View(" + view + ") was specified " +
               "but the current Koshian-View is not a ViewGroup.");
      }

      final ViewGroup viewGroup = (ViewGroup) parent;
      viewGroup.addView(view, parentViewConstructor.instantiateLayoutParams());
   }

   private static void insertView(final Object parent, final View view) {
      if (!(parent instanceof ViewGroup)) {
         throw new IllegalStateException("A View(" + view + ") was specified " +
               "but the current Koshian-View is not a ViewGroup.");
      }

      final ViewGroup viewGroup = (ViewGroup) parent;

      viewGroup.addView(view, applyingIndex++,
            parentViewConstructor.instantiateLayoutParams());
   }

   private static void assertNextView(final Object parent, final View view) {
      if (!(parent instanceof ViewGroup)) {
         throw new IllegalStateException("A View(" + view + ") was specified " +
               "but the current Koshian-View is not a ViewGroup.");
      }

      final ViewGroup viewGroup = (ViewGroup) parent;
      final int applyingIndex = $$KoshianInternal.applyingIndex;

      if (applyingIndex >= viewGroup.getChildCount()) {
         throw new AssertionError("A View (" + view + ") " +
               "which is already added to some ViewGroup was specified, " +
               "but it does not match with the next View. " +
               "(Information: There is no next View)");
      }

      final View nextView = viewGroup.getChildAt(applyingIndex);

      if (view != nextView) {
         throw new AssertionError("A View (" + view + ") " +
               "which is already added to some ViewGroup was specified, " +
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
