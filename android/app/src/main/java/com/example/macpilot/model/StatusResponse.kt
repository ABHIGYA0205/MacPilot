package com.example.macpilot.model

data class StatusResponse(
    val device: String,
    val battery: Int?,
    val charging: Boolean?,
    val cpu: Double,
    val ram: Double
)