package cat.petrushkacat.exercises.ui

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import cat.petrushkacat.exercises.MainActivity
import cat.petrushkacat.exercises.patterns.WeatherSource

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ObserverUi() {
    //handmade observers in compose is definitely a pain...
    val page = remember { mutableStateOf(-1) }
    val activity = LocalContext.current as MainActivity
    val weather by activity.temperature.collectAsState()

    HorizontalPager(pageCount = 3) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(weather.toString())

            LaunchedEffect(key1 = it) {
                if(it != page.value) {
                    Log.d("observer", "removed/added")
                    WeatherSource.removeObserver(activity.observer)
                    WeatherSource.addObserver(activity.observer)
                    page.value = it
                }
            }
        }
    }
}