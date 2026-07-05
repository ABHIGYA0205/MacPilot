package com.example.macpilot.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable

private val colors = darkColorScheme(

    primary = Blue,

    secondary = Accent,

    background = Background,

    surface = CardColor

)

@Composable
fun MacPilotTheme(
    content: @Composable () -> Unit
) {

    MaterialTheme(

        colorScheme = colors,

        content = content

    )

}