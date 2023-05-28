package cat.petrushkacat.exercises.ships

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun ShipsScreen() {

    val shipsOutTunnel by Docks.shipsOutTunnel.collectAsState()
    val shipsInTunnel by Docks.shipsInTunnel.collectAsState()
    val shipsWaitingDock by Docks.shipsWaitingDock.collectAsState()
    val shipsInBananasDock by Docks.shipInBananasDock.collectAsState()
    val shipsInClothesDock by Docks.shipInClothesDock.collectAsState()
    val shipsInBreadDock by Docks.shipInBreadDock.collectAsState()

    Column(modifier = Modifier
        .padding(16.dp)
        .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text("Ships out Tunnel: $shipsOutTunnel")
        Divider(Modifier.fillMaxWidth())
        Text("Ships in Tunnel: $shipsInTunnel")
        Divider(Modifier.fillMaxWidth())
        Text("Ships waiting dock: $shipsWaitingDock")
        Divider(Modifier.fillMaxWidth())
        Text("The ship in bananas dock: $shipsInBananasDock")
        Divider(Modifier.fillMaxWidth())
        Text("The ship in clothes dock: $shipsInClothesDock")
        Divider(Modifier.fillMaxWidth())
        Text("The ship in bread dock: $shipsInBreadDock")
    }

    LaunchedEffect(key1 = true) {
        Docks.ShipGenerator.generateShips()
    }
}