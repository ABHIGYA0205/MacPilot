package com.example.macpilot.network

import com.example.macpilot.model.StatusResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @POST("lock")
    suspend fun lockMac(): Response<Unit>

    @GET("status")
    suspend fun getStatus(): Response<StatusResponse>
}