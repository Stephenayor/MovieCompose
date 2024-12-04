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

//data class ApiResponse<T>(
//    var status: String? = null,
//    var data: T? = null,
//    var message: String? = null
//) {
//    // No-argument constructor required by Gson
//    constructor() : this(null, null, null)
//}