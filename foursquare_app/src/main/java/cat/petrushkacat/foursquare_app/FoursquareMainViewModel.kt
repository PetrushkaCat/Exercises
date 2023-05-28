package cat.petrushkacat.foursquare_app

import android.util.Log
import androidx.lifecycle.ViewModel
import cat.petrushkacat.foursquare_core.models.FoursquareCoreEntity
import cat.petrushkacat.foursquare_core.models.Nearby
import cat.petrushkacat.foursquare_core.usecases.ClearAllUseCase
import cat.petrushkacat.foursquare_core.usecases.GetDetailsUseCase
import cat.petrushkacat.foursquare_core.usecases.GetNearbyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoursquareMainViewModel @Inject constructor(
    private val getNearbyUseCase: GetNearbyUseCase,
    private val getDetailsUseCase: GetDetailsUseCase,
    private val clearAllUseCase: ClearAllUseCase
): ViewModel() {

    private val dispatchersIO: CoroutineDispatcher = Dispatchers.IO

    private val _nearby = MutableStateFlow<List<Nearby>>(emptyList())
    val nearby = _nearby.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _details = MutableStateFlow<FoursquareCoreEntity?>(null)
    val details = _details.asStateFlow()

    init {
        if(AuthStorage.token.value != "") {
            CoroutineScope(dispatchersIO).launch {
                _isLoading.value = true
                _nearby.value = getNearbyUseCase.execute()
                _isLoading.value = false
            }
        }
    }

    fun onTokenObtained() {
        CoroutineScope(dispatchersIO).launch {
            _isLoading.value = true
            _nearby.value = getNearbyUseCase.execute()
            _isLoading.value = false
        }
    }

    fun onDetailsClicked(id: String) {
        CoroutineScope(dispatchersIO).launch {
            _details.value = getDetailsUseCase.execute(id)
        }
    }

    fun refresh() {
        if(!isLoading.value) {
            CoroutineScope(dispatchersIO).launch {
                _isLoading.value = true
                _nearby.value = getNearbyUseCase.execute()
                _isLoading.value = false
            }
        }
    }

    fun onLogout() {
        CoroutineScope(dispatchersIO).launch {
            AuthStorage.token.value = ""
            clearAllUseCase.execute()
            Log.d("logout", AuthStorage.token.value)
        }
    }
}