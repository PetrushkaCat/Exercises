package cat.petrushkacat.exercises.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun Compound() {
    val colors = listOf(Color.Red, Color.Yellow, Color.Green,
        Color.Black, Color.Blue, Color.Cyan, Color.Gray,
    Color.Magenta, Color.LightGray, Color.DarkGray)

    val current = rememberSaveable { mutableStateOf(0) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Box(modifier = Modifier.background(colors[current.value]).size((1000-7 -700).dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = {
                if(current.value < 9) current.value +=1 else current.value = 0
            },
                modifier = Modifier.width(120.dp)
            ) {
                Text("Previous")
            }
            Spacer(modifier = Modifier.width(20.dp))

            Text("Current: ${current.value}")

            Spacer(modifier = Modifier.width(20.dp))
            Button(onClick = {
                if(current.value > 0) current.value -=1 else current.value = 9
            },
                modifier = Modifier.width(120.dp)
            ) {
                Text("Next")
            }
        }
    }
}