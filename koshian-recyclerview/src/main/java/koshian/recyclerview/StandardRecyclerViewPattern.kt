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
import kotlinx.coroutines.*

abstract class KoshianRecyclerViewAdapter<I> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
   private var viewTypeMap = emptyArray<ViewHolderProvider<*>>()
   private var diffUtilJob: Job = Job().apply { complete() }

   var items: List<I> = emptyList()
      @Synchronized
      set(value) {
         val oldItems = field
         field = value
         dispatchUpdateWithDiffUtil(oldItems, value)
      }

   protected open val shouldDetectMovesWithDiffUtil: Boolean = false

   protected open fun createDiffUtilCallback
         (oldItems: List<I>, newItems: List<I>): DiffUtil.Callback?
   {
      return null
   }

   abstract fun getViewHolderProvider(position: Int, item: I): ViewHolderProvider<*>

   final override fun getItemCount() = items.size

   final override fun getItemViewType(position: Int): Int {
      val viewHolderProvider = getViewHolderProvider(position, items[position])

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
      (holder as AndroidxViewHolderImpl<I>).bind(items[position] as I)
   }

   private fun dispatchUpdateWithDiffUtil(oldItems: List<I>, newItems: List<I>) {
      val oldJob = diffUtilJob

      diffUtilJob = GlobalScope.launch(Dispatchers.Default) {
         oldJob.join()

         val diffUtil = createDiffUtilCallback(oldItems, newItems)

         if (diffUtil == null) {
            withContext(Dispatchers.Main) {
               notifyDataSetChanged()
            }

            throw CancellationException()
         }

         val diffUtilResult = DiffUtil
               .calculateDiff(diffUtil, shouldDetectMovesWithDiffUtil)

         withContext(Dispatchers.Main) {
            diffUtilResult.dispatchUpdatesTo(this@KoshianRecyclerViewAdapter)
         }
      }
   }
}

class AndroidxViewHolderImpl<I>(
      private val koshianViewHolder: KoshianViewHolder<I>
) : RecyclerView.ViewHolder(koshianViewHolder.itemView) {
   fun bind(item: I) {
      koshianViewHolder.bind(item)
   }
}
