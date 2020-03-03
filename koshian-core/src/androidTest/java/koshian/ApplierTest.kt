package koshian

import androidx.test.ext.junit.rules.*
import androidx.test.ext.junit.runners.*
import org.junit.*
import org.junit.runner.*
import kotlin.test.*
import kotlin.test.Test

import android.view.*
import android.widget.*

@RunWith(AndroidJUnit4::class)
class ApplierTest {
   @get:Rule
   val activityScenarioRule = activityScenarioRule<EmptyTestActivity>()

   @Test fun apply() {
      activityScenarioRule.scenario.onActivity { activity ->
         val v = koshian(activity) {
            textView {
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
         val v = koshian(activity) {
            frameLayout {
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
         val v = koshian(activity) {
            frameLayout {
               textView {
               }

               textView {
               }
            }
         }

         v.applyKoshian {
            textView {
               view.text = "TextView1"
            }

            textView {
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
         val v = koshian(activity) {
            frameLayout {
               linearLayout {
                  textView {
                  }

                  textView {
                  }
               }

               textView {
               }
            }
         }

         v.applyKoshian {
            linearLayout {
               textView {
                  view.text = "TextView1_1"
               }

               textView {
                  view.text = "TextView1_2"
               }
            }

            textView {
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
         val v = koshian(activity) {
            frameLayout {
               view {
               }
            }
         }

         v.applyKoshian {
            textView {
               view.text = "TextView"
            }

            view {
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
         val v = koshian(activity) {
            frameLayout {
               view {
               }

               view {
               }
            }
         }

         v.applyKoshian {
            view {
               view.elevation = 4.0f
            }

            textView {
               view.text = "TextView"
            }

            view {
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
         val v = koshian(activity) {
            frameLayout {
               view {
               }
            }
         }

         v.applyKoshian {
            view {
               view.elevation = 4.0f
            }

            textView {
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
         val v = koshian(activity) {
            frameLayout {
            }
         }

         v.applyKoshian {
            textView {
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
         val v = koshian(activity) {
            view {
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
         val v = koshian(activity) {
            frameLayout {
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
         val v = koshian(activity) {
            linearLayout {
               view {
               }
            }
         }

         v.applyKoshian {
            view {
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
         val v = koshian(activity) {
            linearLayout {
               frameLayout {
               }
            }
         }

         v.applyKoshian {
            frameLayout {
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
         val v = koshian(activity) {
            linearLayout {
            }
         }

         v.applyKoshian {
            view {
               layout.weight = 4.0f
            }
         }

         val layoutParams = v.getChildAt(0).layoutParams
         assertTrue(layoutParams is LinearLayout.LayoutParams)
         assertEquals(4.0f, layoutParams.weight)
      }
   }
}

