package com.example.macpilot.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("settings")

class SettingsDataStore(
    private val context: Context
) {

    companion object {
        val SERVER_URL = stringPreferencesKey("server_url")
    }

    val serverUrl = context.dataStore.data.map {

        it[SERVER_URL] ?: "http://10.195.127.139:8000/"

    }

    suspend fun saveServerUrl(url: String) {

        context.dataStore.edit {

            it[SERVER_URL] = url

        }

    }

}