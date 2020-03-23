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

package koshian

import androidx.test.ext.junit.rules.*
import androidx.test.ext.junit.runners.*
import org.junit.*
import org.junit.runner.*
import kotlin.test.*
import kotlin.test.Test

import android.widget.*
import kotlin.contracts.*

@RunWith(AndroidJUnit4::class)
class StyleTest {
   @get:Rule
   val activityScenarioRule = activityScenarioRule<EmptyTestActivity>()

   class Style : KoshianStyle() {
      override fun defaultStyle() {
         TextView {
            view.text = "Koshian"
         }
      }
   }

   @Test fun rootView() {
      activityScenarioRule.scenario.onActivity { activity ->
         @OptIn(ExperimentalContracts::class)
         val v = koshian(activity) {
            TextView {
            }
         }

         v.applyKoshian(Style()) {
         }

         assertEquals("Koshian", v.text)
      }
   }
}
