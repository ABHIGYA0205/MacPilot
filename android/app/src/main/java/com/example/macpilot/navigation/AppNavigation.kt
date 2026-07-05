package com.example.macpilot.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.*
import com.example.macpilot.components.BottomBar
import com.example.macpilot.screens.*

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    Scaffold(

        bottomBar = {

            BottomBar(
                navController = navController,
                currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
            )

        }

    ) { paddingValues ->

        NavHost(

            navController = navController,

            startDestination = Screen.Dashboard.route,

            modifier = Modifier.padding(paddingValues)

        ) {

            composable(Screen.Dashboard.route) {
                DashboardScreen(navController)
            }

            composable(Screen.Screenshot.route) {
                ScreenshotScreen(navController)
            }

            composable(Screen.Files.route) {
                FilesScreen(navController)
            }

            composable(Screen.Clipboard.route) {
                ClipboardScreen(navController)
            }

            composable(Screen.AI.route) {
                AIScreen(navController)
            }

            composable(Screen.Settings.route) {
                SettingsScreen(navController)
            }


        }

    }

}