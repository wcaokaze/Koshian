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
import kotlin.reflect.full.IllegalCallableAccessException

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
      val viewGetterName: String

      run {
         val styledAttrs = context.obtainStyledAttributes(attrs, R.styleable.Preview, defStyleAttr, 0)

         containerClassName = styledAttrs.getString(R.styleable.Preview_container)
            ?: throw Exception("container is not specified")

         viewGetterName = styledAttrs.getString(R.styleable.Preview_property)
            ?: throw Exception("property is not specified")

         styledAttrs.recycle()
      }

      val clazz = Class.forName(containerClassName).kotlin
      val container = instantiateContainer(clazz, context)
      val view = getView(clazz, container, context, viewGetterName)

      addView(view)
   }

   private fun instantiateContainer(containerClass: KClass<*>, context: Context): Any {
      var lastFailCause: Exception? = null

      callConstructor@for (constructor in containerClass.constructors) {
         val args = HashMap<KParameter, Any?>()

         buildArgs@for (param in constructor.parameters) {
            when {
               param.isOptional -> continue@buildArgs

               param.type.classifier == Context::class -> {
                  args[param] = context
               }

               param.type.isMarkedNullable -> {
                  args[param] = null
               }

               else -> {
                  val arg = param.type.instantiateViaDefaultConstructor()
                     ?: continue@callConstructor

                  args[param] = arg
               }
            }
         }

         try {
            return constructor.callBy(args)
         } catch (e: Exception) {
            lastFailCause = e
            continue@callConstructor
         }
      }

      when {
         lastFailCause is IllegalCallableAccessException -> throw Exception(
            "An IllegalCallableAccessException was thrown. Maybe the constructor is private?",
            lastFailCause)

         lastFailCause != null -> throw Exception(lastFailCause)

         else -> throw Exception(
            "cannot instantiate $containerClass. " +
            "It must have either of constructor(Context), " +
            "or all parameters expect Context must have a default argument.")
      }
   }

   private fun getView(containerClass: KClass<*>,
                       container: Any?,
                       context: Context,
                       viewGetterName: String): View
   {
      val getters = containerClass.members.filter { it.name == viewGetterName }

      if (getters.isEmpty()) {
         throw Exception("No member named `$viewGetterName` was found in $containerClass")
      }

      var lastFailCause: Exception? = null

      callGetter@for (getter in getters) {
         val args = HashMap<KParameter, Any?>()

         buildArgs@for (param in getter.parameters) {
            when {
               param.isOptional -> continue@buildArgs

               param.type.classifier == Context::class -> {
                  args[param] = context
               }

               param.type.classifier == containerClass -> {
                  args[param] = container
               }

               else -> continue@callGetter
            }
         }

         val callResult = try {
            getter.callBy(args)
         } catch (e: Exception) {
            lastFailCause = e
            continue@callGetter
         }

         if (callResult !is View) { continue@callGetter }

         return callResult
      }

      when {
         lastFailCause is IllegalCallableAccessException -> throw Exception(
            "An IllegalCallableAccessException was thrown. Maybe $viewGetterName is private?")

         lastFailCause != null -> throw Exception(lastFailCause)

         else -> throw Exception(
            "cannot get a View from $viewGetterName. " +
            "It must be a property or a function(Context), or all parameters " +
            "expect Context must have a default argument.")
      }
   }

   private fun KType.instantiateViaDefaultConstructor(): Any? {
      val classifier = classifier

      if (classifier !is KClass<*>) { return null }

      val defaultConstructor = classifier
         .constructors.firstOrNull { it.parameters.isEmpty() }

      if (defaultConstructor != null) { return defaultConstructor.call() }

      val optionalParameterConstructor = classifier
         .constructors.firstOrNull { c -> c.parameters.all { it.isOptional } }

      if (optionalParameterConstructor != null) {
         return optionalParameterConstructor.callBy(emptyMap())
      }

      return null
   }
}
