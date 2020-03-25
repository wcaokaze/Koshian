package koshian;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;

import com.wcaokaze.koshian.R;

import java.util.Iterator;

import kotlin.jvm.functions.Function1;

public final class $$ApplierInternal {
   public static int applyingIndex = -1;

   public static <V extends View>
         V addNewViewAndApplyStyle(final ViewManager parentView,
                                   final KoshianViewConstructor<V> childConstructor)
   {
      final V child = childConstructor.instantiate($$KoshianInternal.context);

      if (parentView instanceof ViewGroup) {
         final ViewGroup parentViewGroup = (ViewGroup) parentView;
         parentViewGroup.addView(child, applyingIndex++,
               $$KoshianInternal.parentViewConstructor.instantiateLayoutParams());
      } else {
         parentView.addView(
               child,
               $$KoshianInternal.parentViewConstructor.instantiateLayoutParams());
      }

      final Function1<V, Void> styleAction = childConstructor.getStyleAction();

      if (styleAction != null) {
         styleAction.invoke(child);
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

         $$StyleInternal.applyStyle(view);
      }
   }

   public static void invokeViewInKoshian(final Object parent,
                                          final KoshianApplicable applicable)
   {
      final View view = applicable.getView();

      if (applyingIndex == -1) {
         if (view.getParent() != null) {
            throw new IllegalStateException(
                  "An Applicable(" + applicable + ") was specified in a Creator, " +
                  "but the specified view was already added to another ViewGroup.");
         }

         addApplicable(parent, applicable);
      } else {
         if (view.getParent() == null) {
            insertApplicable(parent, applicable);
         } else {
            assertNextApplicable(parent, applicable);
         }
      }
   }

   private static void addView(final Object parent, final View view) {
      if (!(parent instanceof ViewGroup)) {
         throw new IllegalStateException("A View(" + view + ") was specified " +
               "but the current Koshian-View is not a ViewGroup.");
      }

      final ViewGroup viewGroup = (ViewGroup) parent;

      viewGroup.addView(
            view,
            $$KoshianInternal.parentViewConstructor.instantiateLayoutParams());
   }

   private static void addApplicable(final Object parent,
                                     final KoshianApplicable applicable)
   {
      if (!(parent instanceof ViewGroup)) {
         throw new IllegalStateException(
               "An Applicable(" + applicable + ") was specified " +
               "but the current Koshian-View is not a ViewGroup.");
      }

      final ViewGroup viewGroup = (ViewGroup) parent;

      viewGroup.addView(
            applicable.getView(),
            $$KoshianInternal.parentViewConstructor.instantiateLayoutParams());
   }

   private static void insertView(final Object parent, final View view) {
      if (!(parent instanceof ViewGroup)) {
         throw new IllegalStateException("A View(" + view + ") was specified " +
               "but the current Koshian-View is not a ViewGroup.");
      }

      final ViewGroup viewGroup = (ViewGroup) parent;

      viewGroup.addView(
            view,
            applyingIndex++,
            $$KoshianInternal.parentViewConstructor.instantiateLayoutParams());
   }

   private static void insertApplicable(final Object parent,
                                        final KoshianApplicable applicable)
   {
      if (!(parent instanceof ViewGroup)) {
         throw new IllegalStateException(
               "An Applicable(" + applicable + ") was specified " +
               "but the current Koshian-View is not a ViewGroup.");
      }

      final ViewGroup viewGroup = (ViewGroup) parent;

      viewGroup.addView(
            applicable.getView(),
            applyingIndex++,
            $$KoshianInternal.parentViewConstructor.instantiateLayoutParams());
   }

   private static void assertNextView(final Object parent, final View view) {
      if (!(parent instanceof ViewGroup)) {
         throw new IllegalStateException("A View(" + view + ") was specified " +
               "but the current Koshian-View is not a ViewGroup.");
      }

      final ViewGroup viewGroup = (ViewGroup) parent;
      final int applyingIndex = $$ApplierInternal.applyingIndex;

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

   private static void assertNextApplicable(final Object parent,
                                            final KoshianApplicable applicable)
   {
      if (!(parent instanceof ViewGroup)) {
         throw new IllegalStateException(
               "An Applicable(" + applicable + ") was specified " +
               "but the current Koshian-View is not a ViewGroup.");
      }

      final ViewGroup viewGroup = (ViewGroup) parent;
      final int applyingIndex = $$ApplierInternal.applyingIndex;

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
      int applyingIndex = $$ApplierInternal.applyingIndex;

      for (; applyingIndex < parent.getChildCount(); applyingIndex++) {
         final View child = parent.getChildAt(applyingIndex);
         final Object childName = child.getTag(R.id.view_tag_name);

         if (childName != null) { continue; }
         if (!child.getClass().equals(viewClass)) { return null; }

         $$ApplierInternal.applyingIndex = applyingIndex + 1;

         @SuppressWarnings("unchecked")
         final V casted = (V) child;

         return casted;
      }

      $$ApplierInternal.applyingIndex = applyingIndex;
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
   }
}
