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

   @Test fun rootView() {
      class Style : KoshianStyle() {
         override fun defaultStyle() {
            TextView {
               view.hint = "Koshian"
            }
         }
      }

      activityScenarioRule.scenario.onActivity { activity ->
         @OptIn(ExperimentalContracts::class)
         val v = koshian(activity) {
            TextView {
            }
         }

         v.applyKoshian(Style()) {
            view.text = "Koshian"
         }

         assertEquals("Koshian", v.hint)
         assertEquals("Koshian", v.text)
      }
   }

   @Test fun viewGroup() {
      class Style : KoshianStyle() {
         override fun defaultStyle() {
            FrameLayout {
               view.elevation = 4.0f
            }
         }
      }

      activityScenarioRule.scenario.onActivity { activity ->
         @OptIn(ExperimentalContracts::class)
         val v = koshian(activity) {
            FrameLayout {
            }
         }

         v.applyKoshian(Style()) {
            view.alpha = 0.4f
         }

         assertEquals(4.0f, v.elevation)
         assertEquals(0.4f, v.alpha)
      }
   }

   @Test fun nestedViews() {
      class Style : KoshianStyle() {
         override fun defaultStyle() {
            TextView {
               view.text = "Koshian"
            }

            FrameLayout {
               view.elevation = 4.0f
            }
         }
      }

      activityScenarioRule.scenario.onActivity { activity ->
         @OptIn(ExperimentalContracts::class)
         val v = koshian(activity) {
            FrameLayout {
               FrameLayout {
                  TextView {
                  }
               }

               TextView {
               }
            }
         }

         v.applyKoshian(Style()) {
         }

         assertEquals(4.0f, v.elevation)
         assertEquals(2, v.childCount)

         val child1 = v.getChildAt(0)
         assertTrue(child1 is FrameLayout)
         assertEquals(4.0f, child1.elevation)
         assertEquals(1, child1.childCount)

         val child1_1 = child1.getChildAt(0)
         assertTrue(child1_1 is TextView)
         assertEquals("Koshian", child1_1.text)

         val child2 = v.getChildAt(1)
         assertTrue(child2 is TextView)
         assertEquals("Koshian", child2.text)
      }
   }

   @Test fun nestedViews_withApplying() {
      class Style : KoshianStyle() {
         override fun defaultStyle() {
            TextView {
               view.hint = "Koshian"
            }

            FrameLayout {
               view.elevation = 4.0f
            }
         }
      }

      activityScenarioRule.scenario.onActivity { activity ->
         @OptIn(ExperimentalContracts::class)
         val v = koshian(activity) {
            FrameLayout {
               FrameLayout {
                  TextView {
                  }
               }

               TextView {
               }
            }
         }

         v.applyKoshian(Style()) {
            view.alpha = 0.1f

            FrameLayout {
               view.alpha = 0.2f

               TextView {
                  view.text = "TextView1"
               }
            }

            TextView {
               view.text = "TextView2"
            }
         }

         assertEquals(4.0f, v.elevation)
         assertEquals(0.1f, v.alpha)
         assertEquals(2, v.childCount)

         val child1 = v.getChildAt(0)
         assertTrue(child1 is FrameLayout)
         assertEquals(4.0f, child1.elevation)
         assertEquals(0.2f, child1.alpha)
         assertEquals(1, child1.childCount)

         val child1_1 = child1.getChildAt(0)
         assertTrue(child1_1 is TextView)
         assertEquals("Koshian", child1_1.hint)
         assertEquals("TextView1", child1_1.text)

         val child2 = v.getChildAt(1)
         assertTrue(child2 is TextView)
         assertEquals("Koshian", child2.hint)
         assertEquals("TextView2", child2.text)
      }
   }
}
