package com.example.macpilot.repository

import android.content.Context
import com.example.macpilot.datastore.SettingsDataStore
import com.example.macpilot.model.*
import com.example.macpilot.network.RetrofitClient
import kotlinx.coroutines.flow.first
import com.example.macpilot.model.MouseMoveRequest
import com.example.macpilot.model.MouseClickRequest
import com.example.macpilot.model.MouseScrollRequest
private const val API_KEY = "macpilot-secret-key"

class MacRepository(
    private val context: Context
) {

    private suspend fun api() =
        RetrofitClient.getApi(
            SettingsDataStore(context)
                .serverUrl
                .first()
        )

    suspend fun getStatus(): Result<StatusResponse> {

        return try {

            val response = api().getStatus(API_KEY)

            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Unable to fetch status"))
            }

        } catch (e: Exception) {

            Result.failure(e)

        }

    }

    suspend fun getClipboard() =
        api().getClipboard(API_KEY)

    suspend fun setClipboard(text: String) =
        api().setClipboard(
            API_KEY,
            ClipboardRequest(text)
        )

    suspend fun executeCommand(command: String) =
        api().executeCommand(
            API_KEY,
            CommandRequest(command)
        )

    suspend fun getVolume() =
        api().getVolume(API_KEY)

    suspend fun setVolume(value: Int) =
        api().setVolume(
            API_KEY,
            VolumeRequest(value)
        )

    suspend fun getScreenshot() =
        api().getScreenshot(API_KEY)

    suspend fun moveMouse(
        dx: Float,
        dy: Float
    ) =
        api().moveMouse(
            API_KEY,
            MouseMoveRequest(dx, dy)
        )

    suspend fun mouseClick(
        button: String
    ) =
        api().mouseClick(
            API_KEY,
            MouseClickRequest(button)
        )
    suspend fun scrollMouse(
        dy: Float
    ) =
        api().scrollMouse(
            API_KEY,
            MouseScrollRequest(dy)
        )

}
