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

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import koshian.*
import koshian.recyclerview.*
import kotlin.contracts.ExperimentalContracts

class MainActivity : Activity() {
   private lateinit var recyclerViewAdapter: FizzBuzzRecyclerViewAdapter

   private fun showKoshianGitHubRepository() {
      val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/wcaokaze/Koshian"))
      startActivity(intent)
   }

   private fun prepareFizzBuzzItems() {
      recyclerViewAdapter.items = FizzBuzzItemSequence().take(100).toList()
   }

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      buildLayout()
      prepareFizzBuzzItems()
   }

   private fun buildLayout() {
      @OptIn(ExperimentalContracts::class)
      val contentView = koshian(this) {
         LinearLayout {
            view.orientation = VERTICAL

            TextView {
               view.text = "This is an example app for Koshian."
            }

            TextView {
               view.text = "https://github.com/wcaokaze/Koshian"
               view.setOnClickListener { showKoshianGitHubRepository() }
            }

            RecyclerView {
               recyclerViewAdapter = FizzBuzzRecyclerViewAdapter()
               view.adapter = recyclerViewAdapter
               view.layoutManager = LinearLayoutManager(context)
            }
         }
      }

      contentView.applyKoshian(MainActivityStyle()) {
         view.padding = 16.dip

         TextView {
         }

         TextView {
            view.textColor = 0x03a9f4.opaque
         }

         Space {
            layout.height = 24.dip
         }

         RecyclerView {
            layout.width = MATCH_PARENT
            layout.height = 0
            layout.weight = 1.0f
         }
      }

      setContentView(contentView)
   }

   class MainActivityStyle : KoshianStyle() {
      override fun defaultStyle() {
         TextView {
            view.padding = 8.dip
            view.textSizeSp = 16
         }
      }
   }
}
