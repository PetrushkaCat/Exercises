package cat.petrushkacat.foursquare_data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import cat.petrushkacat.foursquare_data.models.FoursquareDataEntity

@Database(version = 1,
    entities = [FoursquareDataEntity::class]
)
@TypeConverters(value = [FoursquareTypeConverters::class])
abstract class FoursquareDB: RoomDatabase() {
    abstract fun foursquareDao(): FoursquareDao
}