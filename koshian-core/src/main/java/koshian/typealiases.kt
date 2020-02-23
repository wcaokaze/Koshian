package koshian

import android.view.*

typealias KoshianParent = Koshian<ViewManager, *, *>

typealias ViewBuilder     <V, L>     = Koshian<V, L, Nothing>
typealias ViewGroupBuilder<V, L, CL> = Koshian<V, L, CL>
