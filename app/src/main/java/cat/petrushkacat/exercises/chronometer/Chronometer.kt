package cat.petrushkacat.exercises.chronometer

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

@Preview
@Composable
fun ChronometerUi() {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val secondsCount = remember { mutableStateOf(0) }
    val toastText = remember { MutableStateFlow("0") }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(secondsCount.value.toString())
    }

    LaunchedEffect(key1 = true) {
        scope.launch {
            launch {
                while (isActive) {
                    delay(1000)
                    secondsCount.value += 1
                    toastText.value = secondsCount.value.toString()
                }
            }
            launch {
                delay(9000)
                toastText.collect {
                    try {
                        if (Integer.parseInt(it) % 10 == 0) {
                            delay(100)
                            Log.d("toast text", toastText.value)
                            Toast.makeText(context, toastText.value, Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        Toast.makeText(context, toastText.value, Toast.LENGTH_SHORT).show()
                    }
                }

            }
            launch {
                toastText.collect {
                    if (secondsCount.value % 40 == 0) {
                        toastText.value = "Surprise"
                    }
                }

            }
        }
    }
}