package koshian

import android.view.*

typealias KoshianExt<V, L> = Koshian<V, L, *, *>

typealias KoshianParent<L, M> = Koshian<ViewManager, *, L, M>

typealias ViewBuilder     <V, L,     M> = Koshian<V, L, Nothing, M>
typealias ViewGroupBuilder<V, L, CL, M> = Koshian<V, L, CL,      M>
