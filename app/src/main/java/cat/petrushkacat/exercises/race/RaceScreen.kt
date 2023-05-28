package cat.petrushkacat.exercises.race

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun RaceScreen() {
    val vehicles by Track.vehicles.collectAsState()
    val vehicleDistances by Track.vehicleDistances.collectAsState()
    val trackLength by Track.trackLength.collectAsState()
    val scoreBoard = Track.scoreBoard
    val raceState by Track.raceState.collectAsState()

    when(raceState) {
        Track.RaceState.Configuration -> {
            RaceSettingsScreen(
                vehicles = vehicles,
                onAdd = {
                    Track.addVehicleToTheRun(it)
                },
                onStart = {
                    Track.startRace(it)
                }
            )
        }
        Track.RaceState.Racing -> {
            RacingScreen(vehicleDistances = vehicleDistances, trackLength = trackLength)
        }

        Track.RaceState.Finished -> {
            FinishedScreen(scoreBoard = scoreBoard, onStartAgain = {
              Track.startRace(trackLength)
            })
        }
    }
}

@Composable
fun FinishedScreen(
    scoreBoard: List<Score>,
    onStartAgain: () -> Unit
) {
    Column() {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            items(scoreBoard.size) {
                val place = it + 1
                Column(modifier = Modifier.fillMaxWidth().height(50.dp)) {
                    when (val vehicle = scoreBoard[it].vehicle) {
                        is Car -> {
                            Text("$place place: " + vehicle.toString() + " breaks: ${scoreBoard[it].breaks}")
                        }

                        is Bike -> {
                            Text("$place place: " + vehicle.toString() + " breaks: ${scoreBoard[it].breaks}")
                        }

                        is Truck -> {
                            Text("$place place: " + vehicle.toString() + " breaks: ${scoreBoard[it].breaks}")
                        }
                    }
                }
            }
        }
        Column(modifier = Modifier
            .height(50.dp)
            .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = { onStartAgain() }) {
                Text("Start again")
            }
        }
    }
}

@Composable
fun RacingScreen(
    vehicleDistances: List<VehicleDistance>,
    trackLength: Int
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    "Track length: $trackLength m",
                    modifier = Modifier.fillMaxWidth(),
                    style = TextStyle(fontSize = 20.sp),
                    textAlign = TextAlign.Center
                )
            }
        }
        items(vehicleDistances.size) {
            RaceItem(vehicleDistance = vehicleDistances[it], trackLength = trackLength)
        }
    }
}

@Composable
fun RaceSettingsScreen(
    vehicles: List<Vehicle>,
    onAdd: (Vehicle) -> Unit,
    onStart: (Int) -> Unit
) {
    val raceLength = rememberSaveable { mutableStateOf("") }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AddCar(
            onAdd = {
                onAdd(it)
            },
            newVehicleId = vehicles.size + 1
        )
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .weight(1f)
        ) {
            items(vehicles.size) {
                when(val vehicle = vehicles[it]) {
                    is Car -> { Text(vehicle.toString()) }
                    is Bike -> { Text(vehicle.toString()) }
                    is Truck -> { Text(vehicle.toString()) }
                }
            }
        }
        Column(modifier = Modifier.height(100.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = raceLength.value.toString(),
                onValueChange = {
                    val number = it.toIntOrNull()
                    raceLength.value = number?.toString() ?: ""
                },
                label = { Text("Race length, m") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword)
            )
            Button(
                onClick = { onStart(raceLength.value.toInt()) },
                content = {
                    Text("Start")
                },
                enabled = raceLength.value.isNotEmpty() && vehicles.size >= 2
            )
        }
    }
}

@Composable
fun AddCar(
    newVehicleId: Int,
    onAdd: (Vehicle) -> Unit
) {
    val isCarSelected = rememberSaveable { mutableStateOf(true) }
    val isBikeSelected = rememberSaveable { mutableStateOf(false) }
    val isTruckSelected = rememberSaveable { mutableStateOf(false) }

    val speed = rememberSaveable { mutableStateOf("") }
    val breakChance = rememberSaveable { mutableStateOf("") }

    val passengersCount = rememberSaveable { mutableStateOf("") }
    val hasSideCar = rememberSaveable { mutableStateOf(false) }
    val loadWeight = rememberSaveable { mutableStateOf("") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row() {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                RadioButton(selected = isCarSelected.value, onClick = {
                    isCarSelected.value = true
                    isBikeSelected.value = false
                    isTruckSelected.value = false
                })
                Text("Car")
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                RadioButton(selected = isBikeSelected.value, onClick = {
                    isCarSelected.value = false
                    isBikeSelected.value = true
                    isTruckSelected.value = false
                })
                Text("Bike")
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                RadioButton(selected = isTruckSelected.value, onClick = {
                    isCarSelected.value = false
                    isBikeSelected.value = false
                    isTruckSelected.value = true
                })
                Text("Truck")
            }
        }
        Spacer(Modifier.height(20.dp))
        TextField(
            value = speed.value.toString(),
            onValueChange = {
                val number = it.toIntOrNull()
                speed.value = number?.toString() ?: ""
        },
            label = { Text("Speed, m/s")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword)
        )
        TextField(
            value = breakChance.value.toString(),
            onValueChange = {
                var number = it.toIntOrNull()
                if(number != null) {
                    if(number > 100) {
                        number = 100
                    }
                }
                breakChance.value = number?.toString() ?: ""
            },
            label = { Text("Break chance, %")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword)
        )
        if(!isBikeSelected.value) {
            if(isCarSelected.value) {
                TextField(
                    value = passengersCount.value.toString(),
                    onValueChange = {
                        val number = it.toIntOrNull()
                        passengersCount.value = number?.toString() ?: ""
                    },
                    label = { Text("Passengers count") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword)
                )
            } else {
                TextField(
                    value = loadWeight.value.toString(),
                    onValueChange = {
                        val number = it.toIntOrNull()
                        loadWeight.value = number?.toString() ?: ""
                    },
                    label = { Text("Load weight, kg") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword)
                )
            }
        } else {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Checkbox(checked = hasSideCar.value, onCheckedChange = {
                    hasSideCar.value = it
                })
                Text("Has sidecar")
            }
        }
        Button(onClick = {
            val speedInt = if(speed.value.isNotEmpty()) speed.value.toInt() else 0
            val breakChanceInt = if(breakChance.value.isNotEmpty()) breakChance.value.toInt() else 0

            val vehicle: Vehicle = if(isCarSelected.value) {
                val passengersCountInt = if(passengersCount.value.isNotEmpty()) passengersCount.value.toInt() else 0
                Car(
                    newVehicleId,
                    speedInt,
                    breakChanceInt,
                    passengersCountInt
                )
            } else if(isBikeSelected.value) {
                Bike(
                    newVehicleId,
                    speedInt,
                    breakChanceInt,
                    hasSideCar.value
                )
            } else {
                val loadWeightInt = if(loadWeight.value.isNotEmpty()) loadWeight.value.toInt() else 0

                Truck(
                    newVehicleId,
                    speedInt,
                    breakChanceInt,
                    loadWeightInt
                )
            }
            onAdd(vehicle)
        },
            enabled = (speed.value.isNotEmpty())
        ) {
            Text("Add")
        }
    }
}

@Composable
fun RaceItem(vehicleDistance: VehicleDistance, trackLength: Int) {
    val vehicleType = when(vehicleDistance.vehicle) {
        is Car -> "car"
        is Truck -> "truck"
        is Bike -> "bike"
        else -> ""
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
    ) {
        Text("Vehicle ${vehicleDistance.vehicle.id} ($vehicleType) has passed ${vehicleDistance.distance} m")
        CircularProgressIndicator(progress = vehicleDistance.distance.toFloat() / trackLength, trackColor = Color.LightGray)
    }
}