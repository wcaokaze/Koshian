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

package com.wcaokaze.koshian.example

import android.content.Context
import android.view.View
import android.widget.TextView
import koshian.*
import koshian.recyclerview.*
import kotlin.contracts.ExperimentalContracts

sealed class FizzBuzzRecyclerViewItem
object FizzItem     : FizzBuzzRecyclerViewItem()
object BuzzItem     : FizzBuzzRecyclerViewItem()
object FizzBuzzItem : FizzBuzzRecyclerViewItem()
class NormalItem(val value: Int) : FizzBuzzRecyclerViewItem()

class FizzBuzzRecyclerViewAdapter : KoshianRecyclerViewAdapter<FizzBuzzRecyclerViewItem>() {
   override fun selectViewHolderProvider(
         position: Int,
         item: FizzBuzzRecyclerViewItem
   ): ViewHolderProvider<*> {
      return when (item) {
         is FizzItem     -> ViewHolderProvider(item, ::FizzItemViewHolder)
         is BuzzItem     -> ViewHolderProvider(item, ::BuzzItemViewHolder)
         is FizzBuzzItem -> ViewHolderProvider(item, ::FizzBuzzItemViewHolder)
         is NormalItem   -> ViewHolderProvider(item, ::NormalItemViewHolder)
      }
   }
}

class FizzItemViewHolder(context: Context) : KoshianViewHolder<FizzItem>() {
   override val itemView: View

   override fun bind(item: FizzItem) {
      // nop
   }

   init {
      @OptIn(ExperimentalContracts::class)
      itemView = koshian(context) {
         TextView {
            layout.margins = 16.dip
            view.minHeight = 24.dip
            view.text = "Fizz"
         }
      }
   }
}

class BuzzItemViewHolder(context: Context) : KoshianViewHolder<BuzzItem>() {
   override val itemView: View

   override fun bind(item: BuzzItem) {
      // nop
   }

   init {
      @OptIn(ExperimentalContracts::class)
      itemView = koshian(context) {
         TextView {
            layout.margins = 16.dip
            view.minHeight = 24.dip
            view.text = "Buzz"
         }
      }
   }
}

class FizzBuzzItemViewHolder(context: Context) : KoshianViewHolder<FizzBuzzItem>() {
   override val itemView: View

   override fun bind(item: FizzBuzzItem) {
      // nop
   }

   init {
      @OptIn(ExperimentalContracts::class)
      itemView = koshian(context) {
         TextView {
            layout.margins = 16.dip
            view.minHeight = 24.dip
            view.text = "FizzBuzz"
         }
      }

      itemView.applyKoshian {
      }
   }
}

class NormalItemViewHolder(context: Context) : KoshianViewHolder<NormalItem>() {
   override val itemView: TextView

   override fun bind(item: NormalItem) {
      itemView.text = item.value.toString()
   }

   init {
      @OptIn(ExperimentalContracts::class)
      itemView = koshian(context) {
         TextView {
            layout.margins = 16.dip
            view.minHeight = 24.dip
         }
      }
   }
}
