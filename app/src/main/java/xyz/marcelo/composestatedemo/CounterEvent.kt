package xyz.marcelo.composestatedemo

sealed class CounterEvent {
    data class ValueEntered(val value: String) : CounterEvent()
    object CountButtonClicked : CounterEvent()
    object ResetButtonClicked : CounterEvent()
}