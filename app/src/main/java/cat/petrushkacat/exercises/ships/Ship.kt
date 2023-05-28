package cat.petrushkacat.exercises.ships

data class Ship(
    val name: String,
    val type: CargoType,
    var load: Int
)

enum class CargoType {
    Bananas,
    Clothes,
    Bread
}