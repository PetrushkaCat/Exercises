package cat.petrushkacat.exercises.ui.list

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Text as Text3
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.Male
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

sealed class Screen(val route: String) {
    object Screen1 : Screen("screen1")
    object Screen2 : Screen("screen2")
    object Screen3 : Screen("screen3")
    object Screen4 : Screen("screen4")
    object Screen5 : Screen("screen5")
}

@Preview
@Composable
fun MainScreen() {
    val screens =
        listOf(Screen.Screen1, Screen.Screen2, Screen.Screen3, Screen.Screen4, Screen.Screen5)
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigation {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentScreen = navBackStackEntry?.destination
                screens.forEach { screen ->
                    BottomNavigationItem(
                        selected = currentScreen?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }

                        },
                        label = {
                            Text(
                                screen.route[6].toString(),
                                style = TextStyle(fontSize = 16.sp)
                            )
                        },
                        icon = { },
                        selectedContentColor = Color.Red,
                        unselectedContentColor = Color.White
                    )
                }
            }
        },
        content = {
            NavHost(
                navController = navController,
                startDestination = Screen.Screen1.route,
                modifier = Modifier.padding(it)
            ) {
                composable(Screen.Screen1.route) { Screen1() }
                composable(Screen.Screen2.route) { Screen2() }
                composable(Screen.Screen3.route) { Screen3() }
                composable(Screen.Screen4.route) { Screen4() }
                composable(Screen.Screen5.route) { Screen5() }
            }
        })
}

@Preview
@Composable
fun Screen1() {
    val users = UsersGenerator.generateUsers()

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(users.size) {
            Screen1Item(user = users[it])
        }
    }
}

@Composable
fun Screen1Item(user: User) {
    Row(
        modifier = Modifier
            .background(Color.White)
            .height(72.dp)
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(9f)) {
            AsyncImage(
                model = user.squareAvatarUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape),
            )
            Spacer(Modifier.width(16.dp))
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(user.firstName + " " + user.lastName)
                Text(
                    user.description,
                    style = TextStyle(color = Color.Gray, fontSize = 14.sp),
                    maxLines = 2
                )
            }
        }

        val icon = if (user.sex == Sex.Male) Icons.Default.Male else Icons.Default.Female
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
            Spacer(Modifier.width(16.dp))
            Icon(icon, null, modifier = Modifier.size(24.dp))
        }
    }
}

@Preview
@Composable
fun Screen2() {
    val users = UsersGenerator.generateUsers()

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(users.size) {
            Screen2Item(user = users[it])
        }
    }
}

@Composable
fun Screen2Item(user: User) {
    Row(
        modifier = Modifier
            .background(Color.White)
            .height(88.dp)
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row(modifier = Modifier.weight(9f)) {
            AsyncImage(
                model = user.squareAvatarUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(44.dp)
                    .clickable { }
                    .padding(top = 4.dp),
            )
            Spacer(Modifier.width(16.dp))
            Column(
            ) {
                Text(user.firstName + " " + user.lastName)
                Text("Age: ${user.age}",
                    style = TextStyle(color = Color.Gray, fontSize = 14.sp))
                Text(
                    user.description,
                    style = TextStyle(color = Color.Gray, fontSize = 14.sp),
                    maxLines = 2
                )
            }
        }

        val icon = if (user.sex == Sex.Male) Icons.Default.Male else Icons.Default.Female
        Row(modifier = Modifier.weight(1f)) {
            Spacer(Modifier.width(16.dp))
            Icon(icon, null, modifier = Modifier
                .size(28.dp)
                .padding(top = 4.dp))
        }
    }
}


@Preview
@Composable
fun Screen3() {
    val titles = listOf("Home", "Garden", "Kitchen", "Pharmacy", "Work", "Mall")
    val color = listOf(Color.LightGray, Color.Red, Color.Green, Color.Cyan, Color.Yellow, Color.Magenta)

    LazyVerticalGrid(modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(12.dp),
        columns = GridCells.Adaptive(150.dp)
    ) {
        items(20) {
            Screen3Item(titles[it % 6], color[it % 6])
        }
    }
}

@Composable
fun Screen3Item(
    text: String = "",
    color: Color = Color.Cyan
) {
    Column(modifier = Modifier
        .height(108.dp)
        .padding(4.dp)
        .background(color, RoundedCornerShape(8.dp))
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Icon(
                Icons.Default.Face,
                null,
                modifier = Modifier.size(90.dp),
                tint = Color.White.copy(alpha = 0.5f)
            )
            Column(modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomStart)) {
                Text(text, style = MaterialTheme.typography.titleLarge)
            }
        }
    }
}

@Preview
@Composable
fun Screen4() {
    val users = UsersGenerator.generateUsers()

    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(top = 16.dp, end = 16.dp, start = 16.dp)
        .border(1.dp, Color.Gray)
    ) {
        items(users.size) {
            if(it % 3 == 0) {
                Screen4Item1()
            } else if(it % 3 == 1) {
                Screen4Item2()
            } else {
                Screen4Item3()
            }
        }
    }
}

@Composable
fun Screen4Item1() {
    Row(modifier = Modifier
        .height(100.dp)
        .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(modifier = Modifier
            .fillMaxSize()
            .weight(3f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text("Item 1: Big Box", textAlign = TextAlign.Center)
        }
        Row(Modifier.weight(1f)) {
            Divider(
                Modifier
                    .fillMaxHeight()
                    .width(1.dp))
            Column {
                Row(
                    modifier = Modifier
                        .height(50.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text("Item 1: Small Box 1", textAlign = TextAlign.Center)
                }
                Divider(Modifier.fillMaxWidth())
                Row(
                    modifier = Modifier
                        .height(50.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text("Item 1: Small Box 2", textAlign = TextAlign.Center)
                }
            }
        }
    }
    Divider(Modifier.fillMaxWidth())
}

@Composable
fun Screen4Item2() {
    Row(modifier = Modifier
        .height(100.dp)
        .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text("Item 2: Big Box", textAlign = TextAlign.Center)
    }
    Divider(Modifier.fillMaxWidth())
}

@Composable
fun Screen4Item3() {
    Row(modifier = Modifier
        .height(100.dp)
        .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(modifier = Modifier.weight(1f)) {
            Column {
                Row(
                    modifier = Modifier
                        .height(50.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text("Item 3: Small Box 1", textAlign = TextAlign.Center)
                }
                Divider(Modifier.fillMaxWidth())
                Row(
                    modifier = Modifier
                        .height(50.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text("Item 3: Small Box 2", textAlign = TextAlign.Center)
                }
            }
            Divider(
                Modifier
                    .fillMaxHeight()
                    .requiredWidth(1.dp))
        }
        Row(modifier = Modifier
            .fillMaxSize()
            .weight(3f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text("Item 3: Big Box", textAlign = TextAlign.Center)
        }
    }

    Divider(Modifier.fillMaxWidth())
}
@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Preview
@Composable
fun Screen5() {
    val scope = rememberCoroutineScope()
    val users = remember { mutableStateOf<List<User>>(emptyList()) }
    val isRefreshing = remember { mutableStateOf(false) }
    val progress = remember { mutableStateOf(0f) }

    val pullRefreshState = rememberPullRefreshState(refreshing = isRefreshing.value, onRefresh = {
        scope.launch {
            isRefreshing.value = true
            val job = launch {
                repeat(100) {
                    delay(28)
                    progress.value += 1f / 100
                }
            }
            delay(3000)
            users.value = UsersGenerator.generateUsers()
            isRefreshing.value = false
            job.join()
            progress.value = 0f
        }
    })

    Box(Modifier.fillMaxSize()) {
       // if(isRefreshing.value) {
            CircularProgressIndicator(
                progress = progress.value,
                modifier = Modifier.align(Alignment.Center),
            )
       // }
        Column {
            TopAppBar(
                title = {},
                actions = {
                    Icon(
                        Icons.Default.Delete,
                        null,
                        modifier = Modifier
                            .clickable { users.value = emptyList() }
                            .size(48.dp)
                            .padding(10.dp)
                    )
                })
            if (users.value.isNotEmpty() || isRefreshing.value) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    items(users.value.size) {
                        Screen1Item(user = users.value[it])
                    }
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .pullRefresh(pullRefreshState)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Empty")
                }
            }
        }
    }


    LaunchedEffect(key1 = true) {
        scope.launch {
            isRefreshing.value = true
            val job = launch {
                repeat(200) {
                    delay(26)
                    progress.value += 1f / 200
                }
            }
            delay(6000)
            users.value = UsersGenerator.generateUsers()
            isRefreshing.value = false
            job.join()
            progress.value = 0f
        }
    }
}