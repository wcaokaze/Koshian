package koshian

import android.widget.*

inline val KoshianParent.textView get() = KoshianViewConstructor(::TextView)
