package cat.petrushkacat.foursquare_core.usecases

import cat.petrushkacat.foursquare_core.Repository
import cat.petrushkacat.foursquare_core.models.FoursquareCoreEntity
import javax.inject.Inject

class GetDetailsUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend fun execute(id: String): FoursquareCoreEntity {
        return repository.getDetails(id)
    }
}