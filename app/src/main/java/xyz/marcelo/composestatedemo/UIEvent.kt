package xyz.marcelo.composestatedemo

sealed class UIEvent {
    data class ShowMessage(val message: String) : UIEvent()
}