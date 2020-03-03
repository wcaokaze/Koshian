package koshian

import android.view.*

typealias KoshianParent<L, M> = Koshian<ViewManager, *, L, M>

typealias ViewBuilder     <V, L,     M> = Koshian<V, L, Nothing, M>
typealias ViewGroupBuilder<V, L, CL, M> = Koshian<V, L, CL,      M>
