package cat.petrushkacat.exercises

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import cat.petrushkacat.a_patterns.CoffeeBase
import cat.petrushkacat.a_patterns.CoffeeJava
import cat.petrushkacat.a_patterns.Country
import cat.petrushkacat.a_patterns.Factory
import cat.petrushkacat.a_patterns.Observer
import cat.petrushkacat.a_patterns.RailDeliveryType
import cat.petrushkacat.a_patterns.Sun
import cat.petrushkacat.a_patterns.WeatherSource
import cat.petrushkacat.exercises.ui.Center
import cat.petrushkacat.exercises.ui.ObserverUi
import cat.petrushkacat.exercises.ui.theme.ExercisesTheme
import kotlinx.coroutines.flow.MutableStateFlow

class MainActivity : ComponentActivity() {

    val observer = object: Observer {
        override fun update(weather: Int) {
            temperature.value = weather
        }
    }
    val temperature = MutableStateFlow(-1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val coffee = CoffeeJava.CoffeeBuilder(CoffeeBase.Single)
            .addMilk()
            .addCinnamon()
            .addSugar()
            .build()

        val russiaDelivery = Factory().createDelivery(
            Country.Russia("company", 1000, 303,
                RailDeliveryType(1520, true))
        )

        Log.d("pattern_cofeee", coffee.toString())
        Log.d("pattern_sun", Sun.toString()  + " " + Sun.toString())
        Log.d("pattern_factory", (russiaDelivery as Factory.Delivery.Rail).railDeliveryType.toString())


        WeatherSource.addObserver(observer = observer)

        setContent {
            ExercisesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Center()

                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        WeatherSource.removeObserver(observer)
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ExercisesTheme {
        Greeting("Android")
    }
}