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

         @OptIn(ExperimentalContracts::class)
         val v = koshian(activity) {
            LinearLayout {
               View {
               }

               koshianTextView = TextView {
                  view.text = "Koshian Text"
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
               view.hint = "Koshian Hint"
            }

            View {
            }
         }

         assertEquals("Koshian Text", koshianTextView.text)
         assertEquals(WRAP_CONTENT, koshianTextView.layoutParams.width)
         assertEquals(MATCH_PARENT, koshianTextView.layoutParams.height)
         assertEquals("Koshian Hint", koshianTextView.hint)
      }
   }

   @Test fun cursor() {
      activityScenarioRule.scenario.onActivity { activity ->
         val creatorView1: View
         val creatorView2: View
         lateinit var applierView1: View
         lateinit var applierView2: View
         val textView: TextView

         @OptIn(ExperimentalContracts::class)
         val v = koshian(activity) {
            LinearLayout {
               creatorView1 = View {
               }

               textView = TextView {
               }

               creatorView2 = View {
               }
            }
         }

         v.applyKoshian {
            View {
               applierView1 = view
            }

            textView {
            }

            View {
               applierView2 = view
            }
         }

         assertSame(applierView1, creatorView1)
         assertSame(applierView2, creatorView2)
      }
   }

   @Test fun inNotViewGroupBuilder() {
      activityScenarioRule.scenario.onActivity { activity ->
         val koshianTextView: TextView

         @OptIn(ExperimentalContracts::class)
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

         @OptIn(ExperimentalContracts::class)
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

         @OptIn(ExperimentalContracts::class)
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

         @OptIn(ExperimentalContracts::class)
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

         @OptIn(ExperimentalContracts::class)
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

   @Test fun addView_inCreatorContext() {
      activityScenarioRule.scenario.onActivity { activity ->
         val addedView = View(activity)

         @OptIn(ExperimentalContracts::class)
         val v = koshian(activity) {
            LinearLayout {
               View {
               }
               addedView {
               }
               View {
               }
            }
         }

         assertEquals(3, v.childCount)
         assertEquals(View::class, v.getChildAt(0)::class)
         assertSame(addedView, v.getChildAt(1))
         assertEquals(View::class, v.getChildAt(2)::class)
      }
   }

   @Test fun addView_inNotViewGroupBuilder() {
      activityScenarioRule.scenario.onActivity { activity ->
         val addedView = View(activity)

         val exception = assertFailsWith<IllegalStateException> {
            @OptIn(ExperimentalContracts::class)
            koshian(activity) {
               View {
                  addedView {
                  }
               }
            }
         }

         val message = exception.message
         assertNotNull(message)
         assertTrue(addedView.toString() in message)
         assertTrue("current Koshian-View is not a ViewGroup." in message)
      }
   }

   @Test fun addView_alreadyAddedView() {
      activityScenarioRule.scenario.onActivity { activity ->
         val addedView: View

         @OptIn(ExperimentalContracts::class)
         koshian(activity) {
            LinearLayout {
               addedView = View {
               }
            }
         }

         val exception = assertFailsWith<IllegalStateException> {
            @OptIn(ExperimentalContracts::class)
            koshian(activity) {
               LinearLayout {
                  addedView {
                  }
               }
            }
         }

         val message = exception.message
         assertNotNull(message)
         assertTrue(addedView.toString() in message)
         assertTrue("already added to another ViewGroup." in message)
      }
   }
}
