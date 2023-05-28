package cat.petrushkacat.exercises.patterns

class Factory {

    fun createDelivery(country: Country): Delivery {
        return when(country) {
            is Country.Russia -> {
                Delivery.Rail(
                    country.companyName,
                    country.weight,
                    country.size,
                    country.railDeliveryType
                )
            }
            is Country.Belarus -> {
                Delivery.Auto(country.companyName, country.weight, country.size)
            }
            is Country.USA -> {
                Delivery.Water(
                    country.companyName,
                    country.weight,
                    country.size,
                    country.waterDeliveryType
                )
            }
            is Country.Germany -> {
                Delivery.Avia(country.companyName, country.weight, country.size, country.aviaType)
            }
        }
    }

    open class Delivery(val companyName: String, val weight: Int, val size: Int) {
        class Auto(companyName: String, weight: Int, size: Int): Delivery(companyName, weight, size)
        class Avia(companyName: String, weight: Int, size: Int, val aviaType: AviaType): Delivery(companyName, weight, size)
        class Water(companyName: String, weight: Int, size: Int, val waterDeliveryType: WaterDeliveryType): Delivery(companyName, weight, size)
        class Rail(companyName: String, weight: Int, size: Int, val railDeliveryType: RailDeliveryType): Delivery(companyName, weight, size)
    }
}

sealed class Country(val companyName: String, val weight: Int, val size: Int) {
    class Belarus(companyName: String, weight: Int, size: Int): Country(companyName, weight, size)
    class Germany(companyName: String, weight: Int, size: Int, val aviaType: AviaType): Country(companyName, weight, size)
    class USA(companyName: String, weight: Int, size: Int, val waterDeliveryType: WaterDeliveryType): Country(companyName, weight, size)
    class Russia(companyName: String, weight: Int, size: Int, val railDeliveryType: RailDeliveryType): Country(companyName, weight, size)
}

enum class AviaDeliveryType {
    City,
    Cities,
    Countries
}

enum class AviaTransport {
    Passenger,
    Cargo
}

data class AviaType (
    val deliveryType: AviaDeliveryType,
    val transport: AviaTransport
)

enum class WaterDeliveryType {
    River,
    Sea
}

data class RailDeliveryType(
    val wheelSize: Int,
    val hasBallast: Boolean
)