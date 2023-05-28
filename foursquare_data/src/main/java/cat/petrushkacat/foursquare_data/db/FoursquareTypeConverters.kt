package cat.petrushkacat.foursquare_data.db

import androidx.room.TypeConverter
import cat.petrushkacat.foursquare_data.models.Category
import cat.petrushkacat.foursquare_data.models.Chain
import cat.petrushkacat.foursquare_data.models.Geocodes
import cat.petrushkacat.foursquare_data.models.Location
import cat.petrushkacat.foursquare_data.models.RelatedPlaces
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class FoursquareTypeConverters {

    @TypeConverter
    fun categoriesToJson(entity: List<Category>): String {
        return Gson().toJson(entity)
    }

    @TypeConverter
    fun categoriesFromJson(json: String): List<Category> {
        val type = object: TypeToken<List<Category>>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun chainsToJson(entity: List<Chain>): String {
        return Gson().toJson(entity)
    }

    @TypeConverter
    fun chainsFromJson(json: String): List<Chain> {
        val type = object: TypeToken<List<Chain>>() {}.type
        return Gson().fromJson(json, type)
    }
    
    @TypeConverter
    fun geocodesToJson(entity: Geocodes): String {
        return Gson().toJson(entity)
    }

    @TypeConverter
    fun geocodesFromJson(json: String): Geocodes {
        return Gson().fromJson(json, Geocodes::class.java)
    }

    @TypeConverter
    fun locationToJson(entity: Location): String {
        return Gson().toJson(entity)
    }

    @TypeConverter
    fun locationFromJson(json: String): Location {
        return Gson().fromJson(json, Location::class.java)
    }

    @TypeConverter
    fun relatedPlacesToJson(entity: RelatedPlaces): String {
        return Gson().toJson(entity)
    }

    @TypeConverter
    fun relatedPlacesFromJson(json: String): RelatedPlaces {
        return Gson().fromJson(json, RelatedPlaces::class.java)
    }
}