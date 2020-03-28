/*
 * Copyright 2020 wcaokaze
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package koshian

import android.view.*

typealias KoshianExt<V, L> = Koshian<V, L, *, *>

typealias CreatorParent<L> = Koshian<ViewManager, *, L, KoshianMode.Creator>
typealias ViewCreator     <V, L>     = Koshian<V, L, Nothing, KoshianMode.Creator>
typealias ViewGroupCreator<V, L, CL> = Koshian<V, L, CL,      KoshianMode.Creator>

typealias ApplierParent<L, S> = Koshian<ViewGroup, *, L, KoshianMode.Applier<S>>
typealias ViewApplier     <V, L,     S> = Koshian<V, L, Nothing, KoshianMode.Applier<S>>
typealias ViewGroupApplier<V, L, CL, S> = Koshian<V, L, CL,      KoshianMode.Applier<S>>

typealias ViewStyle<V> = Koshian<V, Nothing, Nothing, KoshianMode.Style>
