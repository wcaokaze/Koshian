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

import android.content.*
import android.util.*
import android.view.*
import android.widget.*
import com.wcaokaze.koshian.*
import kotlin.reflect.*

class Preview
      @JvmOverloads constructor(
            context: Context,
            attrs: AttributeSet? = null,
            defStyleAttr: Int = 0
      )
      : FrameLayout(context, attrs, defStyleAttr)
{
   init {
      val containerClassName: String
      val viewPropertyName: String

      run {
         val styledAttrs = context.obtainStyledAttributes(attrs, R.styleable.Preview, defStyleAttr, 0)

         containerClassName = styledAttrs.getString(R.styleable.Preview_container_class)
            ?: throw Exception("container_class is not specified")

         viewPropertyName = styledAttrs.getString(R.styleable.Preview_view_property)
            ?: throw Exception("view_property is not specified")

         styledAttrs.recycle()
      }

      val clazz = Class.forName(containerClassName).kotlin
      val container = instantiateContainer(clazz, context)
      val view = getView(clazz, container, viewPropertyName)

      addView(view)
   }

   // We can ignore the warning about kotlin-reflect.jar not found.
   // Since this function is called on Android Studio, not on Android runtime.
   private fun instantiateContainer(containerClass: KClass<*>, context: Context): Any {
      callConstructor@for (constructor in containerClass.constructors) {
         val args = HashMap<KParameter, Any?>()

         buildArgs@for (param in constructor.parameters) {
            when {
               param.isOptional -> continue@buildArgs

               param.type.classifier == Context::class -> {
                  args[param] = context
               }

               else -> continue@callConstructor
            }
         }

         try {
            return constructor.callBy(args)
         } catch (e: Exception) {
            continue@callConstructor
         }
      }

      throw Exception("cannot instantiate $containerClass. " +
            "It must have either of constructor(Context), " +
            "or all parameters expect Context must have a default argument.")
   }

   private fun getView(containerClass: KClass<*>,
                       container: Any?,
                       viewPropertyName: String): View
   {
      val callable = containerClass.members.firstOrNull { it.name == viewPropertyName }
         ?: throw Exception("No property named `$viewPropertyName` was found")

      if (callable.parameters.isEmpty() || callable.parameters.all { it.isOptional }) {
         return callable.callBy(emptyMap()) as? View
            ?: throw Exception("$viewPropertyName did not return a View")
      }

      if (callable.parameters.first().type.classifier == containerClass
            && callable.parameters.drop(1).all { it.isOptional })
      {
         val parameter = callable.parameters.first()

         val view = callable.callBy(
            mapOf(
               parameter to container
            )
         )

         if (view !is View) { throw Exception("$viewPropertyName did not return a View") }

         return view
      }

      throw Exception("cannot get a View from $viewPropertyName. " +
            "It must be a property or a function without any parameters.")
   }
}
