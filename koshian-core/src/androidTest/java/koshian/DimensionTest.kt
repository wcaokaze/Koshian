package koshian

import androidx.test.ext.junit.rules.*
import androidx.test.ext.junit.runners.*
import org.junit.*
import org.junit.runner.*
import kotlin.test.Test

import com.wcaokaze.koshian.test.R
import kotlin.contracts.*
import kotlin.math.*

@RunWith(AndroidJUnit4::class)
class DimensionTest {
   @get:Rule
   val activityScenarioRule = activityScenarioRule<EmptyTestActivity>()

   @Test fun dip_int() {
      activityScenarioRule.scenario.onActivity { activity ->
         @UseExperimental(ExperimentalContracts::class)
         val size = koshian(activity) { 4.dip }

         assertClose(
               activity.resources.getDimension(R.dimen.four_dip),
               size.toFloat(),
               0.001f)
      }
   }

   @Test fun dip_float() {
      activityScenarioRule.scenario.onActivity { activity ->
         @UseExperimental(ExperimentalContracts::class)
         val size = koshian(activity) { 4.0f.dip }

         assertClose(
               activity.resources.getDimension(R.dimen.four_dip),
               size.toFloat(),
               0.001f)
      }
   }

   @Test fun dip_double() {
      activityScenarioRule.scenario.onActivity { activity ->
         @UseExperimental(ExperimentalContracts::class)
         val size = koshian(activity) { 4.0.dip }

         assertClose(
               activity.resources.getDimension(R.dimen.four_dip),
               size.toFloat(),
               0.001f)
      }
   }

   private fun assertClose(expected: Float, actual: Float, offset: Float) {
      assert(abs(expected - actual) < offset)
   }
}
