package cat.petrushkacat.exercises.counter

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CounterViewModel: ViewModel() {

    private val _currentRoundValue = MutableStateFlow(0.dp)
    val currentRoundValue = _currentRoundValue.asStateFlow()

    fun updateCounter(value: Dp) {
        _currentRoundValue.value += value
    }
}