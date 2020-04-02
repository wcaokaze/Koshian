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

import android.content.*
import android.view.*
import androidx.recyclerview.widget.*
import koshian.*
import kotlin.contracts.*

typealias ViewHolderProvider<I> = (Context) -> KoshianViewHolder<I>

abstract class KoshianRecyclerViewAdapter<I> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
   private var viewTypeMap = emptyArray<ViewHolderProvider<I>>()

   var items: List<I> = emptyList()
      set(value) {
         field = value
         notifyDataSetChanged()
      }

   abstract fun getViewHolderProvider(position: Int, item: I): ViewHolderProvider<I>

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

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
      val koshianViewHolder = viewTypeMap[viewType].invoke(parent.context)
      return AndroidxViewHolderImpl(koshianViewHolder)
   }

   override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
      @Suppress("UNCHECKED_CAST")
      (holder as AndroidxViewHolderImpl<I>).bind(items[position])
   }
}

class AndroidxViewHolderImpl<I>(
      private val koshianViewHolder: KoshianViewHolder<I>
) : RecyclerView.ViewHolder(koshianViewHolder.itemView) {
   fun bind(item: I) {
      koshianViewHolder.bind(item)
   }
}

abstract class KoshianViewHolder<I> {
   abstract val itemView: View

   abstract fun bind(item: I)
}

// =============================================================================

sealed class Item
class ItemA : Item()
class ItemB : Item()
class ItemC : Item()

class RecyclerViewAdapter : KoshianRecyclerViewAdapter<Item>() {
   override fun getViewHolderProvider(position: Int, item: Item): ViewHolderProvider<Item> {
      return when (item) {
         is ItemA -> ::ItemAViewHolder
         is ItemB -> ::ItemBViewHolder
         is ItemC -> ::ItemCViewHolder
      }
   }
}

class ItemAViewHolder(context: Context) : KoshianViewHolder<Item>() {
   override val itemView: View

   override fun bind(item: Item) {
   }

   init {
      @OptIn(ExperimentalContracts::class)
      itemView = koshian(context) {
         FrameLayout {
         }
      }
   }
}

class ItemBViewHolder(context: Context) : KoshianViewHolder<Item>() {
   override val itemView: View

   override fun bind(item: Item) {
   }

   init {
      @OptIn(ExperimentalContracts::class)
      itemView = koshian(context) {
         FrameLayout {
         }
      }
   }
}

class ItemCViewHolder(context: Context) : KoshianViewHolder<Item>() {
   override val itemView: View

   override fun bind(item: Item) {
   }

   init {
      @OptIn(ExperimentalContracts::class)
      itemView = koshian(context) {
         FrameLayout {
         }
      }
   }
}
