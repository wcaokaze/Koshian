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
            textView {
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
            frameLayout {
               textView {
                  view.text = "TextView1"
               }

               frameLayout {
                  textView {
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
            linearLayout {
               layout.width  = MATCH_PARENT
               layout.height = MATCH_PARENT

               textView {
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
            linearLayout {
               textView {
                  view.text = "TextView1"
               }
            }
         }

         @UseExperimental(ExperimentalContracts::class)
         v.addView {
            textView {
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
         val textView: TextView

         @UseExperimental(ExperimentalContracts::class)
         val v = koshian(activity) {
            frameLayout {
               textView = textView {
                  view.text = "Koshian"
               }
            }
         }

         assertEquals("Koshian", textView.text)
         assertSame(textView, v.getChildAt(0))
      }
   }

   @Test fun contracts_addView() {
      activityScenarioRule.scenario.onActivity { activity ->
         val textView: TextView

         @UseExperimental(ExperimentalContracts::class)
         val v = koshian(activity) {
            frameLayout {
            }
         }

         @UseExperimental(ExperimentalContracts::class)
         val child = v.addView {
            frameLayout {
               textView = textView {
                  view.text = "Koshian"
               }
            }
         }

         assertEquals("Koshian", textView.text)
         assertSame(child, v.getChildAt(0))
         assertSame(textView, child.getChildAt(0))
      }
   }
}
