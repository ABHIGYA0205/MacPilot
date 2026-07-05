package com.example.macpilot

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.example.macpilot.navigation.AppNavigation
import com.example.macpilot.ui.theme.MacPilotTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        val splashScreen = installSplashScreen()

        var keepSplash = true

        splashScreen.setKeepOnScreenCondition {
            keepSplash
        }

        lifecycleScope.launch {

            delay(1000)   // Keep splash for 1 second

            keepSplash = false

        }

        super.onCreate(savedInstanceState)

        setContent {
            MacPilotTheme {
                AppNavigation()
            }
        }
    }
}