package koshian;

import android.content.*;
import android.view.*;

public final class KoshianRoot implements ViewManager {
   public static final KoshianRoot INSTANCE = new KoshianRoot();

   public static final KoshianViewParentConstructor<KoshianRoot, ViewGroup.LayoutParams>
         CONSTRUCTOR = new KoshianRootConstructor();

   private static final class KoshianRootConstructor
         implements KoshianViewParentConstructor<KoshianRoot, ViewGroup.LayoutParams>
   {
      @Override
      public KoshianRoot instantiate(final Context context) {
         return INSTANCE;
      }

      @Override
      public ViewGroup.LayoutParams instantiateLayoutParams() {
         return new ViewGroup.LayoutParams(
               ViewGroup.LayoutParams.WRAP_CONTENT,
               ViewGroup.LayoutParams.WRAP_CONTENT);
      }
   }

   private KoshianRoot() {}

   @Override
   public void addView(final View view, final ViewGroup.LayoutParams params) {
      view.setLayoutParams(params);
   }

   @Override
   public void removeView(final View view) {
      // nop
   }

   @Override
   public void updateViewLayout(final View view, final ViewGroup.LayoutParams params) {
      // nop
   }
}
