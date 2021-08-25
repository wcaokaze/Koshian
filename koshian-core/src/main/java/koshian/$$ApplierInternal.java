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

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.wcaokaze.koshian.R;

import java.util.Iterator;

public final class $$ApplierInternal {
   /**
    * Implementation for Applier
    * {@code view {} }
    */
   public static void invokeViewInKoshian(
         final Koshian<?, ?, ?, ?> koshian,
         final View view
   ) {
      if (koshian.applyingIndex == -1) {
         if (view.getParent() != null) {
            throw new IllegalStateException("A View(" + view + ") was specified in a Creator, " +
                  "but the specified view was already added to another ViewGroup.");
         }

         addView(koshian, view);
      } else {
         if (view.getParent() == null) {
            insertView(koshian, view);
         } else {
            assertNextView(koshian, view);
         }

         $$StyleInternal.applyCurrentStyleTo(koshian, view, koshian.getContext());
      }
   }

   /**
    * Implementation for Applier
    * {@code applicable {} }
    */
   public static KoshianApplicable.ApplicableMode
         invokeViewInKoshian(
               final Koshian<?, ?, ?, ?> koshian,
               final KoshianApplicable<?> applicable
         )
   {
      final View view = applicable.getView();

      if (koshian.applyingIndex == -1) {
         if (view.getParent() != null) {
            throw new IllegalStateException(
                  "An Applicable(" + applicable + ") was specified in a Creator, " +
                  "but the specified view was already added to another ViewGroup.");
         }

         addApplicable(koshian, applicable);
         return KoshianApplicable.ApplicableMode.CREATION;
      } else {
         if (view.getParent() == null) {
            insertApplicable(koshian, applicable);
            return KoshianApplicable.ApplicableMode.INSERTION;
         } else {
            assertNextApplicable(koshian, applicable);
            return KoshianApplicable.ApplicableMode.ASSERTION;
         }
      }
   }

   private static void addView(
         final Koshian<?, ?, ?, ?> koshian,
         final View view
   ) {
      final Object parent = koshian.get$$koshianInternal$view();

      if (!(parent instanceof ViewGroup)) {
         throw new IllegalStateException("A View(" + view + ") was specified " +
               "but the current Koshian-View is not a ViewGroup.");
      }

      final ViewGroup viewGroup = (ViewGroup) parent;

      viewGroup.addView(
            view,
            (ViewGroup.LayoutParams) koshian.getViewConstructor().instantiateLayoutParams()
      );
   }

   private static void addApplicable(
         final Koshian<?, ?, ?, ?> koshian,
         final KoshianApplicable<?> applicable
   ) {
      final Object parent = koshian.get$$koshianInternal$view();

      if (!(parent instanceof ViewGroup)) {
         throw new IllegalStateException(
               "An Applicable(" + applicable + ") was specified " +
               "but the current Koshian-View is not a ViewGroup.");
      }

      final ViewGroup viewGroup = (ViewGroup) parent;

      viewGroup.addView(
            applicable.getView(),
            (ViewGroup.LayoutParams) koshian.getViewConstructor().instantiateLayoutParams()
      );
   }

   private static void insertView(
         final Koshian<?, ?, ?, ?> koshian,
         final View view
   ) {
      final Object parent = koshian.get$$koshianInternal$view();

      if (!(parent instanceof ViewGroup)) {
         throw new IllegalStateException("A View(" + view + ") was specified " +
               "but the current Koshian-View is not a ViewGroup.");
      }

      final ViewGroup viewGroup = (ViewGroup) parent;

      viewGroup.addView(
            view,
            koshian.applyingIndex++,
            (ViewGroup.LayoutParams) koshian.getViewConstructor().instantiateLayoutParams());
   }

   private static void insertApplicable(
         final Koshian<?, ?, ?, ?> koshian,
         final KoshianApplicable<?> applicable
   ) {
      final Object parent = koshian.get$$koshianInternal$view();

      if (!(parent instanceof ViewGroup)) {
         throw new IllegalStateException(
               "An Applicable(" + applicable + ") was specified " +
               "but the current Koshian-View is not a ViewGroup.");
      }

      final ViewGroup viewGroup = (ViewGroup) parent;

      viewGroup.addView(
            applicable.getView(),
            koshian.applyingIndex++,
            (ViewGroup.LayoutParams) koshian.getViewConstructor().instantiateLayoutParams());
   }

   private static void assertNextView(
         final Koshian<?, ?, ?, ?> koshian,
         final View view
   ) {
      final Object parent = koshian.get$$koshianInternal$view();

      if (!(parent instanceof ViewGroup)) {
         throw new IllegalStateException("A View(" + view + ") was specified " +
               "but the current Koshian-View is not a ViewGroup.");
      }

      final ViewGroup viewGroup = (ViewGroup) parent;
      final int applyingIndex = koshian.applyingIndex;

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

      koshian.applyingIndex++;
   }

   private static void assertNextApplicable(
         final Koshian<?, ?, ?, ?> koshian,
         final KoshianApplicable<?> applicable
   ) {
      final Object parent = koshian.get$$koshianInternal$view();

      if (!(parent instanceof ViewGroup)) {
         throw new IllegalStateException(
               "An Applicable(" + applicable + ") was specified " +
               "but the current Koshian-View is not a ViewGroup.");
      }

      final ViewGroup viewGroup = (ViewGroup) parent;
      final int applyingIndex = koshian.applyingIndex;

      if (applyingIndex >= viewGroup.getChildCount()) {
         throw new AssertionError("An Applicable (" + applicable + ") " +
               "which is already added to some ViewGroup was specified, " +
               "but it does not match with the next View. " +
               "(Information: There is no next View)");
      }

      final View nextView = viewGroup.getChildAt(applyingIndex);

      if (applicable.getView() != nextView) {
         throw new AssertionError("An Applicable (" + applicable + ") " +
               "which is already added to some ViewGroup was specified, " +
               "but it does not match with the next View. " +
               "(Information: the next View was " + nextView + ")");
      }

      koshian.applyingIndex++;
   }

   // ==========================================================================

   /**
    * Implementation for Applier
    * {@code View {} }
    */
   public static <V extends View> V findViewOrInsertNew(
         final Koshian<?, ?, ? extends ViewGroup.LayoutParams, ?> koshian,
         final KoshianViewConstructor<V, ?> childConstructor,
         final Class<V> viewClass
   ) {
      final ViewGroup parentViewGroup = (ViewGroup) koshian.get$$koshianInternal$view();
      assert parentViewGroup != null;

      V foundView = findView(koshian, parentViewGroup, viewClass);

      if (foundView == null) {
         foundView = insertNewView(koshian, parentViewGroup, childConstructor);
      }

      $$StyleInternal.applyCurrentStyleTo(koshian, foundView, koshian.getContext());

      return foundView;
   }

   /**
    * Implementation for Applier
    * {@code View(style) {} }
    */
   public static <V extends View> V findViewOrInsertNewAndApplyStyle(
         final Koshian<?, ?, ? extends ViewGroup.LayoutParams, ?> koshian,
         final KoshianViewConstructor<V, ?> childConstructor,
         final KoshianStyle.StyleElement<V> styleElement,
         final Class<V> viewClass
   ) {
      final ViewGroup parentViewGroup = (ViewGroup) koshian.get$$koshianInternal$view();
      assert parentViewGroup != null;

      V foundView = findView(koshian, parentViewGroup, viewClass);

      if (foundView == null) {
         foundView = insertNewView(koshian, parentViewGroup, childConstructor);
      }

      final Context context = koshian.getContext();
      $$StyleInternal.applyCurrentStyleTo(koshian, foundView, context);
      styleElement.applyStyleTo(foundView, context);

      return foundView;
   }

   private static <V extends View> V insertNewView(
         final Koshian<?, ?, ? extends ViewGroup.LayoutParams, ?> koshian,
         final ViewGroup parentView,
         final KoshianViewConstructor<V, ?> childConstructor
   ) {
      final V child = childConstructor.instantiate(koshian.getContext());

      parentView.addView(
            child,
            koshian.applyingIndex++,
            koshian.getViewConstructor().instantiateLayoutParams()
      );

      return child;
   }

   // ==========================================================================

   /**
    * Implementation for Applier
    * {@code View("name") {} }
    */
   public static <V> Iterator<V>
         findViewByName(final Object parent,
                        final String name,
                        final Class<V> viewClass)
   {
      return new ViewNameFilterIterator<>((ViewGroup) parent, name, viewClass);
   }

   private static <V> V findView(
         final Koshian<?, ?, ?, ?> koshian,
         final ViewGroup parent,
         final Class<V> viewClass
   ) {
      int applyingIndex = koshian.applyingIndex;

      for (; applyingIndex < parent.getChildCount(); applyingIndex++) {
         final View child = parent.getChildAt(applyingIndex);
         final Object childName = child.getTag(R.id.view_tag_name);

         if (childName != null) { continue; }
         if (!child.getClass().equals(viewClass)) { return null; }

         koshian.applyingIndex = applyingIndex + 1;

         @SuppressWarnings("unchecked")
         final V casted = (V) child;

         return casted;
      }

      koshian.applyingIndex = applyingIndex;
      return null;
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
   }
}
