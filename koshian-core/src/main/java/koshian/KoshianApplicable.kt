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

import android.view.*

interface KoshianApplicable<out R> {
   enum class ApplicableMode {
      /**
       * Applicable was added.
       *
       * e.g.
       * ```kotlin
       * val applicable = Applicable()
       *
       * koshian(context) {
       *    FrameLayout {
       *       applicable {
       *       }
       *    }
       * }
       * ```
       */
      CREATION,

      /**
       * Applicable was matched to some View.
       *
       * e.g.
       * ```kotlin
       * val applicable = Applicable()
       *
       * view = koshian(context) {
       *    FrameLayout {
       *       applicable {
       *       }
       *    }
       * }
       *
       * view.applyKoshian {
       *    FrameLayout {
       *       applicable {
       *       }
       *    }
       * }
       * ```
       */
      ASSERTION,

      /**
       * Applicable was inserted.
       *
       * e.g.
       * ```kotlin
       * val applicable = Applicable()
       *
       * view.applyKoshian {
       *    FrameLayout {
       *       applicable {
       *       }
       *    }
       * }
       * ```
       */
      INSERTION,
   }

   val view: View

   fun beforeApply(mode: ApplicableMode) {}
   fun afterApply(mode: ApplicableMode) {}

   fun getResult(mode: ApplicableMode): R
}
