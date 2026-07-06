package com.example.macpilot.network

import okhttp3.*
import org.json.JSONObject

object TouchpadSocket {

    private val client = OkHttpClient()

    private var socket: WebSocket? = null

    fun connect(serverUrl: String) {

        if (socket != null) return

        val wsUrl = serverUrl
            .replace("http://", "ws://")
            .replace("https://", "wss://")
            .trimEnd('/') + "/ws/touchpad"

        val request = Request.Builder()
            .url(wsUrl)
            .build()

        socket = client.newWebSocket(
            request,
            object : WebSocketListener() {

                override fun onOpen(
                    webSocket: WebSocket,
                    response: Response
                ) {
                    println("Touchpad Connected")
                }

                override fun onFailure(
                    webSocket: WebSocket,
                    t: Throwable,
                    response: Response?
                ) {
                    t.printStackTrace()
                }

            }
        )
    }

    fun move(dx: Float, dy: Float) {
        socket?.send(
            JSONObject()
                .put("type", "move")
                .put("dx", dx)
                .put("dy", dy)
                .toString()
        )
    }

    fun leftClick() {
        socket?.send(
            JSONObject()
                .put("type", "left")
                .toString()
        )
    }

    fun rightClick() {
        socket?.send(
            JSONObject()
                .put("type", "right")
                .toString()
        )
    }

    fun scroll(dy: Float) {
        socket?.send(
            JSONObject()
                .put("type", "scroll")
                .put("dy", dy)
                .toString()
        )
    }

    fun disconnect() {
        socket?.close(1000, "Bye")
        socket = null
    }
}