package com.example.macpilot.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.macpilot.components.HeaderCard
import com.example.macpilot.components.MetricCard
import com.example.macpilot.viewmodel.MainViewModel
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.BatteryChargingFull
import androidx.compose.material.icons.rounded.Memory
import androidx.compose.material.icons.rounded.DeveloperBoard
import androidx.compose.material.icons.rounded.Power
import androidx.compose.material.icons.rounded.Lock
import com.example.macpilot.components.QuickActionCard
import androidx.compose.material.icons.rounded.Code
import androidx.compose.material.icons.rounded.Terminal
import androidx.compose.material.icons.rounded.Language
import androidx.compose.material.icons.rounded.TouchApp
import com.example.macpilot.navigation.Screen
data class Metric(
    val icon: ImageVector,
    val title: String,
    val value: String
)

@Composable
fun DashboardScreen(
    navController: NavController
) {

    val vm: MainViewModel = viewModel()

    val status by vm.status.collectAsState()
    val connected by vm.connected.collectAsState()

    val metrics = listOf(

        Metric(
            Icons.Rounded.BatteryChargingFull,
            "Battery",
            "${status?.battery ?: "--"}%"
        ),

        Metric(
            Icons.Rounded.DeveloperBoard,
            "CPU",
            "${status?.cpu ?: "--"}%"
        ),

        Metric(
            Icons.Rounded.Memory,
            "RAM",
            "${status?.ram ?: "--"}%"
        ),

        Metric(
            Icons.Rounded.Power,
            "Charging",
            if (status?.charging == true) "Yes" else "No"
        )

    )

    Scaffold { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(18.dp)
                .verticalScroll(rememberScrollState())
        ){

            HeaderCard(
                device = status?.device ?: "Connecting...",
                connected = connected,
                onRefresh = {
                    vm.refreshStatus()
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "System Status",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(12.dp))

            LazyVerticalGrid(

                columns = GridCells.Fixed(2),

                modifier = Modifier.height(260.dp),

                verticalArrangement = Arrangement.spacedBy(12.dp),

                horizontalArrangement = Arrangement.spacedBy(12.dp)

            ) {

                items(metrics) { metric ->

                    MetricCard(
                        icon = metric.icon,
                        title = metric.title,
                        value = metric.value
                    )

                }

            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Quick Actions",
                style =MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(12.dp))
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.height(210.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                item {
                    QuickActionCard(
                        title = "Lock Mac",
                        icon = Icons.Rounded.Lock
                    ) {
                        vm.executeCommand("Lock Mac")
                    }
                }

                item {
                    QuickActionCard(
                        title = "VS Code",
                        icon = Icons.Rounded.Code
                    ) {
                        vm.executeCommand("Open VS Code")
                    }
                }

                item {
                    QuickActionCard(
                        title = "Terminal",
                        icon = Icons.Rounded.Terminal
                    ) {
                        vm.executeCommand("Open Terminal")
                    }
                }


                item {
                    QuickActionCard(
                        title = "Touchpad",
                        icon = Icons.Rounded.TouchApp
                    ) {
                        navController.navigate(Screen.Touchpad.route)
                    }
                }

            }


        }


    }


}