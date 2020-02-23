package koshian

sealed class KoshianMode {
   object Create : KoshianMode()
   object Applier : KoshianMode()
}
