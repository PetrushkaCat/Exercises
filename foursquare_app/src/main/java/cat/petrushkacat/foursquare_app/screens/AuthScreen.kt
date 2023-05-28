package cat.petrushkacat.foursquare_app.screens

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import cat.petrushkacat.foursquare_app.AuthStorage
import cat.petrushkacat.foursquare_app.CLIENT_ACCESS_CODE_REQUEST
import cat.petrushkacat.foursquare_app.CLIENT_ID
import com.foursquare.android.nativeoauth.FoursquareOAuth

@Composable
fun AuthScreen(
    onTokenObtained: () -> Unit
) {
    val context = LocalContext.current
    val token = AuthStorage.token.collectAsState()
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Button(onClick = {
            (context as Activity).startActivityForResult(
                FoursquareOAuth.getConnectIntent(context, CLIENT_ID),
                CLIENT_ACCESS_CODE_REQUEST
            )
        }) {
            Text("Sign in")
        }
    }

    LaunchedEffect(key1 = token) {
            AuthStorage.token.collect {
                if(it.isNotEmpty()) {
                    onTokenObtained()
            }
        }
    }

}