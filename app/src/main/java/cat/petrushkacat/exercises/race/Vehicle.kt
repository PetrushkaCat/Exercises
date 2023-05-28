package cat.petrushkacat.exercises.race

abstract class Vehicle(
    val id: Int,
    val speed: Int,
    val breakChance: Int,
) {
    override fun toString(): String {
        return "Vehicle(id=$id, speed=$speed m/s, breakChance=$breakChance %)"
    }
}

class Car(
    id: Int,
    speed: Int,
    breakChance: Int,
    val passengersCount: Int
): Vehicle(id, speed, breakChance) {

    override fun toString(): String {
        return "Car(id=$id, speed=$speed m/s, breakChance=$breakChance %, passengersCount=$passengersCount)"
    }
}

class Truck(
    id: Int,
    speed: Int,
    breakChance: Int,
    val loadWeight: Int
): Vehicle(id, speed, breakChance) {
    override fun toString(): String {
        return "Truck(id=$id, speed=$speed m/s, breakChance=$breakChance %, loadWeight=$loadWeight kg)"
    }
}

class Bike(
    id: Int,
    speed: Int,
    breakChance: Int,
    val hasSidecar: Boolean
): Vehicle(id, speed, breakChance) {
    override fun toString(): String {
        return "Bike(id=$id, speed=$speed m/s, breakChance=$breakChance %, hasSidecar=$hasSidecar)"
    }
}

