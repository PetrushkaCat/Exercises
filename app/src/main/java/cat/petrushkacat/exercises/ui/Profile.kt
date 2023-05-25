package cat.petrushkacat.exercises.ui

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Comment
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.PanoramaFishEye
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun ProfileUi() {
    val scope = rememberCoroutineScope()
    val profile = remember { mutableStateOf( Profile() ) }
    val backgroundColor = remember { mutableStateOf(Color.Green) }
    val textColor = Color.White
    val textColorDark = Color.DarkGray
    val view = LocalView.current

    LaunchedEffect(key1 = true) {
        scope.launch {
            for(index in 1..10) {

                profile.value = Profile(
                    "Tindexle dsgfg rg rer h hththhth ntnt ger ge egegegeg $index",
                    "Name $index",
                    "$index days ago",
                    index * 11,
                    index * 21,
                    index * 9,
                    index * 111,
                    index * 23,
                    index * 57
                )
                backgroundColor.value = Color(143567 + index * -500000)
                delay(5000)
            }
        }
    }
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = backgroundColor.value.toArgb()
            window.navigationBarColor = backgroundColor.value.toArgb()
        }
    }
    Column {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(containerColor = backgroundColor.value),
            title = {},
            navigationIcon = {
                Icon(
                    Icons.Default.ArrowBackIos,
                    null,
                    modifier = Modifier
                        .clickable { }
                        .size(48.dp)
                        .padding(horizontal = 10.dp),
                    tint = textColor

                )
            },
            actions = {
                Icon(
                    Icons.Default.MoreHoriz,
                    null,
                    modifier = Modifier
                        .clickable { }
                        .size(48.dp)
                        .padding(horizontal = 10.dp),
                    tint = textColor
                )
            }
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor.value)
                .padding(24.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            Column {
                Text(profile.value.title,
                    style = MaterialTheme.typography.titleLarge.copy(textColor))
                Spacer(Modifier.height(20.dp))
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row {
                        Box(modifier = Modifier
                            .background(Color.Blue, CircleShape)
                            .size(60.dp)
                        ) {}
                        Spacer(Modifier.width(10.dp))
                        Column(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Center) {
                            Text(
                                profile.value.name,
                                style = MaterialTheme.typography.bodyLarge.copy(textColor)
                            )
                            Text(
                                profile.value.ago,
                                style = MaterialTheme.typography.bodyMedium.copy(textColorDark)
                            )
                        }
                    }
                    Button(onClick = { },
                        modifier = Modifier
                            .background(
                                Brush.horizontalGradient(
                                    listOf(Color(0xFFFF5900), Color.Red)
                                ), shape = RoundedCornerShape(24.dp)
                            )
                            .height(35.dp),
                        contentPadding = PaddingValues(horizontal = 24.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                    ) {
                        Text("+Follow", style = TextStyle(textColor))
                    }
                }
                Spacer(modifier = Modifier.height(30.dp))
                Divider(Modifier.fillMaxWidth())
                Spacer(Modifier.height(10.dp))
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Recommended by:",
                        style = MaterialTheme.typography.bodyMedium.copy(textColorDark))
                        Text("${profile.value.recommendUsers} / ${profile.value.totalUsers} users",
                            style = MaterialTheme.typography.bodyMedium.copy(textColorDark))
                    }
                    Spacer(Modifier.height(10.dp))
                    Row(modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row {
                            Box(modifier = Modifier.width(70.dp)) {
                                repeat(5) {
                                    val index = if(it == 0) (it - 5).absoluteValue else it
                                    if(index != 5) {
                                        Box(modifier = Modifier
                                            .offset(x = (index * 15).dp)
                                            .size(25.dp)
                                            .background(Color.Blue, CircleShape)
                                            .border(1.dp, Color.White, shape = CircleShape),
                                            contentAlignment = Alignment.Center
                                        ) {}
                                    } else {
                                        Box(modifier = Modifier
                                            .offset(x = (index * 15).dp)
                                            .size(25.dp)
                                            .background(Color.Gray, CircleShape),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                "+" + (profile.value.totalUsers - 4).toString(),
                                                textAlign = TextAlign.Center,
                                                style = TextStyle(textColor, fontSize = 10.sp)
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                Icons.Default.Visibility,
                                null,
                                modifier = Modifier
                                    .size(25.dp),
                                tint = textColorDark
                            )
                            Text(profile.value.seen.toString(),
                                style = MaterialTheme.typography.bodyMedium.copy(textColorDark))
                            Spacer(Modifier.width(10.dp))
                            Icon(
                                Icons.Default.ChatBubble,
                                null,
                                modifier = Modifier
                                    .size(25.dp),
                                tint = textColorDark
                            )
                            Text(profile.value.comments.toString(),
                                style = MaterialTheme.typography.bodyMedium.copy(textColorDark))
                            Spacer(Modifier.width(10.dp))
                            Icon(
                                Icons.Default.Favorite,
                                null,
                                modifier = Modifier
                                    .size(25.dp),
                                tint = textColorDark
                            )
                            Text(profile.value.likes.toString(),
                                style = MaterialTheme.typography.bodyMedium.copy(textColorDark))
                        }
                    }
                }
            }
        }
    }
}

data class Profile(
    val title: String = "Title Title egrght ntnte btnture berbtjnywgb brbbrebn brbtnte rebrben ereherh",
    val name: String = "name surname",
    val ago: String = "1 day ago",
    val recommendUsers: Int = 40,
    val totalUsers: Int = 62,
    val amountOfCircles: Int = 15,
    val seen: Int = 1002,
    val comments: Int = 100,
    val likes: Int = 500
)