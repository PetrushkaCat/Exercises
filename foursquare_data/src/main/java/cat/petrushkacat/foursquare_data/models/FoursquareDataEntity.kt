package cat.petrushkacat.foursquare_data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

data class FoursquareResponse(
    val context: Context,
    val results: List<FoursquareDataEntity>
)

@Entity
data class FoursquareDataEntity(
    val categories: List<Category>,
    val chains: List<Chain>,
    val distance: Int,
    @PrimaryKey val fsq_id: String,
    val geocodes: Geocodes,
    val link: String,
    val location: Location,
    val name: String,
    val related_places: RelatedPlaces,
    val timezone: String
)

data class Context(
    val geo_bounds: GeoBounds
)

data class GeoBounds(
    val circle: Circle
)

data class Circle(
    val center: Center,
    val radius: Int
)

data class Center(
    val latitude: Double,
    val longitude: Double
)

data class Category(
    val icon: Icon,
    val id: Int,
    val name: String
)

data class Chain(
    val id: String,
    val name: String
)

data class Geocodes(
    val drop_off: DropOff,
    val main: Main,
    val roof: Roof
)

data class Location(
    val address: String,
    val address_extended: String,
    val census_block: String,
    val country: String,
    val cross_street: String,
    val dma: String,
    val formatted_address: String,
    val locality: String,
    val postcode: String,
    val region: String
)

data class RelatedPlaces(
    val parent: Parent
)

data class Icon(
    val prefix: String,
    val suffix: String
)

data class DropOff(
    val latitude: Double,
    val longitude: Double
)

data class Main(
    val latitude: Double,
    val longitude: Double
)

data class Roof(
    val latitude: Double,
    val longitude: Double
)

data class Parent(
    val fsq_id: String,
    val name: String
)