package cat.petrushkacat.foursquare_app.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import cat.petrushkacat.foursquare_app.FoursquareMainViewModel
import cat.petrushkacat.foursquare_core.models.Nearby
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun NearbyScreen(
    onDetailsClicked: () -> Unit,
    onLogout: () -> Unit
) {
    val viewModel = hiltViewModel<FoursquareMainViewModel>()
    val nearby by viewModel.nearby.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()

    Column {
        TopAppBar(
            title = { Text("Nearby") },
            actions = {
                Icon(
                    Icons.Default.Refresh,
                    "Logout",
                    modifier = Modifier
                        .clickable {
                            viewModel.refresh()
                        }
                        .size(48.dp)
                        .padding(10.dp)
                )
                Icon(
                    Icons.Default.Logout,
                    "Logout",
                    modifier = Modifier
                        .clickable {
                            onLogout()
                        }
                        .size(48.dp)
                        .padding(10.dp)
                )

            }
        )
        LazyColumn(modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(8.dp)) {
            items(nearby.size) {
                NearbyItem(
                    nearby = nearby[it],
                    onClick = {
                        viewModel.onDetailsClicked(it)
                        onDetailsClicked()
                    })
            }
        }
    }

    if (nearby.isEmpty() && !isLoading) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Nothing was found")
        }
    } else {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            PullRefreshIndicator(refreshing = isLoading, state = rememberPullRefreshState(
                refreshing = isLoading,
                onRefresh = { }))
        }
    }
}

@Composable
fun NearbyItem(
    nearby: Nearby,
    onClick: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .clickable {
                onClick(nearby.id)
            }
            .height(72.dp)
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(9f)) {
            AsyncImage(
                model = nearby.picture,
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape),
                colorFilter = ColorFilter.tint(color = Color.Black)
            )
            Spacer(Modifier.width(16.dp))
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(nearby.name)
                Text(
                    nearby.category,
                    style = TextStyle(color = Color.Gray, fontSize = 14.sp),
                    maxLines = 2
                )
            }
        }
    }
}