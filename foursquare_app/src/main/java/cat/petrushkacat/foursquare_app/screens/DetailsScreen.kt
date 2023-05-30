package cat.petrushkacat.foursquare_app.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import cat.petrushkacat.foursquare_app.FoursquareMainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    onBackClicked: () -> Unit
) {
    val viewModel = hiltViewModel<FoursquareMainViewModel>()
    val details by viewModel.details.collectAsStateWithLifecycle()

    if (details!= null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            TopAppBar(title = {}, navigationIcon = {
                Icon(
                    Icons.Default.ArrowBackIos,
                    "Back",
                    modifier = Modifier
                        .clickable {
                            onBackClicked()
                        }
                        .size(48.dp)
                        .padding(10.dp)
                )
            })
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    details?.name ?: "",
                    modifier = Modifier.padding(horizontal = 16.dp),
                    style = MaterialTheme.typography.headlineLarge
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    details?.categories?.get(0)?.name ?: "",
                    modifier = Modifier.padding(horizontal = 16.dp),
                    style = MaterialTheme.typography.bodyLarge.copy(color = Color.Gray)
                )
                Spacer(modifier = Modifier.height(20.dp))
                Divider(Modifier.fillMaxWidth())
                InfoItem(
                    name = "Distance",
                    value = details?.distance.toString() + " m"
                )
                InfoItem(
                    name = "Location",
                    value = details?.location?.address ?: ""
                )
                InfoItem(name = "id", value = details?.fsq_id ?: "")
                InfoItem(name = "latitude", value = details?.geocodes?.main?.latitude.toString())
                InfoItem(name = "longitude", details?.geocodes?.main?.longitude.toString())
            }
        }
    } else {
        Column(modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Loading...")
        }
    }
}

@Composable
fun InfoItem(name: String, value: String) {

    Column(
        modifier = Modifier
            .height(50.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Row(
            modifier = Modifier
                .height(49.dp)
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(name, style = TextStyle(color = Color.Gray))
            Text(value, style = TextStyle(color = Color.Black))
        }
        Divider(Modifier.fillMaxWidth())
    }
}