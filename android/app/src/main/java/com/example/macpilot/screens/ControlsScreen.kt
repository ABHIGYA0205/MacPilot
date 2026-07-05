package com.example.macpilot.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Bedtime
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.PowerSettingsNew
import androidx.compose.material.icons.rounded.RestartAlt
import androidx.compose.material.icons.rounded.VolumeDown
import androidx.compose.material.icons.rounded.VolumeUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.macpilot.viewmodel.MainViewModel

data class ControlAction(
    val title: String,
    val command: String,
    val icon: ImageVector
)

@Composable
fun FilesScreen(
    navController: NavController
) {

    val vm: MainViewModel = viewModel()

    var showConfirm by remember {
        mutableStateOf(false)
    }

    var pendingAction by remember {
        mutableStateOf<ControlAction?>(null)
    }
    val volume by vm.volume.collectAsState()
    LaunchedEffect(Unit) {

        vm.loadVolume()

    }

    val actions = listOf(

        ControlAction(
            "Restart",
            "Restart",
            Icons.Rounded.RestartAlt
        ),

        ControlAction(
            "Shutdown",
            "Shutdown",
            Icons.Rounded.PowerSettingsNew
        ),

        ControlAction(
            "Lock Mac",
            "Lock Mac",
            Icons.Rounded.Lock
        ),

        ControlAction(
            "Sleep",
            "Sleep",
            Icons.Rounded.Bedtime
        )

    )
    Spacer(modifier = Modifier.height(20.dp))


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(18.dp)
    ) {

        Text(
            text = "Controls",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Volume",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(8.dp))

        var slider by remember {
            mutableFloatStateOf(volume.toFloat())
        }

        LaunchedEffect(volume) {
            slider = volume.toFloat()
        }

        Slider(

            value = slider,

            onValueChange = {

                slider = it

            },

            onValueChangeFinished = {

                vm.setVolume(slider.toInt())

            },

            valueRange = 0f..100f

        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text("0%")

            Text(
                "${slider.toInt()}%",
                fontWeight = FontWeight.Bold
            )

            Text("100%")

        }

        Spacer(modifier = Modifier.height(24.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.height(270.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {

            items(actions) { action ->

                Card(
                    onClick = {

                        if (
                            action.command.equals("Restart", true) ||
                            action.command.equals("Shutdown", true)
                        ) {

                            pendingAction = action
                            showConfirm = true

                        } else {

                            vm.executeCommand(action.command)

                        }

                    },
                    modifier = Modifier.height(120.dp),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 3.dp
                    )
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.Start
                    ) {

                        Icon(
                            imageVector = action.icon,
                            contentDescription = null,
                            modifier = Modifier.size(32.dp)
                        )

                        Text(
                            text = action.title,
                            style = MaterialTheme.typography.titleMedium
                        )

                    }

                }

            }

        }

    }

    if (showConfirm && pendingAction != null) {

        val actionText =
            if (pendingAction!!.command == "Shutdown")
                "shut down"
            else
                "restart"

        AlertDialog(

            onDismissRequest = {
                showConfirm = false
            },

            title = {
                Text("Confirm Action")
            },

            text = {
                Text(
                    "Are you sure you want to $actionText your Mac?"
                )
            },

            confirmButton = {

                Button(
                    onClick = {

                        vm.executeCommand(
                            pendingAction!!.command
                        )

                        showConfirm = false

                    }
                ) {

                    Text("Yes")

                }

            },

            dismissButton = {

                OutlinedButton(
                    onClick = {

                        showConfirm = false

                    }
                ) {

                    Text("Cancel")

                }

            }

        )

    }

}