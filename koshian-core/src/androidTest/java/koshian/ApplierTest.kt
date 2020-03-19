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
class ApplierTest {
   @get:Rule
   val activityScenarioRule = activityScenarioRule<EmptyTestActivity>()

   @Test fun apply() {
      activityScenarioRule.scenario.onActivity { activity ->
         @UseExperimental(ExperimentalContracts::class)
         val v = koshian(activity) {
            TextView {
            }
         }

         v.applyKoshian {
            view.text = "Koshian"
         }

         assertEquals("Koshian", v.text)
      }
   }

   @Test fun applyViewGroup() {
      activityScenarioRule.scenario.onActivity { activity ->
         @UseExperimental(ExperimentalContracts::class)
         val v = koshian(activity) {
            FrameLayout {
            }
         }

         v.applyKoshian {
            view.elevation = 4.0f
         }

         assertEquals(4.0f, v.elevation)
      }
   }

   @Test fun applyChildView() {
      activityScenarioRule.scenario.onActivity { activity ->
         @UseExperimental(ExperimentalContracts::class)
         val v = koshian(activity) {
            FrameLayout {
               TextView {
               }

               TextView {
               }
            }
         }

         v.applyKoshian {
            TextView {
               view.text = "TextView1"
            }

            TextView {
               view.text = "TextView2"
            }
         }

         val child1 = v.getChildAt(0)
         assertTrue(child1 is TextView)
         assertEquals("TextView1", child1.text)

         val child2 = v.getChildAt(1)
         assertTrue(child2 is TextView)
         assertEquals("TextView2", child2.text)
      }
   }

   @Test fun nestedViewGroup() {
      activityScenarioRule.scenario.onActivity { activity ->
         @UseExperimental(ExperimentalContracts::class)
         val v = koshian(activity) {
            FrameLayout {
               LinearLayout {
                  TextView {
                  }

                  TextView {
                  }
               }

               TextView {
               }
            }
         }

         v.applyKoshian {
            LinearLayout {
               TextView {
                  view.text = "TextView1_1"
               }

               TextView {
                  view.text = "TextView1_2"
               }
            }

            TextView {
               view.text = "TextView2"
            }
         }

         val child1 = v.getChildAt(0)
         assertTrue(child1 is LinearLayout)

         val child1_1 = child1.getChildAt(0)
         assertTrue(child1_1 is TextView)
         assertEquals("TextView1_1", child1_1.text)

         val child1_2 = child1.getChildAt(1)
         assertTrue(child1_2 is TextView)
         assertEquals("TextView1_2", child1_2.text)

         val child2 = v.getChildAt(1)
         assertTrue(child2 is TextView)
         assertEquals("TextView2", child2.text)
      }
   }

   @Test fun insert_first() {
      activityScenarioRule.scenario.onActivity { activity ->
         @UseExperimental(ExperimentalContracts::class)
         val v = koshian(activity) {
            FrameLayout {
               View {
               }
            }
         }

         v.applyKoshian {
            TextView {
               view.text = "TextView"
            }

            View {
               view.elevation = 4.0f
            }
         }

         val child1 = v.getChildAt(0)
         assertTrue(child1 is TextView)
         assertEquals("TextView", child1.text)

         val child2 = v.getChildAt(1)
         assertEquals(View::class, child2::class)
         assertEquals(4.0f, child2.elevation)
      }
   }

   @Test fun insert_middle() {
      activityScenarioRule.scenario.onActivity { activity ->
         @UseExperimental(ExperimentalContracts::class)
         val v = koshian(activity) {
            FrameLayout {
               View {
               }

               View {
               }
            }
         }

         v.applyKoshian {
            View {
               view.elevation = 4.0f
            }

            TextView {
               view.text = "TextView"
            }

            View {
               view.elevation = 8.0f
            }
         }

         val child1 = v.getChildAt(0)
         assertEquals(View::class, child1::class)
         assertEquals(4.0f, child1.elevation)

         val child2 = v.getChildAt(1)
         assertTrue(child2 is TextView)
         assertEquals("TextView", child2.text)

         val child3 = v.getChildAt(2)
         assertEquals(View::class, child3::class)
         assertEquals(8.0f, child3.elevation)
      }
   }

   @Test fun insert_last() {
      activityScenarioRule.scenario.onActivity { activity ->
         @UseExperimental(ExperimentalContracts::class)
         val v = koshian(activity) {
            FrameLayout {
               View {
               }
            }
         }

         v.applyKoshian {
            View {
               view.elevation = 4.0f
            }

            TextView {
               view.text = "TextView"
            }
         }

         val child1 = v.getChildAt(0)
         assertEquals(View::class, child1::class)
         assertEquals(4.0f, child1.elevation)

         val child2 = v.getChildAt(1)
         assertTrue(child2 is TextView)
         assertEquals("TextView", child2.text)
      }
   }

   @Test fun insert_intoEmptyView() {
      activityScenarioRule.scenario.onActivity { activity ->
         @UseExperimental(ExperimentalContracts::class)
         val v = koshian(activity) {
            FrameLayout {
            }
         }

         v.applyKoshian {
            TextView {
               view.text = "TextView"
            }
         }

         val child = v.getChildAt(0)
         assertTrue(child is TextView)
         assertEquals("TextView", child.text)
      }
   }

   @Test fun layoutParams_singleView() {
      activityScenarioRule.scenario.onActivity { activity ->
         @UseExperimental(ExperimentalContracts::class)
         val v = koshian(activity) {
            View {
            }
         }

         v.applyKoshian {
            layout.width = 4
         }

         assertEquals(4, v.layoutParams.width)
      }
   }

   @Test fun layoutParams_singleViewGroup() {
      activityScenarioRule.scenario.onActivity { activity ->
         @UseExperimental(ExperimentalContracts::class)
         val v = koshian(activity) {
            FrameLayout {
            }
         }

         v.applyKoshian {
            layout.width = 4
         }

         assertEquals(4, v.layoutParams.width)
      }
   }

   @Test fun layoutParams_viewInViewGroup() {
      activityScenarioRule.scenario.onActivity { activity ->
         @UseExperimental(ExperimentalContracts::class)
         val v = koshian(activity) {
            LinearLayout {
               View {
               }
            }
         }

         v.applyKoshian {
            View {
               layout.weight = 4.0f
            }
         }

         val layoutParams = v.getChildAt(0).layoutParams
         assertTrue(layoutParams is LinearLayout.LayoutParams)
         assertEquals(4.0f, layoutParams.weight)
      }
   }

   @Test fun layoutParams_viewGroupInViewGroup() {
      activityScenarioRule.scenario.onActivity { activity ->
         @UseExperimental(ExperimentalContracts::class)
         val v = koshian(activity) {
            LinearLayout {
               FrameLayout {
               }
            }
         }

         v.applyKoshian {
            FrameLayout {
               layout.weight = 4.0f
            }
         }

         val layoutParams = v.getChildAt(0).layoutParams
         assertTrue(layoutParams is LinearLayout.LayoutParams)
         assertEquals(4.0f, layoutParams.weight)
      }
   }

   @Test fun layoutParams_insertedView() {
      activityScenarioRule.scenario.onActivity { activity ->
         @UseExperimental(ExperimentalContracts::class)
         val v = koshian(activity) {
            LinearLayout {
            }
         }

         v.applyKoshian {
            View {
               layout.weight = 4.0f
            }
         }

         val layoutParams = v.getChildAt(0).layoutParams
         assertTrue(layoutParams is LinearLayout.LayoutParams)
         assertEquals(4.0f, layoutParams.weight)
      }
   }
}
