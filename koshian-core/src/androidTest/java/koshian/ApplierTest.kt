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

   @Test fun specifyView_inNotViewGroupBuilder() {
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
         assertTrue("no ViewGroup" in message)
      }
   }

   @Test fun specifyView_notMatch() {
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

   @Test fun naming() {
      activityScenarioRule.scenario.onActivity { activity ->
         @UseExperimental(ExperimentalContracts::class)
         val v = koshian(activity) {
            LinearLayout {
               TextView("View1") {
               }
            }
         }

         v.applyKoshian {
            TextView("View1") {
               view.text = "View1"
            }
         }

         val child = v.getChildAt(0)
         assertTrue(child is TextView)
         assertEquals("View1", child.text)
      }
   }

   @Test fun naming_viewGroup() {
      activityScenarioRule.scenario.onActivity { activity ->
         @UseExperimental(ExperimentalContracts::class)
         val v = koshian(activity) {
            LinearLayout {
               FrameLayout("View1") {
                  TextView {
                  }
               }
            }
         }

         v.applyKoshian {
            FrameLayout("View1") {
               TextView {
                  view.text = "Child"
               }
            }
         }

         val child = v.getChildAt(0)
         assertTrue(child is FrameLayout)

         assertEquals(1, child.childCount)
         val child2 = child.getChildAt(0)
         assertTrue(child2 is TextView)
         assertEquals("Child", child2.text)
      }
   }

   @Test fun naming_multipleViews() {
      activityScenarioRule.scenario.onActivity { activity ->
         @UseExperimental(ExperimentalContracts::class)
         val v = koshian(activity) {
            LinearLayout {
               TextView("View1") {
               }

               TextView("View1") {
               }
            }
         }

         v.applyKoshian {
            TextView("View1") {
               view.text = "View1"
            }
         }

         val child1 = v.getChildAt(0)
         assertTrue(child1 is TextView)
         assertEquals("View1", child1.text)

         val child2 = v.getChildAt(1)
         assertTrue(child2 is TextView)
         assertEquals("View1", child2.text)
      }
   }

   @Test fun naming_viewGroup_multipleViews() {
      activityScenarioRule.scenario.onActivity { activity ->
         @UseExperimental(ExperimentalContracts::class)
         val v = koshian(activity) {
            LinearLayout {
               FrameLayout("View1") {
                  TextView {
                  }
               }

               FrameLayout("View1") {
                  TextView {
                  }
               }
            }
         }

         v.applyKoshian {
            FrameLayout("View1") {
               TextView {
                  view.text = "Child"
               }
            }
         }

         val child1 = v.getChildAt(0)
         assertTrue(child1 is FrameLayout)
         assertEquals(1, child1.childCount)

         val child1_1 = child1.getChildAt(0)
         assertTrue(child1_1 is TextView)
         assertEquals("Child", child1_1.text)

         val child2 = v.getChildAt(1)
         assertTrue(child2 is FrameLayout)
         assertEquals(1, child2.childCount)

         val child2_1 = child2.getChildAt(0)
         assertTrue(child2_1 is TextView)
         assertEquals("Child", child2_1.text)
      }
   }

   @Test fun naming_ignoreMismatchedClass() {
      activityScenarioRule.scenario.onActivity { activity ->
         @UseExperimental(ExperimentalContracts::class)
         val v = koshian(activity) {
            LinearLayout {
               View("View1") {
               }
            }
         }

         v.applyKoshian {
            TextView("View1") {
               fail()
            }
         }
      }
   }

   @Test fun naming_viewGroup_ignoreMismatchedClass() {
      activityScenarioRule.scenario.onActivity { activity ->
         @UseExperimental(ExperimentalContracts::class)
         val v = koshian(activity) {
            LinearLayout {
               FrameLayout("View1") {
               }
            }
         }

         v.applyKoshian {
            LinearLayout("View1") {
               fail()
            }
         }
      }
   }

   @Test fun naming_multipleNames() {
      activityScenarioRule.scenario.onActivity { activity ->
         @UseExperimental(ExperimentalContracts::class)
         val v = koshian(activity) {
            LinearLayout {
               TextView("View1") {
               }

               TextView("View2") {
               }

               TextView("View1") {
               }
            }
         }

         v.applyKoshian {
            TextView("View1") {
               view.text = "View1"
            }

            TextView("View2") {
               view.text = "View2"
            }
         }

         val child1 = v.getChildAt(0)
         assertTrue(child1 is TextView)
         assertEquals("View1", child1.text)

         val child2 = v.getChildAt(1)
         assertTrue(child2 is TextView)
         assertEquals("View2", child2.text)

         val child3 = v.getChildAt(2)
         assertTrue(child3 is TextView)
         assertEquals("View1", child3.text)
      }
   }

   @Test fun naming_viewGroup_multipleNames() {
      activityScenarioRule.scenario.onActivity { activity ->
         @UseExperimental(ExperimentalContracts::class)
         val v = koshian(activity) {
            LinearLayout {
               FrameLayout("View1") {
                  TextView {
                  }
               }

               FrameLayout("View2") {
                  TextView {
                  }
               }

               FrameLayout("View1") {
                  TextView {
                  }
               }
            }
         }

         v.applyKoshian {
            FrameLayout("View1") {
               TextView {
                  view.text = "View1"
               }
            }

            FrameLayout("View2") {
               TextView {
                  view.text = "View2"
               }
            }
         }

         val child1 = v.getChildAt(0)
         assertTrue(child1 is FrameLayout)
         assertEquals(1, child1.childCount)

         val child1_1 = child1.getChildAt(0)
         assertTrue(child1_1 is TextView)
         assertEquals("View1", child1_1.text)

         val child2 = v.getChildAt(1)
         assertTrue(child2 is FrameLayout)
         assertEquals(1, child2.childCount)

         val child2_1 = child2.getChildAt(0)
         assertTrue(child2_1 is TextView)
         assertEquals("View2", child2_1.text)

         val child3 = v.getChildAt(2)
         assertTrue(child3 is FrameLayout)
         assertEquals(1, child3.childCount)

         val child3_1 = child3.getChildAt(0)
         assertTrue(child3_1 is TextView)
         assertEquals("View1", child3_1.text)
      }
   }

   @Test fun naming_notAffectCursor() {
      activityScenarioRule.scenario.onActivity { activity ->
         val first:  View
         val second: View
         val third:  View
         val fourth: View
         val fifth:  View

         @UseExperimental(ExperimentalContracts::class)
         val container = koshian(activity) {
            LinearLayout {
               first = View {
               }
               second = View("Second") {
               }
               third = View {
               }
               fourth = View("Fourth") {
               }
               fifth = View {
               }
            }
         }

         lateinit var inserted: TextView

         container.applyKoshian {
            View("Second") {
               assertSame(second, view)
            }
            View {
               assertSame(first, view)
            }
            View {
               assertSame(third, view)
            }
            TextView {
               inserted = view
            }
         }

         assertEquals(6, container.childCount)
         assertSame(first,    container.getChildAt(0))
         assertSame(second,   container.getChildAt(1))
         assertSame(third,    container.getChildAt(2))
         assertSame(inserted, container.getChildAt(3))
         assertSame(fourth,   container.getChildAt(4))
         assertSame(fifth,    container.getChildAt(5))
      }
   }

   @Test fun naming_viewGroup_notAffectCursor() {
      activityScenarioRule.scenario.onActivity { activity ->
         val first:  FrameLayout
         val second: FrameLayout
         val third:  FrameLayout
         val fourth: FrameLayout
         val fifth:  FrameLayout

         @UseExperimental(ExperimentalContracts::class)
         val container = koshian(activity) {
            LinearLayout {
               first = FrameLayout {
               }
               second = FrameLayout("Second") {
               }
               third = FrameLayout {
               }
               fourth = FrameLayout("Fourth") {
               }
               fifth = FrameLayout {
               }
            }
         }

         lateinit var inserted: TextView

         container.applyKoshian {
            FrameLayout("Second") {
               assertSame(second, view)
            }
            FrameLayout {
               assertSame(first, view)
            }
            FrameLayout {
               assertSame(third, view)
            }
            TextView {
               inserted = view
            }
         }

         assertEquals(6, container.childCount)
         assertSame(first,    container.getChildAt(0))
         assertSame(second,   container.getChildAt(1))
         assertSame(third,    container.getChildAt(2))
         assertSame(inserted, container.getChildAt(3))
         assertSame(fourth,   container.getChildAt(4))
         assertSame(fifth,    container.getChildAt(5))
      }
   }

   @Test fun naming_nestedViewGroup() {
      activityScenarioRule.scenario.onActivity { activity ->
         @UseExperimental(ExperimentalContracts::class)
         val v = koshian(activity) {
            LinearLayout {
               FrameLayout("Parent") {
                  TextView("Child") {
                  }
                  TextView("Child") {
                  }
               }

               FrameLayout("Parent") {
                  TextView("Child") {
                  }
                  TextView("Child") {
                  }
               }
            }
         }

         v.applyKoshian {
            FrameLayout("Parent") {
               TextView("Child") {
                  view.text = "Child"
               }
            }
         }

         assertEquals(2, v.childCount)

         for (i in 0..1) {
            val parent = v.getChildAt(i)
            assertTrue(parent is FrameLayout)
            assertEquals(2, parent.childCount)

            for (j in 0..1) {
               val child = parent.getChildAt(j)
               assertTrue(child is TextView)
               assertEquals("Child", child.text)
            }
         }
      }
   }
}

