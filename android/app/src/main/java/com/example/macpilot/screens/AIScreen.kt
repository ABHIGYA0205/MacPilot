package com.example.macpilot.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AutoAwesome
import androidx.compose.material.icons.rounded.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.macpilot.viewmodel.MainViewModel
@Composable
fun AIScreen(
    navController: NavController
) {
    val vm: MainViewModel = viewModel()

    var prompt by remember {
        mutableStateOf("")
    }

    var result by remember {
        mutableStateOf("")
    }

    val suggestions = listOf(
        "Open Chrome",
        "Open Brave",
        "cam",
        "scare",
        "Open VS Code",
        "Open Downloads",
        "Open Terminal",
        "bt on",
        "bt off"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        Text(
            text = "MacPilot AI",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Control your Mac using natural language.",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = prompt,
            onValueChange = {
                prompt = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Ask MacPilot")
            },
            leadingIcon = {
                Icon(Icons.Rounded.AutoAwesome, null)
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {

                vm.executeCommand(prompt) { success ->

                    result =
                        if (success)
                            "Command Executed"
                        else
                            "Failed to Execute"

                }

            }
        ) {

            Icon(Icons.Rounded.Send, null)

            Spacer(modifier = Modifier.width(8.dp))

            Text("Execute")

        }
        Spacer(modifier = Modifier.height(16.dp))

        if (result.isNotEmpty()) {

            AssistChip(
                onClick = {},
                label = {
                    Text(result)
                }
            )

            Spacer(modifier = Modifier.height(20.dp))
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Suggestions",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            items(suggestions) { suggestion ->

                ElevatedCard(
                    onClick = {
                        prompt = suggestion
                    }
                ) {

                    ListItem(
                        headlineContent = {
                            Text(suggestion)
                        }
                    )

                }

            }

        }

    }

}