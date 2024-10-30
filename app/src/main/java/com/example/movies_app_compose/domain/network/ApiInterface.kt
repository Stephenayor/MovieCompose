package com.example.movies_app_compose.domain.network

import com.example.movies_app_compose.domain.model.MoviesList
import com.example.movies_app_compose.utils.ApiResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("movies?")
    suspend fun getMovies(
        @Query("page")page: Int
    ): Flow<ApiResponse<MoviesList>>
}