package cat.petrushkacat.exercises.ui

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cat.petrushkacat.exercises.ui.theme.Pink80

@Preview
@Composable
fun Center() {
    val elementModifier = Modifier
        .fillMaxWidth()
        .height(50.dp)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            Modifier
                .border(1.dp, Color.Gray)
                .wrapContentSize()
                .padding(10.dp)
        ) {
            Row(elementModifier.background(Color.Red)) {

            }
            Spacer(modifier = Modifier.height(25.dp))
            Row(elementModifier.background(Color.Yellow)) {

            }
            Spacer(modifier = Modifier.height(25.dp))
            Row(elementModifier.background(Color.Green)) {

            }
            Spacer(modifier = Modifier.height(25.dp))
            Button(modifier = elementModifier, onClick = {}) {
                Text("button")
            }

        }
    }
}

@Preview
@Composable
fun Scroll() {
    val elementModifier = Modifier
        .fillMaxWidth()
        .height(50.dp)

    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .border(1.dp, Color.Gray)
            .padding(10.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column() {
            Row(elementModifier.background(Color.Red)) {

            }
            Row(elementModifier.background(Color.Yellow)) {

            }
            Row(elementModifier.background(Color.Green)) {

            }
        }
        Button(modifier = elementModifier, onClick = {}) {
            Text("button")
        }

    }
}

@Preview
@Composable
fun ZStackView() {
    Box(modifier = Modifier.fillMaxSize(),
        Alignment.Center
    ) {
        Box(Modifier.fillMaxHeight(0.5f).width(200.dp).background(Color.Yellow).align(Alignment.CenterStart))
        Box(Modifier.fillMaxHeight(0.80f).width(200.dp).background(Color.Blue).align(Alignment.CenterEnd))
        Box(Modifier.fillMaxHeight(0.85f).width(200.dp).background(Color.Red).align(Alignment.Center))

    }
}

@Preview
@Composable
fun BoxInBoxInBoxInBoxInBox() {
    val modifier = if(LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Modifier.fillMaxWidth(0.7f)
        } else {
            Modifier.fillMaxHeight(0.7f)
        }
    Box(Modifier.fillMaxSize().background(Color.Black)) {
        Box(Modifier.fillMaxSize(0.995f).background(Color.White).align(Alignment.BottomStart)) {
            Box(
                modifier
                    .aspectRatio(1f)
                    .background(Color.Red)
                    .align(Alignment.TopStart)
                    .border(1.dp, Color.Blue)
            ) {
                Box(
                    Modifier
                        .fillMaxSize(0.15f)
                        .background(Color.Cyan)
                        .align(Alignment.BottomStart)
                        .border(1.dp, Color.White)
                ) {}
                Box(
                    Modifier
                        .fillMaxSize(0.15f)
                        .background(Color.Green)
                        .align(Alignment.TopStart)
                        .border(1.dp, Color.White)
                ) {}
                Box(
                    Modifier
                        .fillMaxSize(0.15f)
                        .background(Pink80)
                        .align(Alignment.Center)
                        .border(1.dp, Color.White)
                ) {}
                Box(
                    Modifier
                        .fillMaxSize(0.15f)
                        .background(Color.Black)
                        .align(Alignment.TopEnd)
                        .border(1.dp, Color.White)
                ) {
                    Box(
                        Modifier
                            .fillMaxSize(0.5f)
                            .background(Color.Yellow)
                            .align(Alignment.TopEnd)
                            .border(1.dp, Color.White)
                    ) {}
                }
            }
        }
    }
}