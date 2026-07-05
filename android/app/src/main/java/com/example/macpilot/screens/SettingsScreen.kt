package com.example.macpilot.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.NetworkWifi
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.macpilot.datastore.SettingsDataStore
import kotlinx.coroutines.launch
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.macpilot.viewmodel.MainViewModel
import com.example.macpilot.model.StatusResponse
@Composable
fun SettingsScreen(
    navController: NavController
) {

    val context = LocalContext.current
    val dataStore = remember { SettingsDataStore(context) }
    val scope = rememberCoroutineScope()

    var serverUrl by remember {
        mutableStateOf("")
    }

    var saved by remember {
        mutableStateOf(false)
    }
    var connectionStatus by remember {
        mutableStateOf("")
    }

    var deviceName by remember {
        mutableStateOf("")
    }

    var battery by remember {
        mutableStateOf("")
    }

    val vm: MainViewModel = viewModel()
    LaunchedEffect(Unit) {
        dataStore.serverUrl.collect {
            serverUrl = it
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        Text(
            text = "Settings",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(24.dp))

        ElevatedCard {

            ListItem(

                headlineContent = {
                    Text("Server Address")
                },

                supportingContent = {

                    OutlinedTextField(

                        value = serverUrl,

                        onValueChange = {

                            serverUrl = it
                            saved = false

                        },

                        modifier = Modifier.fillMaxWidth(),

                        singleLine = true,

                        placeholder = {
                            Text("http://192.168.1.100:8000/")
                        },

                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Uri
                        )

                    )

                },

                leadingContent = {

                    Icon(
                        Icons.Rounded.NetworkWifi,
                        null
                    )

                }

            )

        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(

            modifier = Modifier.fillMaxWidth(),

            onClick = {

                scope.launch {

                    var url = serverUrl.trim()

                    if (!url.startsWith("http://")) {
                        url = "http://$url"
                    }

                    if (!url.endsWith("/")) {
                        url += "/"
                    }

                    dataStore.saveServerUrl(url)

                    saved = true

                }

            }

        ) {

            Text("Save")

        }
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(

            modifier = Modifier.fillMaxWidth(),

            onClick = {

                vm.testConnection { status: StatusResponse? ->

                    if (status != null) {

                        connectionStatus = "Connected"

                        deviceName = status.device

                        battery = "${status.battery}%"

                    } else {

                        connectionStatus = "Unable to Connect"

                        deviceName = ""

                        battery = ""

                    }

                }

            }

        ) {

            Text("Test Connection")

        }
        if (connectionStatus.isNotEmpty()) {

            Spacer(modifier = Modifier.height(12.dp))

            if (connectionStatus.isNotEmpty()) {

                Spacer(modifier = Modifier.height(16.dp))

                ElevatedCard(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {

                        Text(
                            text = if (connectionStatus == "Connected")
                                "🟢 Connected"
                            else
                                "🔴 Unable to Connect",
                            style = MaterialTheme.typography.titleMedium
                        )

                        if (deviceName.isNotEmpty()) {

                            Spacer(modifier = Modifier.height(8.dp))

                            Text("Device: $deviceName")
                            Text("Battery: $battery")

                        }

                    }

                }

            }

        }

        if (saved) {

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                "✓ Server address updated.",
                color = MaterialTheme.colorScheme.primary
            )

        }

        Spacer(modifier = Modifier.height(32.dp))

        ElevatedCard {

            ListItem(

                headlineContent = {

                    Text("About")

                },

                supportingContent = {

                    Text(
                        "MacPilot v1.0\nDeveloped by Abhigya Sachdeva"
                    )

                },

                leadingContent = {

                    Icon(
                        Icons.Rounded.Info,
                        null
                    )

                }

            )

        }

    }

}