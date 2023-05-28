package cat.petrushkacat.foursquare_core.usecases

import cat.petrushkacat.foursquare_core.Repository
import javax.inject.Inject

class ClearAllUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend fun execute() {
        return repository.clearAll()
    }
}