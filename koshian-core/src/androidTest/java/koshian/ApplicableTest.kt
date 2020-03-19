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

import android.content.*
import android.view.*
import kotlin.contracts.*

@RunWith(AndroidJUnit4::class)
class ApplicableTest {
   @get:Rule
   val activityScenarioRule = activityScenarioRule<EmptyTestActivity>()

   class ApplicableImpl(context: Context) : KoshianApplicable {
      override val view = View(context)
   }

   inline val <L> Koshian<ApplicableImpl, L, *, *>.layout: L get() {
      @Suppress("UNCHECKED_CAST")
      return (`$$koshianInternal$view` as ApplicableImpl).view.layoutParams as L
   }

   @Test fun addView() {
      activityScenarioRule.scenario.onActivity { activity ->
         val applicable = ApplicableImpl(activity)

         @OptIn(ExperimentalContracts::class)
         val v = koshian(activity) {
            LinearLayout {
               View {
               }
               applicable {
               }
               View {
               }
            }
         }

         assertEquals(3, v.childCount)
         assertEquals(View::class, v.getChildAt(0)::class)
         assertSame(applicable.view, v.getChildAt(1))
         assertEquals(View::class, v.getChildAt(2)::class)
      }
   }

   @Test fun addView_inNotViewGroupBuilder() {
      activityScenarioRule.scenario.onActivity { activity ->
         val applicable = ApplicableImpl(activity)

         val exception = assertFailsWith<IllegalStateException> {
            @OptIn(ExperimentalContracts::class)
            koshian(activity) {
               View {
                  applicable {
                  }
               }
            }
         }

         val message = exception.message
         assertNotNull(message)
         assertTrue(applicable.toString() in message)
         assertTrue("current Koshian-View is not a ViewGroup." in message)
      }
   }

   @Test fun addView_alreadyAddedView() {
      activityScenarioRule.scenario.onActivity { activity ->
         val applicable = ApplicableImpl(activity)

         @OptIn(ExperimentalContracts::class)
         koshian(activity) {
            LinearLayout {
               applicable {
               }
            }
         }

         val exception = assertFailsWith<IllegalStateException> {
            @OptIn(ExperimentalContracts::class)
            koshian(activity) {
               LinearLayout {
                  applicable {
                  }
               }
            }
         }

         val message = exception.message
         assertNotNull(message)
         assertTrue(applicable.toString() in message)
         assertTrue("already added to another ViewGroup." in message)
      }
   }

   @Test fun apply() {
      activityScenarioRule.scenario.onActivity { activity ->
         val applicable = ApplicableImpl(activity)

         @OptIn(ExperimentalContracts::class)
         val v = koshian(activity) {
            LinearLayout {
               View {
               }
               applicable {
               }
               View {
               }
            }
         }

         v.applyKoshian {
            View {
            }
            applicable {
               layout.width = 4
            }
            View {
            }
         }

         assertEquals(4, applicable.view.layoutParams.width)
      }
   }

   @Test fun apply_inNotViewGroupBuilder() {
      activityScenarioRule.scenario.onActivity { activity ->
         val applicable = ApplicableImpl(activity)

         @OptIn(ExperimentalContracts::class)
         val v = koshian(activity) {
            LinearLayout {
               View {
               }
               applicable {
               }
            }
         }

         val exception = assertFailsWith<IllegalStateException> {
            v.applyKoshian {
               View {
                  applicable {
                  }
               }
            }
         }

         val message = exception.message
         assertNotNull(message)
         assertTrue(applicable.toString() in message)
         assertTrue("current Koshian-View is not a ViewGroup." in message)
      }
   }

   @Test fun apply_notMatch() {
      activityScenarioRule.scenario.onActivity { activity ->
         val applicable = ApplicableImpl(activity)
         val unmatchedView: View

         @OptIn(ExperimentalContracts::class)
         val v = koshian(activity) {
            LinearLayout {
               unmatchedView = View {
               }

               applicable {
               }
            }
         }

         val exception = assertFailsWith<AssertionError> {
            v.applyKoshian {
               applicable {
               }
            }
         }

         val message = exception.message
         assertNotNull(message)
         assertTrue(applicable.toString() in message)
         assertTrue("not match" in message)
         assertTrue(unmatchedView.toString() in message)
      }
   }

   @Test fun insertion_first() {
      activityScenarioRule.scenario.onActivity { activity ->
         @OptIn(ExperimentalContracts::class)
         val v = koshian(activity) {
            LinearLayout {
               View {
               }
            }
         }

         val applicable = ApplicableImpl(activity)

         v.applyKoshian {
            applicable {
            }
         }

         assertEquals(2, v.childCount)
         assertSame(applicable.view, v.getChildAt(0))
         assertEquals(View::class, v.getChildAt(1)::class)
      }
   }

   @Test fun insertion_middle() {
      activityScenarioRule.scenario.onActivity { activity ->
         @OptIn(ExperimentalContracts::class)
         val v = koshian(activity) {
            LinearLayout {
               View {
               }

               View {
               }
            }
         }

         val applicable = ApplicableImpl(activity)

         v.applyKoshian {
            View {
            }
            applicable {
            }
            View {
            }
         }

         assertEquals(3, v.childCount)
         assertEquals(View::class, v.getChildAt(0)::class)
         assertSame(applicable.view, v.getChildAt(1))
         assertEquals(View::class, v.getChildAt(2)::class)
      }
   }

   @Test fun insertion_last() {
      activityScenarioRule.scenario.onActivity { activity ->
         @OptIn(ExperimentalContracts::class)
         val v = koshian(activity) {
            LinearLayout {
               View {
               }
            }
         }

         val applicable = ApplicableImpl(activity)

         v.applyKoshian {
            View {
            }
            applicable {
            }
         }

         assertEquals(2, v.childCount)
         assertEquals(View::class, v.getChildAt(0)::class)
         assertSame(applicable.view, v.getChildAt(1))
      }
   }
}
