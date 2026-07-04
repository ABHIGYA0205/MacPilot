package com.example.macpilot

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import com.example.macpilot.model.StatusResponse
import com.example.macpilot.network.RetrofitClient
import com.example.macpilot.screens.HomeScreen
import com.example.macpilot.ui.theme.MacPilotTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            MacPilotTheme {

                val scope = rememberCoroutineScope()

                var status by remember {
                    mutableStateOf<StatusResponse?>(null)
                }

                LaunchedEffect(Unit) {
                    try {
                        val response = RetrofitClient.api.getStatus()

                        if (response.isSuccessful) {
                            status = response.body()
                        }
                    } catch (_: Exception) {
                    }
                }

                HomeScreen(
                    status = status,
                    onLockClick = {

                        scope.launch {

                            try {

                                RetrofitClient.api.lockMac()

                                Toast.makeText(
                                    this@MainActivity,
                                    "Mac Locked!",
                                    Toast.LENGTH_SHORT
                                ).show()

                            } catch (e: Exception) {

                                Toast.makeText(
                                    this@MainActivity,
                                    e.localizedMessage,
                                    Toast.LENGTH_LONG
                                ).show()

                            }

                        }

                    }
                )

            }
        }
    }
}