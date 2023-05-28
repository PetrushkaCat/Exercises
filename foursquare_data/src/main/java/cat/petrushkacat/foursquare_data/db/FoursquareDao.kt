package cat.petrushkacat.foursquare_data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import cat.petrushkacat.foursquare_data.models.FoursquareDataEntity

@Dao
interface FoursquareDao {

    @Insert
    fun save(places: List<FoursquareDataEntity>)

    @Query("SELECT * FROM FoursquareDataEntity")
    fun getAll(): List<FoursquareDataEntity>

    @Query("SELECT * FROM FoursquareDataEntity WHERE fsq_id = :id")
    fun getDetails(id: String): FoursquareDataEntity

    @Query("DELETE FROM FoursquareDataEntity")
    fun clearAll()

}