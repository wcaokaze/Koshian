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
class CreatorTest {
   @get:Rule
   val activityScenarioRule = activityScenarioRule<EmptyTestActivity>()

   @Test fun createView() {
      activityScenarioRule.scenario.onActivity { activity ->
         @UseExperimental(ExperimentalContracts::class)
         val v = koshian(activity) {
            TextView {
               view.text = "Koshian"
            }
         }

         assertEquals("Koshian", v.text)
      }
   }

   @Test fun buildViewGroup() {
      activityScenarioRule.scenario.onActivity { activity ->
         @UseExperimental(ExperimentalContracts::class)
         val v = koshian(activity) {
            FrameLayout {
               TextView {
                  view.text = "TextView1"
               }

               FrameLayout {
                  TextView {
                     view.text = "TextView2"
                  }
               }
            }
         }

         assertEquals(2, v.childCount)

         val child1 = v.getChildAt(0)
         assertTrue(child1 is TextView)
         assertEquals("TextView1", child1.text)

         val child2 = v.getChildAt(1)
         assertTrue(child2 is FrameLayout)
         assertEquals(1, child2.childCount)

         val child3 = child2.getChildAt(0)
         assertTrue(child3 is TextView)
         assertEquals("TextView2", child3.text)
      }
   }

   @Test fun buildLayoutParams() {
      activityScenarioRule.scenario.onActivity { activity ->
         @UseExperimental(ExperimentalContracts::class)
         val v = koshian(activity) {
            LinearLayout {
               layout.width  = MATCH_PARENT
               layout.height = MATCH_PARENT

               TextView {
                  layout.width  = MATCH_PARENT
                  layout.height = MATCH_PARENT
                  layout.weight = 1.0f
                  view.text = "TextView1"
               }
            }
         }

         val parentViewLayoutParams = v.layoutParams
         assertTrue(parentViewLayoutParams is ViewGroup.LayoutParams)
         assertEquals(MATCH_PARENT, parentViewLayoutParams.width)
         assertEquals(MATCH_PARENT, parentViewLayoutParams.height)
      }
   }

   @Test fun addView() {
      activityScenarioRule.scenario.onActivity { activity ->
         @UseExperimental(ExperimentalContracts::class)
         val v = koshian(activity) {
            LinearLayout {
               TextView {
                  view.text = "TextView1"
               }
            }
         }

         @UseExperimental(ExperimentalContracts::class)
         v.addView {
            TextView {
               layout.weight = 4.0f
               view.text = "TextView2"
            }
         }

         assertEquals(2, v.childCount)

         val child1 = v.getChildAt(0)
         assertTrue(child1 is TextView)
         assertEquals("TextView1", child1.text)

         val child2 = v.getChildAt(1)
         assertTrue(child2 is TextView)
         assertEquals("TextView2", child2.text)

         val child2LayoutParams = child2.layoutParams
         assertTrue(child2LayoutParams is LinearLayout.LayoutParams)
         assertEquals(4.0f, child2LayoutParams.weight)
      }
   }

   @Test fun contracts() {
      activityScenarioRule.scenario.onActivity { activity ->
         val koshianTextView: TextView

         @UseExperimental(ExperimentalContracts::class)
         val v = koshian(activity) {
            FrameLayout {
               koshianTextView = TextView {
                  view.text = "Koshian"
               }
            }
         }

         assertEquals("Koshian", koshianTextView.text)
         assertSame(koshianTextView, v.getChildAt(0))
      }
   }

   @Test fun contracts_addView() {
      activityScenarioRule.scenario.onActivity { activity ->
         val koshianTextView: TextView

         @UseExperimental(ExperimentalContracts::class)
         val v = koshian(activity) {
            FrameLayout {
            }
         }

         @UseExperimental(ExperimentalContracts::class)
         val child = v.addView {
            FrameLayout {
               koshianTextView = TextView {
                  view.text = "Koshian"
               }
            }
         }

         assertEquals("Koshian", koshianTextView.text)
         assertSame(child, v.getChildAt(0))
         assertSame(koshianTextView, child.getChildAt(0))
      }
   }
}
