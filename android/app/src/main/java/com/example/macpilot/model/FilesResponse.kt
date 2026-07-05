package com.example.macpilot.model

data class FilesResponse(
    val success: Boolean,
    val current_path: String,
    val items: List<FileItem>
)