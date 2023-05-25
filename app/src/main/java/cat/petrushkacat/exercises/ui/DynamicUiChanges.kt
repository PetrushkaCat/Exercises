package cat.petrushkacat.exercises.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cat.petrushkacat.exercises.R

enum class DynamicUiRoutes() {
    MainScreen,
    Screen1,
    Screen2,
    Screen3,
}

@Preview
@Composable
fun DynamicUiChangesNavHost(
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = DynamicUiRoutes.MainScreen.name,
    ) {
        composable(DynamicUiRoutes.MainScreen.name) {
            DynamicUiMainScreen(
                navigateToScreen1 = {
                    navController.navigate(DynamicUiRoutes.Screen1.name)
                },
                navigateToScreen2 = {
                    navController.navigate(DynamicUiRoutes.Screen2.name)
                },
                navigateToScreen3 = {
                    navController.navigate(DynamicUiRoutes.Screen3.name)
                })
        }
        composable(DynamicUiRoutes.Screen1.name) { DynamicUiScreen1() }
        composable(DynamicUiRoutes.Screen2.name) { DynamicUiScreen2() }
        composable(DynamicUiRoutes.Screen3.name) { DynamicUiScreen3() }
    }
}

@Composable
fun DynamicUiMainScreen(
    navigateToScreen1: () -> Unit,
    navigateToScreen2: () -> Unit,
    navigateToScreen3: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Bottom,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = { navigateToScreen1() }) {
                Text("Screen 1")
            }
            Button(onClick = { navigateToScreen2() }) {
                Text("Screen 2")
            }
            Button(onClick = { navigateToScreen3() }) {
                Text("Screen 3")
            }
        }
    }
}

@Composable
fun DynamicUiScreen1() {
    val isHidden = rememberSaveable() { mutableStateOf(false) }

    Box(Modifier.fillMaxSize()) {
        Column(modifier = Modifier.align(Alignment.Center)) {
            if (!isHidden.value) {
                Image(
                    painterResource(id = R.drawable.kawaii_cat_but_only_300px),
                    "cat cat cat"
                )
            } else {
                Text("Hidden")
            }
        }
        Column(modifier = Modifier.align(Alignment.BottomCenter)) {
            DynamicUiButtons(
                onFirstButtonClick = { isHidden.value = true },
                onSecondButtonClick = { isHidden.value = false },
                firstButtonText = "Hide",
                secondButtonText = "Show"
            )
        }
    }
}

@Composable
fun DynamicUiScreen2() {

    val catsQuantity = rememberSaveable { mutableStateOf(0) }

    Box(Modifier.fillMaxSize()) {
        LazyRow(modifier = Modifier.align(Alignment.Center)) {
            items(catsQuantity.value) {
                Image(
                    painterResource(id = R.drawable.kawaii_cat_but_only_300px),
                    "cat cat cat"
                )
            }
        }
        Column(modifier = Modifier.align(Alignment.BottomCenter)) {
            DynamicUiButtons(
                onFirstButtonClick = {
                    if (catsQuantity.value > 0) {
                        catsQuantity.value -= 1
                    }
                },
                onSecondButtonClick = { catsQuantity.value += 1 },
                firstButtonText = "Remove",
                secondButtonText = "Add"
            )
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun DynamicUiScreen3() {

    val alpha = rememberSaveable { mutableStateOf(0.5f) }

    val imageAlpha: Float by animateFloatAsState(
        targetValue = alpha.value,
        animationSpec = tween(
            durationMillis = 1000,
            easing = LinearEasing,
        )
    )

    Box(Modifier.fillMaxSize()) {
        Column(modifier = Modifier.align(Alignment.Center)) {
            Image(
                painterResource(id = R.drawable.kawaii_cat_but_only_300px),
                "cat cat cat",
                alpha = imageAlpha,
                modifier = Modifier.animateContentSize { initialValue, targetValue ->  }
                )
            
        }
        Column(modifier = Modifier.align(Alignment.BottomCenter)) {
            DynamicUiButtons(
                onFirstButtonClick = { if(alpha.value > 0f) alpha.value -= 0.1f else alpha.value = 0f },
                onSecondButtonClick = { if(alpha.value < 1f) alpha.value += 0.1f else alpha.value = 1f },
                firstButtonText = "Remove alpha",
                secondButtonText = "Add alpha"
            )
        }
    }
}

@Composable
fun DynamicUiButtons(
    onFirstButtonClick: () -> Unit,
    onSecondButtonClick: () -> Unit,
    firstButtonText: String = "Button1",
    secondButtonText: String = "Button2"
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(onClick = { onFirstButtonClick() }) {
            Text(firstButtonText)
        }
        Spacer(modifier = Modifier.width(16.dp))
        Button(onClick = { onSecondButtonClick() }) {
            Text(secondButtonText)
        }
    }
}