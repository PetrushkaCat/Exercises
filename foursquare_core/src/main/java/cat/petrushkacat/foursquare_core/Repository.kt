package cat.petrushkacat.foursquare_core

import cat.petrushkacat.foursquare_core.models.FoursquareCoreEntity
import cat.petrushkacat.foursquare_core.models.Nearby

interface Repository {

    suspend fun getNearby(): List<Nearby>

    suspend fun getDetails(id: String): FoursquareCoreEntity

    suspend fun clearAll()
}