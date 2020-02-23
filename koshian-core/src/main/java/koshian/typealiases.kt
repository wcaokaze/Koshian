package koshian

import android.view.*

typealias KoshianParent = Koshian<ViewManager, *, *, *>

typealias ViewBuilder     <V, L,     M> = Koshian<V, L, Nothing, M>
typealias ViewGroupBuilder<V, L, CL, M> = Koshian<V, L, CL,      M>
