package cat.petrushkacat.exercises.counter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

enum class CounterScreens {
    Screen1,
    Screen2
}

@Preview
@Composable
fun CounterScreenMain() {
    val navController = rememberNavController()

    val viewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
        "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
    }

    NavHost(navController = navController, startDestination = CounterScreens.Screen1.name) {
        composable(CounterScreens.Screen1.name) {
            CompositionLocalProvider(LocalViewModelStoreOwner provides viewModelStoreOwner) {
                CounterScreen1 {
                    navController.navigate(CounterScreens.Screen2.name)
                }
            }
        }
        composable(CounterScreens.Screen2.name) {
            CompositionLocalProvider(LocalViewModelStoreOwner provides viewModelStoreOwner) {
                CounterScreen2 {
                    navController.popBackStack()
                }
            }
        }
    }

}

@Composable
fun CounterScreen1(
    navigateToNext: () -> Unit,
) {

    val counterViewModel = viewModel<CounterViewModel>(LocalViewModelStoreOwner.current!!)
    val roundValue by counterViewModel.currentRoundValue.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(300.dp)
                .background(Color.Yellow, shape = RoundedCornerShape(roundValue))
        ) {
            Text(
                roundValue.value.toInt().toString(), modifier = Modifier
                    .align(Alignment.TopCenter)
                    .offset(y = 8.dp)
            )
            Button(
                onClick = { navigateToNext() },
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
                modifier = Modifier
                    .padding(8.dp)
                    .size(100.dp)
                    .align(Alignment.BottomCenter)
            ) {
                Text(
                    "Tap",
                    style = TextStyle(color = contentColorFor(backgroundColor = Color.Blue))
                )
            }
        }
    }
}

@Composable
fun CounterScreen2(
    navigateToPrevious: () -> Unit,
) {
    val counterViewModel = viewModel<CounterViewModel>(LocalViewModelStoreOwner.current!!)

    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Bottom
    ) {
        Button(
            onClick = { navigateToPrevious() }) {
            Text("Cancel")
        }
        Spacer(Modifier.width(16.dp))
        Button(
            onClick = {
                counterViewModel.updateCounter(10.dp)
                navigateToPrevious()
            }) {
            Text("Update")
        }
    }
}