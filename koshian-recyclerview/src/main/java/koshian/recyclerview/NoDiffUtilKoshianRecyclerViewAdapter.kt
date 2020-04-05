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
 * class StatusItem(val status: Status)
 * object LoadingIndicatorItem
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
 * ```
 * ```kotlin
 * koshian(context) {
 *    RecyclerView {
 *       view.adapter = KoshianRecyclerViewAdapter<TimelineItem> { _, item ->
 *          when (item) {
 *             is StatusItem -> ViewHolderProvider(::StatusViewHolder)
 *             is LoadingIndicatorItem -> ViewHolderProvider(::LoadingIndicatorViewHolder)
 *          }
 *       }
 *    }
 * }
 * ```
 */
@Suppress("FunctionName")
inline fun <I> KoshianRecyclerViewAdapter(
      crossinline viewHolderProviderSelector: (position: Int, item: I) -> ViewHolderProvider<*>
): NoDiffUtilKoshianRecyclerViewAdapter<I> {
   return object : NoDiffUtilKoshianRecyclerViewAdapter<I>() {
      override fun selectViewHolderProvider(position: Int, item: I): ViewHolderProvider<*> {
         return viewHolderProviderSelector(position, item)
      }
   }
}

/**
 * ```kotlin
 * sealed class TimelineItem
 * class StatusItem(val status: Status)
 * object LoadingIndicatorItem
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
 * ```
 * ```kotlin
 * koshian(context) {
 *    RecyclerView {
 *       view.adapter = KoshianRecyclerViewAdapter<TimelineItem> { _, item ->
 *          when (item) {
 *             is StatusItem -> ViewHolderProvider(::StatusViewHolder)
 *             is LoadingIndicatorItem -> ViewHolderProvider(::LoadingIndicatorViewHolder)
 *          }
 *       }
 *    }
 * }
 * ```
 *
 * To use [DiffUtil], see [KoshianRecyclerViewAdapter]
 */
abstract class NoDiffUtilKoshianRecyclerViewAdapter<I>
      : RecyclerView.Adapter<RecyclerView.ViewHolder>()
{
   private var viewTypeMap = emptyArray<ViewHolderProvider<*>>()

   var items: List<I> = emptyList()
      set(value) {
         field = value
         notifyDataSetChanged()
      }

   protected abstract fun selectViewHolderProvider(position: Int, item: I): ViewHolderProvider<*>

   final override fun getItemCount() = items.size

   final override fun getItemViewType(position: Int): Int {
      val viewHolderProvider = selectViewHolderProvider(
            position, items[position])

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
}