package koshian

import androidx.test.ext.junit.rules.*
import androidx.test.ext.junit.runners.*
import org.junit.*
import org.junit.runner.*
import kotlin.test.Test

import android.view.*

@RunWith(AndroidJUnit4::class)
class KoshianTest {
   @get:Rule
   val activityScenarioRule = activityScenarioRule<EmptyTestActivity>()

   @Test fun createView() {
      activityScenarioRule.scenario.onActivity { activity ->
         val view = koshian(activity) {
            view {
            }
         }

         assert(view is View)
      }
   }
}
