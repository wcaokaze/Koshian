package koshian

import android.view.*
import android.widget.*

inline val Koshian<ViewManager, *, *>.frameLayout get() = KoshianViewConstructor(::FrameLayout)
