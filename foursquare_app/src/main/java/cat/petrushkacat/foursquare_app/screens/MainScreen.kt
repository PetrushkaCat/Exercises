package cat.petrushkacat.foursquare_app.screens

import android.app.Activity
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cat.petrushkacat.foursquare_app.AuthStorage
import cat.petrushkacat.foursquare_app.FoursquareMainViewModel
import cat.petrushkacat.foursquare_app.PREFS_TOKEN_KEY

sealed class FoursquareScreen(val route: String) {
    class Nearby: FoursquareScreen("Nearly")
    class Details: FoursquareScreen("Details")
    class Auth: FoursquareScreen("Auth")
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val token by AuthStorage.token.collectAsState()
    val viewModel = viewModel<FoursquareMainViewModel>()
    val context = LocalContext.current
    val viewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
        "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
    }

    val startDestination = if(token != "") FoursquareScreen.Nearby().route else FoursquareScreen.Auth().route

    NavHost(navController = navController, startDestination = startDestination) {
        composable(FoursquareScreen.Nearby().route) {
            CompositionLocalProvider(LocalViewModelStoreOwner provides viewModelStoreOwner ) {
                NearbyScreen(
                    onDetailsClicked = {
                        navController.navigate(FoursquareScreen.Details().route, NavOptions.Builder().setLaunchSingleTop(true).build())
                    },
                    onLogout = {
                        navController.navigate(FoursquareScreen.Auth().route) {
                            viewModel.onLogout()
                            val sharedPreferences = (context as Activity).getPreferences(ComponentActivity.MODE_PRIVATE)
                            sharedPreferences.edit().putString(PREFS_TOKEN_KEY, "").apply()
                            popUpTo(0)
                        }
                    }
                )
            }
        }
        composable(FoursquareScreen.Auth().route) {
            CompositionLocalProvider(LocalViewModelStoreOwner provides viewModelStoreOwner ) {
                AuthScreen(onTokenObtained = {
                    navController.navigate(FoursquareScreen.Nearby().route) {
                        popUpTo(0)
                    }
                })
            }
        }
        composable(FoursquareScreen.Details().route) {
            CompositionLocalProvider(LocalViewModelStoreOwner provides viewModelStoreOwner ) {
                DetailsScreen(onBackClicked = {
                    navController.popBackStack()
                })
            }
        }
    }
}