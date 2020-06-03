
Koshian
================================================================================

DSL to layout Android Views.

```kotlin
private val userIconView: ImageView
private val usernameView: TextView

private val followButton: Button

init {
   val layout = koshian(context) {
      LinearLayout {
         LinearLayout {
            userIconView = ImageView {
               view.image = drawable(R.drawable.ic_empty_user)
            }

            usernameView = TextView {
               view.text = "@"
            }
         }

         followButton = Button {
            view.text = "follow"
         }
      }
   }

   layout.applyKoshian {
      view.orientation = VERTICAL

      LinearLayout {
         layout.width  = MATCH_PARENT
         layout.height = WRAP_CONTENT
         view.orientation = HORIZONTAL

         userIconView {
            layout.width  = 32.dip
            layout.height = 32.dip
            layout.margins = 16.dip
         }

         usernameView {
            layout.width  = MATCH_PARENT
            layout.height = WRAP_CONTENT
            view.textColor = 0x212121.opaque
            view.textSizeSp = 12
            layout.horizontalMargin = 16.dip
            layout.verticalMargin   = 32.dip
         }
      }

      Space {
         layout.width  = 0
         layout.height = 16.dip
      }

      followButton {
         layout.width  = WRAP_CONTENT
         layout.height = WRAP_CONTENT
         layout.gravity = END
         layout.margins = 16.dip
         view.paddings = 8.dip
      }
   }
}
```


Preview
--------------------------------------------------------------------------------

```kotlin
class Container(context: Context) {
   val view = koshian(context) {
      LinearLayout {
         view.orientation = VERTICAL
      }
   }
}
```
```xml
<koshian.Preview
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:container="com.wcaokaze.example.Container"
    app:property="view" />
```

- `app:container`  
    The name of a class which has a View.
- `app:property`  
    The name of a property which has a View. Or, a function which returns a View.

Usage of preview for function
```kotlin
class Container {
   fun build(context: Context) = koshian(context) {
      LinearLayout {
         view.orientation = VERTICAL
      }
   }
}
```
```xml
<koshian.Preview
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:container="com.wcaokaze.example.Container"
    app:property="build" />
```
We can preview even an Activity. Check it in the example app.
[MainActivity.kt](https://github.com/wcaokaze/Koshian/blob/master/example/app/src/main/java/com/wcaokaze/koshian/example/MainActivity.kt#L48)
[preview_test_container.xml](https://github.com/wcaokaze/Koshian/blob/master/example/app/src/main/res/layout/preview_test_container.xml)

But please note that we cannot use Activity (or Fragment) in preview.
```kotlin
fun buildLayout(context: Context): View {
   //           ^~~~~~~~~~~~~~~~
   //           Receive a Context since we cannot use the Activity

   // ...

   return contentView
   // return the View since we cannot call `setContentView(View)`
}
```


applyKoshian
--------------------------------------------------------------------------------

finds Views that are already added in a ViewGroup, and applies Koshian DSL to them.

![](https://raw.github.com/wcaokaze/Koshian/master/imgs/applier.svg?sanitize=true)

The following 2 snippets are equivalent.

1.
    ```kotlin
    val contentView = koshian(context) {
       LinearLayout {
          TextView {
             view.text = "hello"
             view.textColor = 0xffffff opacity 0.8
          }
       }
    }
    ```

2.
    ```kotlin
    val contentView = koshian(context) {
       LinearLayout {
          TextView {
             view.text = "hello"
          }
       }
    }

    contentView.applyKoshian {
       TextView {
          view.textColor = 0xffffff opacity 0.8
       }
    }
    ```

When mismatched View is specified, Koshian creates a new View and inserts it.

![](https://raw.github.com/wcaokaze/Koshian/master/imgs/applier_insertion.svg?sanitize=true)


Naming
--------------------------------------------------------------------------------

Also, naming View is a good way.

![](https://raw.github.com/wcaokaze/Koshian/master/imgs/applier_named.svg?sanitize=true)


Style
--------------------------------------------------------------------------------

We can define styles.

Enjoy the powerful DSL.

![](https://raw.github.com/wcaokaze/Koshian/master/imgs/enjoy.svg?sanitize=true)


Submodules
--------------------------------------------------------------------------------

#### [Koshian-Generator](koshian-generator)

A helper to ready Koshian for external Views.


#### [Koshian-RecyclerView](koshian-recyclerview)

Makes RecyclerView easier.


Install
--------------------------------------------------------------------------------
Gradle
```groovy
repositories {
   jcenter()
}

dependencies {
   implementation 'com.wcaokaze.koshian:koshian-core:0.5.2'
}
```

Gradle (Kotlin)
```kotlin
repositories {
   jcenter()
}

dependencies {
   implementation("com.wcaokaze.koshian:koshian-core:0.5.2")
}
```

