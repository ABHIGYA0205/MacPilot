package com.example.macpilot.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Keyboard
import androidx.compose.material.icons.rounded.Mouse
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.macpilot.viewmodel.MainViewModel
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import com.example.macpilot.network.TouchpadSocket
import com.example.macpilot.datastore.SettingsDataStore
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.ui.platform.LocalHapticFeedback
@Composable
fun TouchpadScreen(
    navController: NavController
) {

    val vm: MainViewModel = viewModel()
    val haptic = LocalHapticFeedback.current
    val context = LocalContext.current

    LaunchedEffect(Unit) {

        val serverUrl = SettingsDataStore(context)
            .serverUrl
            .first()

        TouchpadSocket.connect(serverUrl)

    }
    DisposableEffect(Unit) {

        onDispose {

            TouchpadSocket.disconnect()

        }

    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Remote Touchpad",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Use your phone as a wireless trackpad",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .pointerInput(Unit) {

                    detectTapGestures(

                        onDoubleTap = {

                            haptic.performHapticFeedback(
                                HapticFeedbackType.LongPress
                            )

                            TouchpadSocket.leftClick()

                        }

                    )

                }
                .pointerInput(Unit) {

                    detectDragGestures { change, dragAmount ->

                        change.consume()

                        TouchpadSocket.move(
                            dragAmount.x * 2f,
                            dragAmount.y * 2f
                        )

                    }

                },
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(6.dp)
        ) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Icon(
                        imageVector = Icons.Rounded.Mouse,
                        contentDescription = null,
                        modifier = Modifier.size(56.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Drag your finger here",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "Cursor moves on your Mac",
                        style = MaterialTheme.typography.bodyMedium
                    )

                }

            }

        }

        Spacer(modifier = Modifier.height(18.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Button(
                modifier = Modifier.weight(1f),
                onClick = {

                    haptic.performHapticFeedback(
                        HapticFeedbackType.LongPress
                    )

                    TouchpadSocket.leftClick()

                }
            ) {
                Icon(Icons.Rounded.Mouse, null)
                Spacer(Modifier.width(8.dp))
                Text("Left Click")
            }

            Button(
                modifier = Modifier.weight(1f),
                onClick = {

                    haptic.performHapticFeedback(
                        HapticFeedbackType.LongPress
                    )

                    TouchpadSocket.rightClick()

                }
            ) {
                Icon(Icons.Rounded.Mouse, null)
                Spacer(Modifier.width(8.dp))
                Text("Right Click")
            }

        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = {

                // TODO
                // Keyboard screen

            }
        ) {

            Icon(Icons.Rounded.Keyboard, null)

            Spacer(modifier = Modifier.width(8.dp))

            Text("Keyboard")

        }

    }

}