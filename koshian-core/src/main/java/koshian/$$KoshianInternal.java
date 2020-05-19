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

public final class $$KoshianInternal {
   private static float displayDensity = 0.0f;
   private static float scaledDensity  = 0.0f;

   @SuppressLint("StaticFieldLeak")
   public static Context context = null;

   @SuppressWarnings("rawtypes")
   public static KoshianViewGroupConstructor parentViewConstructor = null;

   public static void init(final Context context) {
      final DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
      displayDensity = displayMetrics.density;
      scaledDensity  = displayMetrics.scaledDensity;
   }

   public static int dpToPx(final int dpValue) {
      final int pxValue = (int) (dpValue * displayDensity);

      if (pxValue != 0) {
         return pxValue;
      } else if (dpValue > 0) {
         return 1;
      } else {
         return -1;
      }
   }

   public static int dpToPx(final float dpValue) {
      final int pxValue = (int) (dpValue * displayDensity);

      if (pxValue != 0) {
         return pxValue;
      } else if (dpValue > 0) {
         return 1;
      } else {
         return -1;
      }
   }

   public static int dpToPx(final double dpValue) {
      final int pxValue = (int) (dpValue * displayDensity);

      if (pxValue != 0) {
         return pxValue;
      } else if (dpValue > 0) {
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
}
