package koshian

import android.view.*
import android.widget.*

inline val Koshian<ViewManager>.textView get() = KoshianViewConstructor(::TextView)
