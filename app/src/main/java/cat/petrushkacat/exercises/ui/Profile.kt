package cat.petrushkacat.exercises.ui

import android.app.Activity
import android.os.Build
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun ProfileUi() {
    val profile = Profile()
    val backgroundColor = Color.Green
    val textColor = Color.White
    val textColorDark = Color.DarkGray
    val view = LocalView.current
    if (!view.isInEditMode) {
        /*SideEffect {
            val window = (view.context as Activity).window
            WindowCompat.setDecorFitsSystemWindows(window, false)
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
                window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
            } else {
                window.insetsController?.apply {
                    hide(WindowInsets.Type.statusBars())
                    systemBarsBehavior =
                        WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                }
            }
        }*/
    }
    Column {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(containerColor = backgroundColor),
            title = {},
            navigationIcon = {
                Icon(
                    Icons.Default.ArrowBackIos,
                    null,
                    modifier = Modifier
                        .clickable { }
                        .size(48.dp)
                        .padding(horizontal = 10.dp)
                )
            },
            actions = {
                Icon(
                    Icons.Default.MoreHoriz,
                    null,
                    modifier = Modifier
                        .clickable { }
                        .size(48.dp)
                        .padding(horizontal = 10.dp)
                )
            }
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
                .padding(8.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            Column {
                Text(profile.title,
                    style = MaterialTheme.typography.titleLarge.copy(textColor))
                Spacer(Modifier.height(20.dp))
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row {
                        Icon(
                            Icons.Default.Face,
                            null,
                            modifier = Modifier.size(50.dp)
                        )
                        Column() {
                            Text(
                                profile.name,
                                style = MaterialTheme.typography.bodyLarge.copy(textColor)
                            )
                            Text(
                                profile.ago,
                                style = MaterialTheme.typography.bodyMedium.copy(textColorDark)
                            )
                        }
                    }
                    Button(onClick = { }, modifier = Modifier.height(30.dp)) {
                        Text("+Follow", style = TextStyle(textColor))
                    }
                }
                Spacer(modifier = Modifier.height(30.dp))
                Divider(Modifier.fillMaxWidth())
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Recommended by:",
                        style = MaterialTheme.typography.bodyMedium.copy(textColorDark))
                        Text("${profile.recommendUsers} / ${profile.totalUsers} users",
                            style = MaterialTheme.typography.bodyMedium.copy(textColorDark))
                    }
                    Row(modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row {
                            Box(modifier = Modifier.width(70.dp)) {
                                repeat(5) {
                                    Icon(
                                        Icons.Default.Face,
                                        null,
                                        modifier = Modifier
                                            .size(20.dp)
                                            .align(Alignment.CenterStart)
                                            .offset(x = (it * 10).dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

data class Profile(
    val title: String = "title",
    val name: String = "name surname",
    val ago: String = "1 day ago",
    val recommendUsers: Int = 40,
    val totalUsers: Int = 62,
    val amountOfCircles: Int = 15,
    val seen: Int = 1002,
    val comments: Int = 100,
    val likes: Int = 500
)