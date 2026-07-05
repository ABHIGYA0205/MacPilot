package com.example.macpilot.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LoadingDialog(
    message: String = "Executing..."
) {

    AlertDialog(
        onDismissRequest = { },
        confirmButton = { },
        title = {
            Text("MacPilot")
        },
        text = {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                CircularProgressIndicator()

                Spacer(modifier = Modifier.width(16.dp))

                Text(message)

            }

        }

    )

}