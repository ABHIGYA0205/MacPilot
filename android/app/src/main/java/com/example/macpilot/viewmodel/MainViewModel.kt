package com.example.macpilot.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.macpilot.model.StatusResponse
import com.example.macpilot.repository.MacRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repository = MacRepository(getApplication())

    private val _status = MutableStateFlow<StatusResponse?>(null)
    val status: StateFlow<StatusResponse?> = _status.asStateFlow()

    private val _connected = MutableStateFlow(false)
    val connected: StateFlow<Boolean> = _connected.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()




    private val _screenshot = MutableStateFlow<ByteArray?>(null)
    val screenshot: StateFlow<ByteArray?> = _screenshot

    private val _volume = MutableStateFlow(50)
    val volume: StateFlow<Int> = _volume.asStateFlow()
    init {
        startMonitoring()
    }

    private fun startMonitoring() {

        viewModelScope.launch {

            while (true) {

                refreshStatus()

                delay(3000)

            }

        }

    }

    fun refreshStatus() {

        viewModelScope.launch {

            _loading.value = true

            repository.getStatus()
                .onSuccess {

                    _status.value = it
                    _connected.value = true

                }
                .onFailure {

                    _connected.value = false

                }

            _loading.value = false

        }

    }




    fun setVolume(value: Int) {

        _volume.value = value

        viewModelScope.launch {

            repository.setVolume(value)

        }

    }
    fun loadVolume() {

        viewModelScope.launch {

            try {

                val response = repository.getVolume()

                if (response.isSuccessful) {

                    _volume.value =
                        response.body()?.value ?: 50

                }

            } catch (_: Exception) {

            }

        }

    }
    fun executeCommand(
        command: String,
        onComplete: (Boolean) -> Unit = {}
    ) {

        viewModelScope.launch {

            try {

                val response =
                    repository.executeCommand(command)

                onComplete(response.isSuccessful)

            } catch (e: Exception) {

                onComplete(false)

            }

        }

    }
    fun pushClipboard(
        text: String,
        onComplete: (Boolean) -> Unit = {}
    ) {

        viewModelScope.launch {

            try {

                val response =
                    repository.setClipboard(text)

                onComplete(response.isSuccessful)

            } catch (e: Exception) {

                onComplete(false)

            }

        }

    }

    fun pullClipboard(
        onComplete: (String?) -> Unit
    ) {

        viewModelScope.launch {

            try {

                val response =
                    repository.getClipboard()

                if (
                    response.isSuccessful &&
                    response.body() != null
                ) {

                    onComplete(response.body()!!.text)

                } else {

                    onComplete(null)

                }

            } catch (e: Exception) {

                onComplete(null)

            }

        }

    }
    fun testConnection(
        onResult: (StatusResponse?) -> Unit
    ) {

        viewModelScope.launch {

            repository.getStatus()
                .onSuccess {

                    onResult(it)

                }
                .onFailure {

                    onResult(null)

                }

        }

    }
    fun loadScreenshot() {

        viewModelScope.launch {

            try {

                val response = repository.getScreenshot()

                if (response.isSuccessful) {

                    _screenshot.value =
                        response.body()?.bytes()

                }

            } catch (_: Exception) {

            }

        }

    }
    fun moveMouse(
        dx: Float,
        dy: Float
    ) {

        viewModelScope.launch {

            try {

                repository.moveMouse(dx, dy)

            } catch (_: Exception) {

            }

        }

    }
    fun leftClick() {

        viewModelScope.launch {

            repository.mouseClick("left")

        }

    }

    fun rightClick() {

        viewModelScope.launch {

            repository.mouseClick("right")

        }

    }
    fun scrollMouse(
        dy: Float
    ) {

        viewModelScope.launch {

            repository.scrollMouse(dy)

        }

    }


}
