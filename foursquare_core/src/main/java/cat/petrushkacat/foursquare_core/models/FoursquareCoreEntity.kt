package cat.petrushkacat.foursquare_core.models

data class FoursquareCoreEntity(
    val categories: List<Category>,
    val chains: List<Chain>,
    val distance: Int,
    val fsq_id: String,
    val geocodes: Geocodes,
    val link: String,
    val location: Location,
    val name: String,
    val related_places: RelatedPlaces,
    val timezone: String
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