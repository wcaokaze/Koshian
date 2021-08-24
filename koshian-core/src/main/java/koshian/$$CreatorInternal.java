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
import android.view.ViewManager;

import com.wcaokaze.koshian.R;

public class $$CreatorInternal {
   /**
    * Implementation for Creator
    * {@code View {} }
    */
   public static <V extends View, L extends ViewGroup.LayoutParams> V addNewView(
         final Object parent,
         final Context context,
         final KoshianViewConstructor<?, L> parentViewConstructor,
         final KoshianViewConstructor<V, ?> constructor
   ) {
      final V view = constructor.instantiate(context);
      final L layoutParams = parentViewConstructor.instantiateLayoutParams();

      ((ViewManager) parent).addView(view, layoutParams);

      return view;
   }

   /**
    * Implementation for Creator
    * {@code View("name") {} }
    */
   public static <V extends View, L extends ViewGroup.LayoutParams> V addNewView(
         final Object parent,
         final Context context,
         final String name,
         final KoshianViewConstructor<?, L> parentViewConstructor,
         final KoshianViewConstructor<V, ?> constructor
   ) {
      final V view = constructor.instantiate(context);
      view.setTag(R.id.view_tag_name, name);

      final L layoutParams = parentViewConstructor.instantiateLayoutParams();

      ((ViewManager) parent).addView(view, layoutParams);

      return view;
   }
}
