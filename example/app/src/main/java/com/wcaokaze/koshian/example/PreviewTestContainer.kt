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

import android.content.*
import androidx.recyclerview.widget.*
import koshian.*
import koshian.recyclerview.*
import kotlin.contracts.*

class PreviewTestContainer(context: Context) {
   @OptIn(ExperimentalContracts::class)
   val contentView = koshian(context) {
      LinearLayout {
         view.orientation = VERTICAL

         TextView {
            view.text = "This is an example app for Koshian."
         }

         TextView {
            view.text = "https://github.com/wcaokaze/Koshian"
         }

         RecyclerView {
            val recyclerViewAdapter = FizzBuzzRecyclerViewAdapter()
            view.adapter = recyclerViewAdapter
            view.layoutManager = LinearLayoutManager(context)
         }
      }
   }

   init {
      contentView.applyKoshian(MainActivity.MainActivityStyle()) {
         view.padding = 16.dp

         TextView {
         }

         TextView {
            view.textColor = 0x03a9f4.opaque
         }

         Space {
            layout.height = 24.dp
         }

         RecyclerView {
            layout.width = MATCH_PARENT
            layout.height = 0
            layout.weight = 1.0f
         }
      }
   }
}
