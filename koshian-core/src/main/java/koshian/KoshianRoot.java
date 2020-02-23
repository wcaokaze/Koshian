package koshian;

import android.view.*;

public final class KoshianRoot implements ViewManager {
   public static final KoshianRoot INSTANCE = new KoshianRoot();

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
