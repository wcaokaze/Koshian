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

abstract class NoDiffUtilKoshianRecyclerViewAdapter<I>
      : KoshianRecyclerViewAdapter<I>()
{
   override var items: List<I> = emptyList()
      set(value) {
         field = value
         notifyDataSetChanged()
      }
}
