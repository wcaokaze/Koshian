
Koshian (WIP)
================================================================================

DSL to layout Android Views.

```kotlin
private val userIconView: ImageView
private val usernameView: TextView

private val followButton: Button

init {
   val layout = koshian(context) {
      linearLayout {
         linearLayout {
            userIconView = imageView {
               view.image = drawable(R.drawable.ic_empty_user)
            }

            usernameView = textView {
               view.text = "@"
            }
         }

         followButton = button {
            view.text = "follow"
         }
      }
   }

   layout.applyKoshian {
      view.orientation = VERTICAL

      linearLayout {
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

      space {
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

