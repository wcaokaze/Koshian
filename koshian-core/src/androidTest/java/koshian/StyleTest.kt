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

   @Test fun nestedViews_withPartialApplying() {
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
                  TextView {
                  }
               }

               TextView {
               }
            }
         }

         v.applyKoshian(Style()) {
            FrameLayout {
               TextView {
                  view.text = "TextView1"
               }
            }
         }

         assertEquals(4.0f, v.elevation)
         assertEquals(2, v.childCount)

         val child1 = v.getChildAt(0)
         assertTrue(child1 is FrameLayout)
         assertEquals(4.0f, child1.elevation)
         assertEquals(2, child1.childCount)

         val child1_1 = child1.getChildAt(0)
         assertTrue(child1_1 is TextView)
         assertEquals("Koshian", child1_1.hint)
         assertEquals("TextView1", child1_1.text)

         val child1_2 = child1.getChildAt(1)
         assertTrue(child1_2 is TextView)
         assertEquals("Koshian", child1_2.hint)

         val child2 = v.getChildAt(1)
         assertTrue(child2 is TextView)
         assertEquals("Koshian", child2.hint)
      }
   }

   @Test fun insertedViews() {
      class Style : KoshianStyle() {
         override fun defaultStyle() {
            TextView {
               view.text = "Koshian"
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
            TextView {
            }
         }

         val child = v.getChildAt(0)
         assertTrue(child is TextView)
         assertEquals("Koshian", child.text)
      }
   }

   @Test fun insertedViewGroups() {
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
            FrameLayout {
            }
         }

         val child = v.getChildAt(0)
         assertTrue(child is FrameLayout)
         assertEquals(4.0f, child.elevation)
      }
   }

   @Test fun insertedViews_inInsertedViewGroups() {
      class Style : KoshianStyle() {
         override fun defaultStyle() {
            TextView {
               view.text = "Koshian"
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
            FrameLayout {
               TextView {
               }
            }
         }

         val child = v.getChildAt(0)
         assertTrue(child is FrameLayout)
         assertEquals(1, child.childCount)

         val child2 = child.getChildAt(0)
         assertTrue(child2 is TextView)
         assertEquals("Koshian", child2.text)
      }
   }

   @Test fun insertedBySpecifyingViews() {
      class Style : KoshianStyle() {
         override fun defaultStyle() {
            TextView {
               view.text = "Koshian"
            }
         }
      }

      activityScenarioRule.scenario.onActivity { activity ->
         val textView = TextView(activity)

         @OptIn(ExperimentalContracts::class)
         val v = koshian(activity) {
            FrameLayout {
            }
         }

         v.applyKoshian(Style()) {
            textView {
            }
         }

         val child = v.getChildAt(0)
         assertSame(textView, child)
         assertEquals("Koshian", textView.text)
      }
   }

   @Test fun namedStyle() {
      class Style : KoshianStyle() {
         override fun defaultStyle() {
            TextView {
               view.hint = "Koshian Hint"
            }
         }

         val style1 = TextView {
            view.text = "Koshian Text"
         }
      }

      activityScenarioRule.scenario.onActivity { activity ->
         @OptIn(ExperimentalContracts::class)
         val v = koshian(activity) {
            FrameLayout {
               TextView {
               }
               TextView {
               }
            }
         }

         v.applyKoshian(Style()) {
            TextView {
            }
            TextView(style.style1) {
            }
         }

         val child1 = v.getChildAt(0)
         val child2 = v.getChildAt(1)

         assertTrue(child1 is TextView)
         assertEquals("Koshian Hint", child1.hint)

         assertTrue(child2 is TextView)
         assertEquals("Koshian Hint", child2.hint)
         assertEquals("Koshian Text", child2.text)
      }
   }

   @Test fun namedStyle_insertedView() {
      class Style : KoshianStyle() {
         override fun defaultStyle() {
            TextView {
               view.hint = "Koshian Hint"
            }
         }

         val style1 = TextView {
            view.text = "Koshian Text"
         }
      }

      activityScenarioRule.scenario.onActivity { activity ->
         @OptIn(ExperimentalContracts::class)
         val v = koshian(activity) {
            FrameLayout {
               TextView {
               }
            }
         }

         v.applyKoshian(Style()) {
            TextView {
            }
            TextView(style.style1) {
            }
         }

         val child1 = v.getChildAt(0)
         val child2 = v.getChildAt(1)

         assertTrue(child1 is TextView)
         assertEquals("Koshian Hint", child1.hint)

         assertTrue(child2 is TextView)
         assertEquals("Koshian Hint", child2.hint)
         assertEquals("Koshian Text", child2.text)
      }
   }

   @Test fun namedStyle_viewGroup() {
      class Style : KoshianStyle() {
         override fun defaultStyle() {
            FrameLayout {
               view.elevation = 4.0f
            }
         }

         val style1 = FrameLayout {
            view.alpha = 0.4f
         }
      }

      activityScenarioRule.scenario.onActivity { activity ->
         @OptIn(ExperimentalContracts::class)
         val v = koshian(activity) {
            FrameLayout {
               FrameLayout {
               }
               FrameLayout {
               }
            }
         }

         v.applyKoshian(Style()) {
            FrameLayout {
            }
            FrameLayout(style.style1) {
            }
         }

         val child1 = v.getChildAt(0)
         val child2 = v.getChildAt(1)

         assertTrue(child1 is FrameLayout)
         assertEquals(4.0f, child1.elevation)

         assertTrue(child2 is FrameLayout)
         assertEquals(4.0f, child2.elevation)
         assertEquals(0.4f, child2.alpha)
      }
   }

   @Test fun namedStyle_insertedViewGroup() {
      class Style : KoshianStyle() {
         override fun defaultStyle() {
            FrameLayout {
               view.elevation = 4.0f
            }
         }

         val style1 = FrameLayout {
            view.alpha = 0.4f
         }
      }

      activityScenarioRule.scenario.onActivity { activity ->
         @OptIn(ExperimentalContracts::class)
         val v = koshian(activity) {
            FrameLayout {
               FrameLayout {
               }
            }
         }

         v.applyKoshian(Style()) {
            FrameLayout {
            }
            FrameLayout(style.style1) {
            }
         }

         val child1 = v.getChildAt(0)
         val child2 = v.getChildAt(1)

         assertTrue(child1 is FrameLayout)
         assertEquals(4.0f, child1.elevation)

         assertTrue(child2 is FrameLayout)
         assertEquals(4.0f, child2.elevation)
         assertEquals(0.4f, child2.alpha)
      }
   }

   @Test fun viewInViewGroupAppliedNamedStyle() {
      class Style : KoshianStyle() {
         override fun defaultStyle() {
            TextView {
               view.hint = "Koshian hint"
            }

            FrameLayout {
               view.elevation = 4.0f
            }
         }

         val style1 = FrameLayout {
            view.alpha = 0.4f
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
            }
         }

         v.applyKoshian(Style()) {
            FrameLayout(style.style1) {
               TextView {
                  view.text = "Koshian text"
               }
            }
         }

         val child1 = v.getChildAt(0)
         assertTrue(child1 is FrameLayout)
         assertEquals(4.0f, child1.elevation)
         assertEquals(0.4f, child1.alpha)

         val child2 = child1.getChildAt(0)
         assertTrue(child2 is TextView)
         assertEquals("Koshian hint", child2.hint)
         assertEquals("Koshian text", child2.text)
      }
   }

   @Test fun namedStyle_inNamedStyle() {
      class Style : KoshianStyle() {
         override fun defaultStyle() {
            TextView {
               view.hint = "Koshian hint"
            }

            FrameLayout {
               view.elevation = 4.0f
            }
         }

         val style1 = TextView {
            view.text = "Koshian text"
         }

         val style2 = FrameLayout {
            view.alpha = 0.4f
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
            }
         }

         v.applyKoshian(Style()) {
            FrameLayout(style.style2) {
               TextView(style.style1) {
               }
            }
         }

         val child1 = v.getChildAt(0)
         assertTrue(child1 is FrameLayout)
         assertEquals(4.0f, child1.elevation)
         assertEquals(0.4f, child1.alpha)

         val child2 = child1.getChildAt(0)
         assertTrue(child2 is TextView)
         assertEquals("Koshian hint", child2.hint)
         assertEquals("Koshian text", child2.text)
      }
   }

   @Test fun namedStyle_forNamedView() {
      class Style : KoshianStyle() {
         override fun defaultStyle() {
            TextView {
               view.hint = "Koshian Hint"
            }
         }

         val style1 = TextView {
            view.text = "Koshian Text"
         }
      }

      activityScenarioRule.scenario.onActivity { activity ->
         @OptIn(ExperimentalContracts::class)
         val v = koshian(activity) {
            FrameLayout {
               TextView("View1") {
               }
               TextView("View1") {
               }
            }
         }

         v.applyKoshian(Style()) {
            TextView("View1", style.style1) {
            }
         }

         val child1 = v.getChildAt(0)
         val child2 = v.getChildAt(1)

         assertTrue(child1 is TextView)
         assertEquals("Koshian Hint", child1.hint)
         assertEquals("Koshian Text", child1.text)

         assertTrue(child2 is TextView)
         assertEquals("Koshian Hint", child2.hint)
         assertEquals("Koshian Text", child2.text)
      }
   }

   @Test fun namedStyle_forNamedViewGroup() {
      class Style : KoshianStyle() {
         override fun defaultStyle() {
            FrameLayout {
               view.elevation = 4.0f
            }
         }

         val style1 = FrameLayout {
            view.alpha = 0.4f
         }
      }

      activityScenarioRule.scenario.onActivity { activity ->
         @OptIn(ExperimentalContracts::class)
         val v = koshian(activity) {
            FrameLayout {
               FrameLayout("View1") {
               }
               FrameLayout("View1") {
               }
            }
         }

         v.applyKoshian(Style()) {
            FrameLayout("View1", style.style1) {
            }
         }

         val child1 = v.getChildAt(0)
         val child2 = v.getChildAt(1)

         assertTrue(child1 is FrameLayout)
         assertEquals(4.0f, child1.elevation)
         assertEquals(0.4f, child1.alpha)

         assertTrue(child2 is FrameLayout)
         assertEquals(4.0f, child2.elevation)
         assertEquals(0.4f, child2.alpha)
      }
   }
}
