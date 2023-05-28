package cat.petrushkacat

import cat.petrushkacat.foursquare_core.Repository
import cat.petrushkacat.foursquare_core.models.FoursquareCoreEntity
import cat.petrushkacat.foursquare_core.models.Nearby
import cat.petrushkacat.foursquare_data.api.Api
import cat.petrushkacat.foursquare_data.db.FoursquareDao
import cat.petrushkacat.foursquare_data.models.FoursquareDataEntity
import com.google.gson.Gson
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val foursquareDao: FoursquareDao,
    private val api: Api
) : Repository {

    override suspend fun getNearby(): List<Nearby> {
        val result: MutableList<Nearby> = mutableListOf()

        return try {
            val entities = api.getPlaces("")
            foursquareDao.save(entities)
            entities.forEach {
                result.add(mapToCoreNearby(it))
            }
            result
        } catch (e: Exception) {
            e.printStackTrace()
            val cache = foursquareDao.getAll()
            cache.forEach {
                result.add(mapToCoreNearby(it))
            }
            result
        }
    }

    override suspend fun getDetails(id: String): FoursquareCoreEntity {
        val entity = foursquareDao.getDetails(id)
        return mapToCoreDetails(entity)
    }


    override suspend fun clearAll() {
        foursquareDao.clearAll()
    }

    private fun mapToCoreNearby(foursquareEntity: FoursquareDataEntity): Nearby {
        return Nearby(
            id = foursquareEntity.fsq_id,
            name = foursquareEntity.name,
            category = foursquareEntity.categories[0].name,
            picture = foursquareEntity.categories[0].icon.prefix + "32" + foursquareEntity.categories[0].icon.suffix
        )
    }

    private fun mapToCoreDetails(foursquareEntity: FoursquareDataEntity): FoursquareCoreEntity {
        val json = Gson().toJson(foursquareEntity)
        return Gson().fromJson(json, FoursquareCoreEntity::class.java)
    }
}