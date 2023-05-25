package cat.petrushkacat.exercises.counter

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cat.petrushkacat.exercises.ui.Center
import cat.petrushkacat.exercises.ui.theme.ExercisesTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class CounterActivity: ComponentActivity() {

    private val counterViewModel by viewModels<CounterViewModel>()
    private var counterJob = Job()
    private var isActivityInitialized = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ExercisesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CounterScreenMain()
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        counterViewModel.updateCounter(5.dp)
    }

    override fun onResume() {
        super.onResume()
        if(isActivityInitialized) {
            counterViewModel.updateCounter(2.dp)
            counterJob.cancel()
        }
        isActivityInitialized = true
    }

    override fun onStop() {
        super.onStop()
        counterJob = Job()
        CoroutineScope(Dispatchers.Default + counterJob).launch {
            while (isActive) {
                delay(1000)
                counterViewModel.updateCounter(2.dp)
                Log.d("counter", "+2")
            }
        }
    }
}