package com.example.macpilot.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.macpilot.viewmodel.MainViewModel
import android.graphics.BitmapFactory

@Composable
fun ScreenshotScreen(
    navController: NavController
) {

    val vm: MainViewModel = viewModel()

    val imageBytes by vm.screenshot.collectAsState()

    LaunchedEffect(Unit) {
        vm.loadScreenshot()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(18.dp)
    ) {

        Text(
            text = "Mac Screen",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {

                if (imageBytes == null) {

                    CircularProgressIndicator()

                } else {

                    val bitmap =
                        BitmapFactory.decodeByteArray(
                            imageBytes,
                            0,
                            imageBytes!!.size
                        )

                    Image(
                        bitmap = bitmap.asImageBitmap(),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )

                }

            }

        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(

            modifier = Modifier.fillMaxWidth(),

            onClick = {

                vm.loadScreenshot()

            }

        ) {

            Icon(Icons.Rounded.Refresh, null)

            Spacer(modifier = Modifier.width(8.dp))

            Text("Refresh")

        }

    }

}