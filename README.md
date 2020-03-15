
Koshian (WIP)
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

