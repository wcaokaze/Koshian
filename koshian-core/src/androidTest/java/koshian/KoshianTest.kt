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
class KoshianTest {
   @get:Rule
   val activityScenarioRule = activityScenarioRule<EmptyTestActivity>()

   @Test fun createView() {
      activityScenarioRule.scenario.onActivity { activity ->
         val view = koshian(activity) {
            textView {
               view.text = "Koshian"
            }
         }

         assertEquals("Koshian", view.text)
      }
   }

   @Test fun buildViewGroup() {
      activityScenarioRule.scenario.onActivity { activity ->
         val view = koshian(activity) {
            frameLayout {
               textView {
                  view.text = "TextView1"
               }

               textView {
                  view.text = "TextView2"
               }
            }
         }

         assertEquals(2, view.childCount)

         val child1 = view.getChildAt(0)
         assertTrue(child1 is TextView)
         assertEquals("TextView1", child1.text)

         val child2 = view.getChildAt(1)
         assertTrue(child2 is TextView)
         assertEquals("TextView2", child2.text)
      }
   }

   @Test fun buildLayoutParams() {
      activityScenarioRule.scenario.onActivity { activity ->
         val view = koshian(activity) {
            linearLayout {
               layout.width  = MATCH_PARENT
               layout.height = MATCH_PARENT

               textView {
                  layout.width  = MATCH_PARENT
                  layout.height = MATCH_PARENT
                  view.text = "TextView1"
               }
            }
         }

         val parentViewLayoutParams = view.layoutParams
         assertTrue(parentViewLayoutParams is ViewGroup.LayoutParams)
         assertEquals(MATCH_PARENT, parentViewLayoutParams.width)
         assertEquals(MATCH_PARENT, parentViewLayoutParams.height)
      }
   }
}
