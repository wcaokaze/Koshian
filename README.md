
Koshian
================================================================================

DSL to layout Android Views.

```kotlin
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

![](https://raw.github.com/wcaokaze/Koshian/master/imgs/preview.png)

We can preview Koshian layouts.

See [Koshian-core](koshian-core/README.md) for more informations.


Modules
--------------------------------------------------------------------------------

#### [Koshian-core](koshian-core)

Koshian's main functions.


#### [Koshian-Generator](koshian-generator)

A helper to ready Koshian for external Views.


#### [Koshian-RecyclerView](koshian-recyclerview)

Makes RecyclerView easier.


Code Style Recommendation
--------------------------------------------------------------------------------

Koshian has too many top level functions.
So it is recommended to import them with `*`.

```kotlin
import koshian.*
```

### IntelliJ IDEA, Android Studio Settings

Settings > Editor > Code Style > Kotlin > Imports > Packages to Use Import with `*`

Add `koshian` and check `With Subpackages`


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

