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

import android.view.*
import android.widget.*
import kotlin.contracts.*

@RunWith(AndroidJUnit4::class)
class SpecifyingViewTest {
   @get:Rule
   val activityScenarioRule = activityScenarioRule<EmptyTestActivity>()

   @Test fun specifyView() {
      activityScenarioRule.scenario.onActivity { activity ->
         val koshianTextView: TextView

         @UseExperimental(ExperimentalContracts::class)
         val v = koshian(activity) {
            LinearLayout {
               View {
               }

               koshianTextView = TextView {
                  view.text = "Koshian"
               }

               View {
               }
            }
         }

         v.applyKoshian {
            View {
            }

            koshianTextView {
               layout.width  = WRAP_CONTENT
               layout.height = MATCH_PARENT
               view.textSize = 4.0f
            }

            View {
            }
         }
      }
   }

   @Test fun inNotViewGroupBuilder() {
      activityScenarioRule.scenario.onActivity { activity ->
         val koshianTextView: TextView

         @UseExperimental(ExperimentalContracts::class)
         val v = koshian(activity) {
            LinearLayout {
               View {
               }

               koshianTextView = TextView {
               }
            }
         }

         val exception = assertFailsWith<IllegalStateException> {
            v.applyKoshian {
               View {
                  koshianTextView {
                  }
               }
            }
         }

         val message = exception.message
         assertNotNull(message)
         assertTrue(koshianTextView.toString() in message)
         assertTrue("current Koshian-View is not a ViewGroup." in message)
      }
   }

   @Test fun notMatch() {
      activityScenarioRule.scenario.onActivity { activity ->
         val unmatchedView: View
         val koshianTextView: TextView

         @UseExperimental(ExperimentalContracts::class)
         val v = koshian(activity) {
            LinearLayout {
               unmatchedView = View {
               }

               koshianTextView = TextView {
               }
            }
         }

         val exception = assertFailsWith<AssertionError> {
            v.applyKoshian {
               koshianTextView {
               }
            }
         }

         val message = exception.message
         assertNotNull(message)
         assertTrue(koshianTextView.toString() in message)
         assertTrue("not match" in message)
         assertTrue(unmatchedView.toString() in message)
      }
   }

   @Test fun insertion_first() {
      activityScenarioRule.scenario.onActivity { activity ->

         @UseExperimental(ExperimentalContracts::class)
         val v = koshian(activity) {
            LinearLayout {
               View {
               }
            }
         }

         val insertedView = View(activity)

         v.applyKoshian {
            insertedView {
            }
         }

         assertEquals(2, v.childCount)
         assertSame(insertedView, v.getChildAt(0))
         assertEquals(View::class, v.getChildAt(1)::class)
      }
   }

   @Test fun insertion_middle() {
      activityScenarioRule.scenario.onActivity { activity ->

         @UseExperimental(ExperimentalContracts::class)
         val v = koshian(activity) {
            LinearLayout {
               View {
               }

               View {
               }
            }
         }

         val insertedView = View(activity)

         v.applyKoshian {
            View {
            }
            insertedView {
            }
            View {
            }
         }

         assertEquals(3, v.childCount)
         assertEquals(View::class, v.getChildAt(0)::class)
         assertSame(insertedView, v.getChildAt(1))
         assertEquals(View::class, v.getChildAt(2)::class)
      }
   }

   @Test fun insertion_last() {
      activityScenarioRule.scenario.onActivity { activity ->

         @UseExperimental(ExperimentalContracts::class)
         val v = koshian(activity) {
            LinearLayout {
               View {
               }
            }
         }

         val insertedView = View(activity)

         v.applyKoshian {
            View {
            }
            insertedView {
            }
         }

         assertEquals(2, v.childCount)
         assertEquals(View::class, v.getChildAt(0)::class)
         assertSame(insertedView, v.getChildAt(1))
      }
   }
}
