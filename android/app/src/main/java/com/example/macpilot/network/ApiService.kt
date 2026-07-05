package com.example.macpilot.network

import com.example.macpilot.model.*
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("status")
    suspend fun getStatus(
        @Header("X-API-Key") apiKey: String
    ): Response<StatusResponse>

    @GET("clipboard")
    suspend fun getClipboard(
        @Header("X-API-Key") apiKey: String
    ): Response<ClipboardResponse>

    @POST("clipboard")
    suspend fun setClipboard(
        @Header("X-API-Key") apiKey: String,
        @Body request: ClipboardRequest
    ): Response<Unit>

    @POST("command")
    suspend fun executeCommand(
        @Header("X-API-Key") apiKey: String,
        @Body request: CommandRequest
    ): Response<Unit>

    @GET("volume")
    suspend fun getVolume(
        @Header("X-API-Key") apiKey: String
    ): Response<VolumeResponse>

    @POST("volume")
    suspend fun setVolume(
        @Header("X-API-Key") apiKey: String,
        @Body request: VolumeRequest
    ): Response<Unit>

    @GET("screenshot")
    suspend fun getScreenshot(
        @Header("X-API-Key") apiKey: String
    ): Response<ResponseBody>

}