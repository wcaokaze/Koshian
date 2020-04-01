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

sealed class Item
class ItemA : Item()
class ItemB : Item()
class ItemC : Item()

class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
   var items: List<Item> = emptyList()
      set(value) {
         field = value
         notifyDataSetChanged()
      }

   override fun getItemCount() = items.size

   override fun getItemViewType(position: Int): Int {
      return when (items[position]) {
         is ItemA -> 0
         is ItemB -> 1
         is ItemC -> 2
      }
   }

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
      return when (viewType) {
         0 -> ItemAViewHolder(parent.context)
         1 -> ItemBViewHolder(parent.context)
         2 -> ItemCViewHolder(parent.context)
         else -> throw NoWhenBranchMatchedException()
      }
   }

   override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
      when (holder) {
         is ItemAViewHolder -> holder.bind(items[position] as ItemA)
         is ItemBViewHolder -> holder.bind(items[position] as ItemB)
         is ItemCViewHolder -> holder.bind(items[position] as ItemC)
      }
   }
}

@OptIn(ExperimentalContracts::class)
fun createItemAView(context: Context) = koshian(context) {
   FrameLayout {
   }
}

class ItemAViewHolder(context: Context) : RecyclerView.ViewHolder(createItemAView(context)) {
   fun bind(itemA: ItemA) {
   }
}

@OptIn(ExperimentalContracts::class)
fun createItemBView(context: Context) = koshian(context) {
   FrameLayout {
   }
}

class ItemBViewHolder(context: Context) : RecyclerView.ViewHolder(createItemBView(context)) {
   fun bind(itemB: ItemB) {
   }
}

@OptIn(ExperimentalContracts::class)
fun createItemCView(context: Context) = koshian(context) {
   FrameLayout {
   }
}

class ItemCViewHolder(context: Context) : RecyclerView.ViewHolder(createItemCView(context)) {
   fun bind(itemC: ItemC) {
   }
}
