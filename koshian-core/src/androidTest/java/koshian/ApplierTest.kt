package koshian

import androidx.test.ext.junit.rules.*
import androidx.test.ext.junit.runners.*
import org.junit.*
import org.junit.runner.*
import kotlin.test.*
import kotlin.test.Test

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

         applyKoshian(v) {
            v.text = "Koshian"
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

         applyKoshian(v) {
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

         applyKoshian(v) {
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

         applyKoshian(v) {
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
}

