package xyz.marcelo.composestatedemo

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class CounterViewModel : ViewModel() {
    private val _screenState = mutableStateOf(
        MainScreenState(
            inputValue = "",
            displayingResult = "Total is 0.0"
        )
    )

    val screenState: State<MainScreenState> = _screenState

    private val _uiEventFlow = MutableSharedFlow<UIEvent>()
    val uiEventFlow = _uiEventFlow.asSharedFlow()

    private var total = 0.0
    private fun addToTotal() {
        total += _screenState.value.inputValue.toDouble()
        _screenState.value = _screenState.value.copy(
            displayingResult = "Total is $total",
            isCountButtonVisible = true
        )
    }

    private fun resetTotal() {
        total = 0.0
        _screenState.value = _screenState.value.copy(
            displayingResult = "Total is $total",
            inputValue = "",
            isCountButtonVisible = true
        )
    }

    fun onEvent(event: CounterEvent) {
        when (event) {
            is CounterEvent.ValueEntered -> {
                _screenState.value = _screenState.value.copy(
                    inputValue = event.value,
                    isCountButtonVisible = true
                )
            }

            is CounterEvent.CountButtonClicked -> {
                addToTotal()
                viewModelScope.launch {
                    _uiEventFlow.emit(
                        UIEvent.ShowMessage(
                            message = "Value added successfully"
                        )
                    )
                }
            }

            is CounterEvent.ResetButtonClicked -> {
                resetTotal()
                viewModelScope.launch {
                    _uiEventFlow.emit(
                        UIEvent.ShowMessage(
                            message = "Value reset successfully"
                        )
                    )
                }
            }
        }
    }
}