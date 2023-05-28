package cat.petrushkacat.exercises.ships

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.sync.withPermit
import kotlin.math.absoluteValue
import kotlin.random.Random

object Docks {

    private val tunnelSemaphore = Semaphore(5)
    private val bananasMutex = Mutex()
    private val clothesMutex = Mutex()
    private val breadMutex = Mutex()
    private val flowsMutex = Mutex()

    private val _shipsOutTunnel = MutableStateFlow<List<Ship>>(emptyList())
    val shipsOutTunnel = _shipsOutTunnel.asStateFlow()

    private val _shipsInTunnel = MutableStateFlow<List<Ship>>(emptyList())
    val shipsInTunnel = _shipsInTunnel.asStateFlow()

    private val _shipsWaitingDock = MutableStateFlow<List<Ship>>(emptyList())
    val shipsWaitingDock = _shipsWaitingDock.asStateFlow()

    private val _shipInBananasDock = MutableStateFlow<List<Ship>>(emptyList())
    val shipInBananasDock = _shipInBananasDock.asStateFlow()

    private val _shipInClothesDock = MutableStateFlow<List<Ship>>(emptyList())
    val shipInClothesDock = _shipInClothesDock.asStateFlow()

    private val _shipInBreadDock = MutableStateFlow<List<Ship>>(emptyList())
    val shipInBreadDock = _shipInBreadDock.asStateFlow()

    private suspend fun tunnel(ship: Ship) {
        updateInfo(ship, UpdateInfoType.OutTunnel)

        tunnelSemaphore.withPermit {
            updateInfo(ship, UpdateInfoType.InTunnel)

            Log.d("ships", ship.name + " sailed to the tunnel")
            delay(1000)
        }

        Log.d("ships", ship.name + " sails from the tunnel")
        updateInfo(ship, UpdateInfoType.WaitingDock)
        when (ship.type) {
            CargoType.Bananas -> bananasShipment(ship)
            CargoType.Clothes -> clothesShipment(ship)
            CargoType.Bread -> breadShipment(ship)
        }
    }

    private suspend fun bananasShipment(ship: Ship) = bananasMutex.withLock {
        Log.d("ships", ship.name + " started unloading its goods")
        updateInfo(ship, UpdateInfoType.BananasDockArrived)

        while (ship.load > 0) {
            delay(990)
            if (ship.load >= 10) {
                ship.load -= 10
            } else {
                ship.load = 0
            }
            updateInfo(ship, UpdateInfoType.BananasDockUnload)
            Log.d("ships", ship.name + " load: ${ship.load}")
        }

        Log.d("ships", ship.name + " unloading completed")
        updateInfo(ship, UpdateInfoType.BananasDockSailsAway)
    }

    private suspend fun clothesShipment(ship: Ship) = clothesMutex.withLock {
        Log.d("ships", ship.name + " started unloading its goods")
        updateInfo(ship, UpdateInfoType.ClothesDockArrived)
        while (ship.load > 0) {
            delay(990)
            if (ship.load >= 10) {
                ship.load -= 10
            } else {
                ship.load = 0
            }
            updateInfo(ship, UpdateInfoType.ClothesDockUnload)
            Log.d("ships", ship.name + " load: ${ship.load}")
        }

        Log.d("ships", ship.name + " unloading completed")
        updateInfo(ship, UpdateInfoType.ClothesDockSailsAway)

    }

    private suspend fun breadShipment(ship: Ship) = breadMutex.withLock {
        Log.d("ships", ship.name + " started unloading its goods")
        updateInfo(ship, UpdateInfoType.BreadDockArrived)

        while (ship.load > 0) {
            delay(990)
            if (ship.load >= 10) {
                ship.load -= 10
            } else {
                ship.load = 0
            }
            updateInfo(ship, UpdateInfoType.BreadDockUnload)

            Log.d("ships", ship.name + " load: ${ship.load}")
            Log.d("ships", _shipInBreadDock.value.toString())
        }

        Log.d("ships", ship.name + " unloading completed")
        updateInfo(ship, UpdateInfoType.BreadDockSailsAway)
    }

    private suspend fun updateInfo(ship: Ship, updateInfoType: UpdateInfoType) = flowsMutex.withLock {
        when(updateInfoType) {
            UpdateInfoType.OutTunnel -> {
                _shipsOutTunnel.value = (_shipsOutTunnel.value.toMutableList().also { it.add(ship) })
            }
            UpdateInfoType.InTunnel -> {
                _shipsOutTunnel.value = (_shipsOutTunnel.value.toMutableList().also { it.remove(ship) })
                _shipsInTunnel.value = (_shipsInTunnel.value.toMutableList().also { it.add(ship) })
            }
            UpdateInfoType.WaitingDock -> {
                _shipsInTunnel.value = (_shipsInTunnel.value.toMutableList().also { it.remove(ship) })
                _shipsWaitingDock.value = (_shipsWaitingDock.value.toMutableList().also { it.add(ship) })
            }
            UpdateInfoType.BananasDockArrived -> {
                _shipsWaitingDock.value = (_shipsWaitingDock.value.toMutableList().also { it.remove(ship) })
                _shipInBananasDock.value = (_shipInBananasDock.value.toMutableList().also { it.add(ship) })
            }
            UpdateInfoType.BananasDockUnload -> {
                _shipInBananasDock.value = listOf(ship.copy(name = "ship -1"))
                delay(10)
                _shipInBananasDock.value = listOf(ship)
            }
            UpdateInfoType.BananasDockSailsAway -> {
                _shipInBananasDock.value = (_shipInBananasDock.value.toMutableList().also { it.remove(ship) })
            }
            UpdateInfoType.ClothesDockArrived -> {
                _shipsWaitingDock.value = (_shipsWaitingDock.value.toMutableList().also { it.remove(ship) })
                _shipInClothesDock.value = (_shipInClothesDock.value.toMutableList().also { it.add(ship) })
            }
            UpdateInfoType.ClothesDockUnload -> {
                _shipInClothesDock.value = listOf(ship.copy(name = "ship -1"))
                delay(10)
                _shipInClothesDock.value = listOf(ship)
            }
            UpdateInfoType.ClothesDockSailsAway -> {
                _shipInClothesDock.value = (_shipInClothesDock.value.toMutableList().also { it.remove(ship) })
            }
            UpdateInfoType.BreadDockArrived -> {
                _shipsWaitingDock.value = (_shipsWaitingDock.value.toMutableList().also { it.remove(ship) })
                _shipInBreadDock.value = (_shipInBreadDock.value.toMutableList().also { it.add(ship) })
            }
            UpdateInfoType.BreadDockUnload -> {
                _shipInBreadDock.value = listOf(ship.copy(name = "ship -1"))
                delay(10)
                _shipInBreadDock.value = listOf(ship)
            }
            UpdateInfoType.BreadDockSailsAway -> {
                _shipInBreadDock.value = (_shipInBreadDock.value.toMutableList().also { it.remove(ship) })
            }
        }
    }

    object ShipGenerator {

        fun generateShips() {
            CoroutineScope(Dispatchers.Default).launch {
                repeat(Random.nextInt().absoluteValue % 100) {
                    launch {
                        Docks.tunnel(
                            Ship(
                                "Ship $it",
                                CargoType.values()[Random.nextInt().absoluteValue % 3],
                                Random.nextInt().absoluteValue % 99 + 1
                            )
                        )
                    }
                }
            }
        }
    }

    private enum class UpdateInfoType {
        OutTunnel,
        InTunnel,
        WaitingDock,
        BananasDockArrived,
        BananasDockUnload,
        BananasDockSailsAway,
        ClothesDockArrived,
        ClothesDockUnload,
        ClothesDockSailsAway,
        BreadDockArrived,
        BreadDockUnload,
        BreadDockSailsAway,
    }
}