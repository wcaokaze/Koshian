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

import kotlin.jvm.functions.Function1;

public abstract class KoshianViewConstructor<V extends View> {
   private Function1<V, Void> mStyleAction = null;

   protected KoshianViewConstructor(final Class<V> targetViewClass) {
      $$StyleInternal.addViewConstructor(targetViewClass, this);
   }

   public abstract V instantiate(Context context);

   public final Function1<V, Void> getStyleAction() {
      return mStyleAction;
   }

   public final void setStyleAction(final Function1<V, Void> styleAction) {
      mStyleAction = styleAction;
   }
}
