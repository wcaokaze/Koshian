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

/**
 * ```kotlin
 * sealed class TimelineItem
 * class StatusItem(val status: Status) : TimelineItem()
 * object LoadingIndicatorItem : TimelineItem()
 *
 * class StatusViewHolder(context: Context) : KoshianViewHolder<StatusItem>() {
 *    override val itemView: View
 *
 *    private val contentView: TextView
 *
 *    override fun bind(item: StatusItem) {
 *       contentView.text = item.status.content
 *    }
 *
 *    init {
 *       itemView = koshian(context) {
 *          LinearLayout {
 *             view.orientation = VERTICAL
 *
 *             contentView = TextView {
 *             }
 *          }
 *       }
 *    }
 * }
 *
 * class TimelineRecyclerViewAdapter : KoshianRecyclerViewAdapter<TimelineItem>() {
 *    override fun selectViewHolderProvider
 *          (position: Int, item: TimelineItem): ViewHolderProvider<*>
 *    {
 *       return when (item) {
 *          is StatusItem -> ViewHolderProvider(item, ::StatusViewHolder)
 *          is LoadingIndicatorItem -> ViewHolderProvider(item, ::LoadingIndicatorViewHolder)
 *       }
 *    }
 * }
 * ```
 * When the Items implement [DiffUtilItem], this adapter uses [DiffUtil].
 */
abstract class KoshianRecyclerViewAdapter<I>
      : RecyclerView.Adapter<RecyclerView.ViewHolder>()
{
   private var viewTypeMap = emptyArray<ViewHolderProvider<*>>()

   private val diffUtilCallback = object : DiffUtil.ItemCallback<I>() {
      override fun areContentsTheSame(oldItem: I, newItem: I): Boolean {
         if (oldItem !is DiffUtilItem || newItem !is DiffUtilItem) {
            return false
         }

         return oldItem.isContentsTheSameWith(newItem)
      }

      override fun areItemsTheSame(oldItem: I, newItem: I): Boolean {
         if (oldItem !is DiffUtilItem || newItem !is DiffUtilItem) {
            return false
         }

         return oldItem.isItemsTheSameWith(newItem)
      }

      override fun getChangePayload(oldItem: I, newItem: I): Any? {
         if (oldItem !is DiffUtilItem || newItem !is DiffUtilItem) {
            return null
         }

         return oldItem.getChangePayload(newItem)
      }
   }

   @Suppress("LeakingThis")
   private val differ = AsyncListDiffer(this, diffUtilCallback)

   protected abstract fun selectViewHolderProvider(position: Int, item: I): ViewHolderProvider<*>

   var items: List<I>
      get() = differ.currentList
      set(value) { differ.submitList(value) }

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
