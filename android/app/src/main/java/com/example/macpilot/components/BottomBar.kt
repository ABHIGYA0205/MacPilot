package com.example.macpilot.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ContentPaste
import androidx.compose.material.icons.rounded.SettingsRemote
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Psychology
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.macpilot.navigation.Screen
import androidx.compose.material.icons.rounded.DesktopWindows
@Composable
fun BottomBar(
    navController: NavController,
    currentRoute: String?
) {

    Surface(
        shadowElevation = 20.dp,
        tonalElevation = 10.dp,
        color = Color.Transparent
    ) {

        NavigationBar(

            modifier = Modifier
                .padding(12.dp)
                .clip(RoundedCornerShape(24.dp)),

            containerColor = MaterialTheme.colorScheme.surface

        ) {

            val items = listOf(

                Triple(
                    Screen.Dashboard,
                    "Home",
                    Icons.Rounded.Home
                ),
                Triple(
                    Screen.Screenshot,
                    "Screen",
                    Icons.Rounded.DesktopWindows
                ),

                Triple(
                    Screen.Files,
                    "Controls",
                    Icons.Rounded.SettingsRemote
                ),

                Triple(
                    Screen.Clipboard,
                    "Clipboard",
                    Icons.Rounded.ContentPaste
                ),

                Triple(
                    Screen.AI,
                    "Assistant",
                    Icons.Rounded.Psychology
                ),

                Triple(
                    Screen.Settings,
                    "Settings",
                    Icons.Rounded.Settings
                )

            )

            items.forEach { item ->

                NavigationBarItem(

                    selected = currentRoute == item.first.route,

                    onClick = {

                        navController.navigate(item.first.route) {

                            launchSingleTop = true

                            restoreState = true

                            popUpTo(Screen.Dashboard.route)

                        }

                    },

                    icon = {

                        Icon(

                            imageVector = item.third,

                            contentDescription = item.second

                        )

                    },

                    label = {

                        Text(item.second)

                    },

                    colors = NavigationBarItemDefaults.colors(

                        selectedIconColor = Color.White,

                        selectedTextColor = Color.White,

                        indicatorColor = Color(0xFF2563EB)

                    )

                )

            }

        }

    }

}