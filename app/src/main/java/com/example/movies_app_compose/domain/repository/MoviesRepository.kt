package com.example.movies_app_compose.domain.repository

import com.example.movies_app_compose.domain.model.MoviesList
import com.example.movies_app_compose.utils.ApiResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface MoviesRepository {

    suspend fun getAllMovies(page: Int): Flow<ApiResponse<MoviesList>>
}