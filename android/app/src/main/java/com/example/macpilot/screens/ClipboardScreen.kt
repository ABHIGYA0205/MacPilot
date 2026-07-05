package com.example.macpilot.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Computer
import androidx.compose.material.icons.rounded.ContentCopy
import androidx.compose.material.icons.rounded.Download
import androidx.compose.material.icons.rounded.PhoneAndroid
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.macpilot.viewmodel.MainViewModel
@Composable
fun ClipboardScreen(
    navController: NavController
) {

    val context = LocalContext.current
    val vm: MainViewModel = viewModel()

    val clipboardManager = remember {
        context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    }

    val scope = rememberCoroutineScope()

    var clipboardText by remember {
        mutableStateOf("No clipboard data available")
    }

    val preview =
        if (clipboardText.length > 500)
            clipboardText.take(500) + "\n\n..."
        else
            clipboardText

    val scrollState = rememberScrollState()

    fun loadPhoneClipboard() {
        val clip = clipboardManager.primaryClip

        clipboardText =
            if (clip != null && clip.itemCount > 0) {
                clip.getItemAt(0).coerceToText(context).toString()
            } else {
                "Clipboard is empty"
            }
    }

    fun copyToPhoneClipboard(text: String) {
        val clip = ClipData.newPlainText("MacPilot", text)
        clipboardManager.setPrimaryClip(clip)
    }

    LaunchedEffect(Unit) {
        loadPhoneClipboard()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        Text(
            text = "Clipboard",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(20.dp)
            ) {

                Icon(
                    Icons.Rounded.ContentCopy,
                    contentDescription = null
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = preview,
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "${clipboardText.length} characters",
                    style = MaterialTheme.typography.labelMedium
                )

            }

        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Actions",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            FilledTonalButton(
                modifier = Modifier.weight(1f),
                onClick = {

                    val clip = clipboardManager.primaryClip

                    val text =
                        if (clip != null && clip.itemCount > 0)
                            clip.getItemAt(0)
                                .coerceToText(context)
                                .toString()
                        else
                            ""

                    vm.pushClipboard(text) { success ->

                        if (success) {

                            clipboardText = text

                        }

                    }

                }
            ) {

                Icon(Icons.Rounded.Computer, null)

                Spacer(modifier = Modifier.width(6.dp))

                Text("Push to Mac")

            }

            FilledTonalButton(
                modifier = Modifier.weight(1f),
                onClick = {

                    vm.pullClipboard { text ->

                        if (text != null) {

                            clipboardText = text

                            copyToPhoneClipboard(text)

                        }

                    }

                }
            ) {

                Icon(Icons.Rounded.PhoneAndroid, null)

                Spacer(modifier = Modifier.width(6.dp))

                Text("Pull")

            }

        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = {

                vm.pullClipboard { text ->

                    if (text != null) {

                        clipboardText = text

                    }

                }

            }
        ) {

            Icon(Icons.Rounded.Download, null)

            Spacer(modifier = Modifier.width(6.dp))

            Text("Refresh Clipboard")

        }

    }

}