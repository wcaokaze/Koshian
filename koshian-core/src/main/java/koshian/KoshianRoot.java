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

import android.content.*;
import android.view.*;

public final class KoshianRoot implements ViewManager {
   public static final KoshianRoot INSTANCE = new KoshianRoot();

   public static final KoshianViewGroupConstructor<ViewGroup, ViewGroup.LayoutParams>
         CONSTRUCTOR = new KoshianRootConstructor();

   private static final class KoshianRootConstructor
         implements KoshianViewGroupConstructor<ViewGroup, ViewGroup.LayoutParams>
   {
      @Override
      public ViewGroup instantiate(final Context context) {
         throw new UnsupportedOperationException();
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
