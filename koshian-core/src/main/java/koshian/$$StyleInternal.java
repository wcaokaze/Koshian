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

import androidx.annotation.NonNull;

public final class $$StyleInternal {
   public static void applyCurrentStyleRecursive(
         final Koshian<? extends View, ?, ?, ?> koshian,
         final Context context
   ) {
      final KoshianStyle currentStyle = koshian.$$koshianInternal$style;
      if (currentStyle == null) { return; }
      applyStyleRecursive(currentStyle, koshian.$$koshianInternal$view, context);
   }

   static void applyCurrentStyleTo(
         final Koshian<?, ?, ?, ?> koshian,
         final View view,
         final Context context
   ) {
      final KoshianStyle currentStyle = koshian.$$koshianInternal$style;
      if (currentStyle == null) { return; }
      applyStyle(currentStyle, view, context);
   }

   private static void applyStyleRecursive(
         @NonNull final KoshianStyle style,
         final View view,
         final Context context
   ) {
      applyStyle(style, view, context);

      if (view instanceof ViewGroup) {
         final ViewGroup viewGroup = (ViewGroup) view;

         for (int i = 0; i < viewGroup.getChildCount(); i++) {
            applyStyleRecursive(style, viewGroup.getChildAt(i), context);
         }
      }
   }

   private static <V extends View>
         void applyStyle(@NonNull final KoshianStyle style, final V view, final Context context)
   {
      @SuppressWarnings("unchecked")
      final KoshianStyle.StyleElement<V> styleElement =
            (KoshianStyle.StyleElement<V>) style.$$koshianInternal$elementMap
                  .get(view.getClass());

      if (styleElement == null) { return; }

      styleElement.applyStyleTo(view, context);
   }
}
