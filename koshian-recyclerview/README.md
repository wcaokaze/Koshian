
Koshian-RecyclerView
================================================================================

RecyclerView is so flexible.
However, even in a very simple usage, it requires a boring pattern 

```kotlin
sealed class TimelineItem
class StatusItem(val status: Status) : TimelineItem()
object LoadingIndicatorItem : TimelineItem()

class TimelineRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
   companion object {
      private const val VIEW_TYPE_STATUS            = 0
      private const val VIEW_TYPE_LOADING_INDICATOR = 1
   }

   var items: List<TimelineItem> = emptyList()
      set(value) {
         field = value
         notifyDataSetChanged() // or something using DiffUtil
      }

   override fun getItemCount() = items.size

   override fun getItemViewType(position: Int): Int {
      return when (items[position]) {
         is StatusItem           -> VIEW_TYPE_STATUS
         is LoadingIndicatorItem -> VIEW_TYPE_LOADING_INDICATOR
      }
   }

   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
      return when (viewType) {
         VIEW_TYPE_STATUS            -> StatusViewHolder(parent.context)
         VIEW_TYPE_LOADING_INDICATOR -> LoadingIndicatorViewHolder(parent.context)
         else -> throw NoWhenBranchMatchedException()
      }
   }

   override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
      when (holder) {
         is StatusViewHolder           -> holder.bind(items[position] as StatusItem)
         is LoadingIndicatorViewHolder -> holder.bind(items[position] as LoadingIndicatorItem)
      }
   }
}

class StatusViewHolder(context: Context) : RecyclerView.ViewHolder(LinearLayout(context)) {
   private val contentView: TextView

   fun bind(item: StatusItem) {
      contentView.text = item.status.content
   }

   init {
      (itemView as LinearLayout).addView {
         contentView = TextView {
         }
      }
   }
}
```

Make it easy with Koshian-RecyclerView.
```kotlin
sealed class TimelineItem
class StatusItem(val status: Status) : TimelineItem()
object LoadingIndicatorItem : TimelineItem()

class StatusViewHolder(context: Context) : KoshianViewHolder<StatusItem>() {
   override val itemView: View

   private val contentView: TextView

   override fun bind(item: StatusItem) {
      contentView.text = item.status.content
   }

   init {
      itemView = koshian(context) {
         LinearLayout {
            view.orientation = VERTICAL

            contentView = TextView {
            }
         }
      }
   }
}

class TimelineRecyclerViewAdapter : KoshianRecyclerViewAdapter<TimelineItem>() {
   override fun selectViewHolderProvider
         (position: Int, item: TimelineItem): ViewHolderProvider<*>
   {
      return when (item) {
         is StatusItem -> ViewHolderProvider(item, ::StatusViewHolder)
         is LoadingIndicatorItem -> ViewHolderProvider(item, ::LoadingIndicatorViewHolder)
      }
   }
}
```

If you want DiffUtil:
```kotlin
sealed class TimelineItem : DiffUtilItem

class StatusItem(val status: Status) : TimelineItem() {
   override fun isContentsTheSameWith(item: Any): Boolean
      = item is StatusItem && item.status.id == status.id

   override fun isItemsTheSameWith(item: Any): Boolean
      = item is StatusItem && item.status == status
}

object LoadingIndicatorItem : TimelineItem() {
   override fun isContentsTheSameWith(item: Any) = item is LoadingIndicatorItem
   override fun isItemsTheSameWith   (item: Any) = item is LoadingIndicatorItem
}
```


Install
--------------------------------------------------------------------------------
Gradle
```groovy
repositories {
   // jcenter()  // Not yet available

   maven { url 'https://dl.bintray.com/wcaokaze/maven' }
}

dependencies {
   implementation 'com.wcaokaze.koshian:koshian-recyclerview:0.3.1'
}
```

Gradle (Kotlin)
```kotlin
repositories {
   // jcenter()
   maven(url = "https://dl.bintray.com/wcaokaze/maven")
}

dependencies {
   implementation("com.wcaokaze.koshian:koshian-recyclerview:0.3.1")
}
```

