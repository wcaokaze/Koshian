
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

Koshian specifying a name doesn't affect the cursor.
Koshian not specifying a name ignores named Views.
Named Views and non-named Views are simply in other worlds.

![](https://raw.github.com/wcaokaze/Koshian/master/imgs/applier_mixing_named_and_non_named.svg?sanitize=true)

For readability, it is recommended to put named Views
as synchronized with the cursor.

![](https://raw.github.com/wcaokaze/Koshian/master/imgs/applier_readable_mixing.svg?sanitize=true)


Install
--------------------------------------------------------------------------------
Gradle
```groovy
repositories {
   // jcenter()  // Not yet available

   maven { url 'https://dl.bintray.com/wcaokaze/Koshian' }
}

dependencies {
   implementation 'com.wcaokaze.koshian:koshian-core:0.1.0'
}
```

Gradle (Kotlin)
```kotlin
repositories {
   // jcenter()
   maven(url = "https://dl.bintray.com/wcaokaze/Koshian")
}

dependencies {
   implementation("com.wcaokaze.koshian:koshian-core:0.1.0")
}
```

