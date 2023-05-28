package cat.petrushkacat.foursquare_app

import kotlinx.coroutines.flow.MutableStateFlow

object AuthStorage {
    var accessCode = ""
    val token = MutableStateFlow("")
}