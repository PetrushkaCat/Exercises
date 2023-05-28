package cat.petrushkacat.foursquare_core.usecases

import cat.petrushkacat.foursquare_core.Repository
import cat.petrushkacat.foursquare_core.models.Nearby
import javax.inject.Inject

class GetNearbyUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend fun execute(): List<Nearby> {
        return repository.getNearby()
    }
}