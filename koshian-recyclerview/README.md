
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

recyclerView.adapter = object : KoshianRecyclerViewAdapter<TimelineItem> { _, item ->
   when (item) {
      is StatusItem           -> ViewHolderProvider(::StatusViewHolder)
      is LoadingIndicatorItem -> ViewHolderProvider(::LoadingIndicatorViewHolder)
   }
}
```

If you want DiffUtil:
```kotlin
recyclerView.adapter = KoshianRecyclerViewAdapter<TimelineItem>() {
   override fun selectViewHolderProvider(position: Int, item: TimelineItem): ViewHolderProvider<*> {
      return when (item) {
         is StatusItem           -> ViewHolderProvider(::StatusViewHolder)
         is LoadingIndicatorItem -> ViewHolderProvider(::LoadingIndicatorViewHolder)
      }
   }

   override fun areItemTheSame(oldItem: TimelineItem, newItem: TimelineItem): Boolean {
      when (oldItem) {
         is StatusItem -> {
            if (newItem !is StatusItem) { return false }

            return oldItem.status.id == newItem.status.id
         }

         is LoadingIndicatorItem -> {
            return newItem is LoadingIndicatorItem
         }
      }
   }

   override fun areContentsTheSame(oldItem: TimelineItem, newItem: TimelineItem): Boolean {
      when (oldItem) {
         is StatusItem -> {
            if (newItem !is StatusItem) { return false }

            return oldItem.status == newItem.status
         }

         is LoadingIndicatorItem -> {
            return newItem is LoadingIndicatorItem
         }
      }
   }
}
```

