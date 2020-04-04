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
import androidx.recyclerview.widget.*

abstract class DiffUtilKoshianRecyclerViewAdapter<I>
      : RecyclerView.Adapter<RecyclerView.ViewHolder>()
{
   private var viewTypeMap = emptyArray<ViewHolderProvider<*>>()

   private val diffUtilCallback = object : DiffUtil.ItemCallback<I>() {
      override fun areContentsTheSame(oldItem: I, newItem: I): Boolean {
         return this@DiffUtilKoshianRecyclerViewAdapter
               .areContentsTheSame(oldItem, newItem)
      }

      override fun areItemsTheSame(oldItem: I, newItem: I): Boolean {
         return this@DiffUtilKoshianRecyclerViewAdapter
               .areItemTheSame(oldItem, newItem)
      }

      override fun getChangePayload(oldItem: I, newItem: I): Any? {
         return this@DiffUtilKoshianRecyclerViewAdapter
               .getChangePayload(oldItem, newItem)
      }
   }

   @Suppress("LeakingThis")
   private val differ = AsyncListDiffer(this, diffUtilCallback)

   abstract fun areContentsTheSame(oldItem: I, newItem: I): Boolean
   abstract fun areItemTheSame(oldItem: I, newItem: I): Boolean

   open fun getChangePayload(oldItem: I, newItem: I): Any? = null

   var items: List<I>
      get() = differ.currentList
      set(value) { differ.submitList(value) }

   protected abstract fun selectViewHolderProvider(position: Int, item: I): ViewHolderProvider<*>

   final override fun getItemCount() = differ.currentList.size

   final override fun getItemViewType(position: Int): Int {
      val viewHolderProvider = selectViewHolderProvider(
            position, differ.currentList[position])

      var viewType = viewTypeMap.indexOfFirst { it::class == viewHolderProvider::class }

      if (viewType == -1) {
         viewTypeMap += viewHolderProvider
         viewType = viewTypeMap.lastIndex
      }

      return viewType
   }

   final override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
      val koshianViewHolder = viewTypeMap[viewType].provide(parent.context)
      return AndroidxViewHolderImpl(koshianViewHolder)
   }

   final override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
      bind<I>(holder, position)
   }

   private fun <I> bind(holder: RecyclerView.ViewHolder, position: Int) {
      @Suppress("UNCHECKED_CAST")
      (holder as AndroidxViewHolderImpl<I>).bind(differ.currentList[position] as I)
   }
}
