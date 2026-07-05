package com.example.macpilot.navigation

sealed class Screen(val route: String) {

    object Dashboard : Screen("dashboard")

    object Files : Screen("files")

    object Clipboard : Screen("clipboard")

    object AI : Screen("ai")

    object Settings : Screen("settings")

    object Screenshot : Screen("screen")

}