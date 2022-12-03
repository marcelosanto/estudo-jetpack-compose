package xyz.marcelo.composestatedemo

data class MainScreenState(
    var isCountButtonVisible: Boolean = false,
    var displayingResult: String = "",
    var inputValue: String = ""
)