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

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

public final class $$StyleInternal {
   public static KoshianStyle style = null;

   public static void applyCurrentStyleRecursive(final View view) {
      final KoshianStyle currentStyle = style;
      if (currentStyle == null) { return; }

      applyStyleRecursive(currentStyle, view);
   }

   static void applyCurrentStyleTo(final View view) {
      final KoshianStyle currentStyle = style;
      if (currentStyle == null) { return; }

      applyStyle(currentStyle, view);
   }

   private static void applyStyleRecursive(@NonNull final KoshianStyle style,
                                           final View view)
   {
      applyStyle(style, view);

      if (view instanceof ViewGroup) {
         final ViewGroup viewGroup = (ViewGroup) view;

         for (int i = 0; i < viewGroup.getChildCount(); i++) {
            applyCurrentStyleRecursive(viewGroup.getChildAt(i));
         }
      }
   }

   private static <V extends View>
         void applyStyle(@NonNull final KoshianStyle style, final V view)
   {
      @SuppressWarnings("unchecked")
      final KoshianStyle.StyleElement<V> styleElement =
            (KoshianStyle.StyleElement<V>) style.$$koshianInternal$elementMap
                  .get(view.getClass());

      if (styleElement == null) { return; }

      styleElement.applyStyleTo(view);
   }
}
