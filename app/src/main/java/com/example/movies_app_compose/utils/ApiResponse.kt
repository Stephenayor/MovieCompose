package com.example.movies_app_compose.utils

sealed class ApiResponse<out T> {
    object Loading: ApiResponse<Nothing>()

    data class Success<out T>(
        val data: T?
    ): ApiResponse<T>()

    data class Failure(
        val e: Exception?
    ): ApiResponse<Nothing>()
}