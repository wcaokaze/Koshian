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

package koshian.recyclerview

import android.view.*

abstract class KoshianViewHolder<in I> {
   internal var androidxViewHolderImpl: AndroidxViewHolderImpl<@UnsafeVariance I>? = null

   abstract val itemView: View

   abstract fun bind(item: I)

   private fun getAndroidxViewHolderOrThrow(): AndroidxViewHolderImpl<I> {
      return androidxViewHolderImpl
            ?: throw IllegalStateException(
                  "This ViewHolder has not initialized yet. " +
                  "Maybe you attempt to get some property in the constructor?"
            )
   }

   @Deprecated("Use bindingAdapterPosition or absoluteAdapterPosition", ReplaceWith("bindingAdapterPosition"))
   val adapterPosition: Int get() = getAndroidxViewHolderOrThrow().adapterPosition

   val bindingAdapterPosition:  Int get() = getAndroidxViewHolderOrThrow().bindingAdapterPosition
   val absoluteAdapterPosition: Int get() = getAndroidxViewHolderOrThrow().absoluteAdapterPosition
   val layoutPosition:          Int get() = getAndroidxViewHolderOrThrow().layoutPosition
   val oldPosition:             Int get() = getAndroidxViewHolderOrThrow().oldPosition

   var isRecyclable
      get() = getAndroidxViewHolderOrThrow().isRecyclable
      set(value) { getAndroidxViewHolderOrThrow().setIsRecyclable(value) }
}
