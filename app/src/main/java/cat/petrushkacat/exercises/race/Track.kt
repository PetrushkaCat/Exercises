package cat.petrushkacat.exercises.race

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.math.absoluteValue
import kotlin.random.Random

object Track {
    private val _vehicles: MutableStateFlow<List<Vehicle>> = MutableStateFlow(mutableListOf())
    val vehicles = _vehicles.asStateFlow()

    private val _vehicleDistances: MutableStateFlow<List<VehicleDistance>> = MutableStateFlow(emptyList())
    val vehicleDistances = _vehicleDistances.asStateFlow()

    private val _trackLength = MutableStateFlow(0)
    val trackLength = _trackLength.asStateFlow()

    private val _scoreBoard: MutableList<Score> = mutableListOf()
    val scoreBoard: List<Score> = _scoreBoard

    private val _raceState: MutableStateFlow<RaceState> = MutableStateFlow(RaceState.Configuration)
    val raceState = _raceState.asStateFlow()

    private val mutex = Mutex()

    fun addVehicleToTheRun(vehicle: Vehicle) {
        _vehicles.value = vehicles.value.toMutableList().also { it.add(vehicle) }
        Log.d("race", vehicles.value.toString())
    }

    fun startRace(trackLength: Int) {
        _scoreBoard.clear()
        _raceState.value = RaceState.Racing
        _trackLength.value = trackLength
        _vehicleDistances.value = vehicles.value.map {
            VehicleDistance(it, 0)
        }
        vehicleDistances.value.forEach {
            CoroutineScope(Dispatchers.Default).launch {
                vehicleRide(it)
            }
        }
    }

    private suspend fun vehicleRide(vehicleDistance: VehicleDistance) {
        var breaks = 0
        while (vehicleDistance.distance < trackLength.value) {
            if (Random.nextInt().absoluteValue % 100 <= vehicleDistance.vehicle.breakChance) {
                Log.d("race", vehicleDistance.vehicle.toString() + " broken")
                delay(200)
                breaks++
            }

            if(vehicleDistance.distance + vehicleDistance.vehicle.speed <= trackLength.value) {
                delay(1000)
                vehicleDistance.distance += vehicleDistance.vehicle.speed
            } else {
                delay(((trackLength.value - vehicleDistance.distance).toDouble() / vehicleDistance.vehicle.speed * 1000).toLong())
                vehicleDistance.distance = trackLength.value
            }

            updateInfo(vehicleDistance)
            Log.d("race", vehicleDistance.vehicle.toString() + " ${vehicleDistance.distance} passed")
        }
        _scoreBoard.add(Score(vehicleDistance.vehicle, breaks))

        if(scoreBoard.size == vehicles.value.size) {
            _raceState.value = RaceState.Finished
        }
        Log.d("race", vehicleDistance.vehicle.toString() + " finished")
    }

    private suspend fun updateInfo(vehicleDistance: VehicleDistance) = mutex.withLock {
        val temp = vehicleDistances.value.toMutableList()
        val index = temp.indexOf(vehicleDistance)
        temp.removeAt(index)
        temp.add(index, VehicleDistance(Car(1, 1, 1, 1), 1))
        _vehicleDistances.value = temp
        delay(5) // flows don't want to update values if there is var changes
        temp.removeAt(index)
        temp.add(index, vehicleDistance)
        _vehicleDistances.value = temp

    }

    sealed interface RaceState {
        object Configuration: RaceState
        object Racing: RaceState
        object Finished: RaceState
    }

}

data class Score(
    val vehicle: Vehicle,
    val breaks: Int
)

class VehicleDistance(
    val vehicle: Vehicle,
    var distance: Int
) {
    override fun toString(): String {
        return "VehicleDistance(vehicle=$vehicle, distance=$distance)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as VehicleDistance

        if (vehicle != other.vehicle) return false
        if (distance != other.distance) return false

        return true
    }

    override fun hashCode(): Int {
        var result = vehicle.hashCode()
        result = 31 * result + distance
        return result
    }
}