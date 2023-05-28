package cat.petrushkacat.foursquare_app

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import cat.petrushkacat.foursquare_app.screens.MainScreen
import cat.petrushkacat.foursquare_app.ui.theme.ExercisesTheme
import cat.petrushkacat.foursquare_core.Repository
import com.foursquare.android.nativeoauth.FoursquareOAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

const val CLIENT_ID = "R0PXIMYDBI0AENO042GDBBNAOX0IVLUY1OMW452CDCENZ14Z"
const val CLIENT_SECRET = "PKRHKWL4FTIMQNNIR3IPBGRX4YJYPMC0RK5BPOFOGM1ZEWGZ"
const val CLIENT_ACCESS_CODE_REQUEST = 1001
const val CLIENT_TOKEN_CODE_REQUEST = 1002
const val PREFS_TOKEN_KEY = "prefs_token_key"

@AndroidEntryPoint
class FoursquareActivity : ComponentActivity() {

    @Inject
    lateinit var repository: Repository

    val viewModel by viewModels<FoursquareMainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AuthStorage.token.value = getPreferences(MODE_PRIVATE).getString(PREFS_TOKEN_KEY, "") ?: ""
        Log.d("token", AuthStorage.token.value)
        setContent {
            ExercisesTheme() {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        try {
            when (requestCode) {
                CLIENT_ACCESS_CODE_REQUEST -> {
                    AuthStorage.accessCode =
                        FoursquareOAuth.getAuthCodeFromResult(resultCode, data).code
                    startActivityForResult(
                        FoursquareOAuth.getTokenExchangeIntent(
                            this,
                            CLIENT_ID,
                            CLIENT_SECRET,
                            AuthStorage.accessCode
                        ),
                        CLIENT_TOKEN_CODE_REQUEST
                    )
                }

                CLIENT_TOKEN_CODE_REQUEST -> {
                    AuthStorage.token.value =
                        FoursquareOAuth.getTokenFromResult(resultCode, data).accessToken
                    Log.d("foursquare", AuthStorage.token.value)
                    val sharedPreferences = this.getPreferences(MODE_PRIVATE)

                    sharedPreferences.edit().putString(PREFS_TOKEN_KEY, AuthStorage.token.value)
                        .apply()
                    viewModel.onTokenObtained()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Sign in failed", Toast.LENGTH_SHORT).show()

            AuthStorage.token.value = "null token"
            val sharedPreferences = this.getPreferences(MODE_PRIVATE)
            sharedPreferences.edit().putString(PREFS_TOKEN_KEY, AuthStorage.token.value).apply()
            viewModel.onTokenObtained()

        }
    }
}