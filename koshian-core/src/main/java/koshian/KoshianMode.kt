package koshian

sealed class KoshianMode {
   object Creator : KoshianMode()
   object Applier : KoshianMode()
}
