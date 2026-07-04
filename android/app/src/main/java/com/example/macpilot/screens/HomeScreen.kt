package com.example.macpilot.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.macpilot.model.StatusResponse

@Composable
fun HomeScreen(
    status: StatusResponse?,
    onLockClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            "🖥 MacPilot",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(20.dp))

        status?.let {

            Text("💻 ${it.device}")
            Text("🔋 Battery: ${it.battery}%")
            Text("⚡ CPU: ${it.cpu}%")
            Text("🧠 RAM: ${it.ram}%")

        }

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = onLockClick
        ) {
            Text("🔒 Lock Mac")
        }
    }
}