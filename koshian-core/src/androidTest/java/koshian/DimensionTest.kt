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

import com.wcaokaze.koshian.test.R
import kotlin.contracts.*
import kotlin.math.*

@RunWith(AndroidJUnit4::class)
class DimensionTest {
   @get:Rule
   val activityScenarioRule = activityScenarioRule<EmptyTestActivity>()

   @Test fun dp_int() {
      activityScenarioRule.scenario.onActivity { activity ->
         @OptIn(ExperimentalContracts::class)
         val size = koshian(activity) { 4.dp }

         assertClose(
               activity.resources.getDimension(R.dimen.four_dp),
               size.toFloat(),
               0.001f)
      }
   }

   @Test fun dp_float() {
      activityScenarioRule.scenario.onActivity { activity ->
         @OptIn(ExperimentalContracts::class)
         val size = koshian(activity) { 4.0f.dp }

         assertClose(
               activity.resources.getDimension(R.dimen.four_dp),
               size.toFloat(),
               0.001f)
      }
   }

   @Test fun dp_double() {
      activityScenarioRule.scenario.onActivity { activity ->
         @OptIn(ExperimentalContracts::class)
         val size = koshian(activity) { 4.0.dp }

         assertClose(
               activity.resources.getDimension(R.dimen.four_dp),
               size.toFloat(),
               0.001f)
      }
   }

   @Test fun px_int() {
      activityScenarioRule.scenario.onActivity { activity ->
         @OptIn(ExperimentalContracts::class)
         val size = koshian(activity) { 4.px }

         assertClose(
               activity.resources.getDimension(R.dimen.four_px),
               size.toFloat(),
               0.001f)
      }
   }

   @Test fun px_float() {
      activityScenarioRule.scenario.onActivity { activity ->
         @OptIn(ExperimentalContracts::class)
         val size = koshian(activity) { 4.0f.px }

         assertClose(
               activity.resources.getDimension(R.dimen.four_px),
               size.toFloat(),
               0.001f)
      }
   }

   @Test fun px_double() {
      activityScenarioRule.scenario.onActivity { activity ->
         @OptIn(ExperimentalContracts::class)
         val size = koshian(activity) { 4.0.px }

         assertClose(
               activity.resources.getDimension(R.dimen.four_px),
               size.toFloat(),
               0.001f)
      }
   }

   @Test fun sp_int() {
      activityScenarioRule.scenario.onActivity { activity ->
         @OptIn(ExperimentalContracts::class)
         val size = koshian(activity) { 4.sp }

         assertClose(
               activity.resources.getDimension(R.dimen.four_sp),
               size,
               0.001f)
      }
   }

   @Test fun sp_float() {
      activityScenarioRule.scenario.onActivity { activity ->
         @OptIn(ExperimentalContracts::class)
         val size = koshian(activity) { 4.0f.sp }

         assertClose(
               activity.resources.getDimension(R.dimen.four_sp),
               size,
               0.001f)
      }
   }

   @Test fun sp_double() {
      activityScenarioRule.scenario.onActivity { activity ->
         @OptIn(ExperimentalContracts::class)
         val size = koshian(activity) { 4.0.sp }

         assertClose(
               activity.resources.getDimension(R.dimen.four_sp),
               size,
               0.001f)
      }
   }

   private fun assertClose(expected: Float, actual: Float, offset: Float) {
      assertTrue(abs(expected - actual) < offset)
   }
}
