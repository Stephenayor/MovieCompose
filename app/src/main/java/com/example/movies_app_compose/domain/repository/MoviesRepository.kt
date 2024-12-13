package com.example.movies_app_compose.domain.repository

import com.example.movies_app_compose.domain.model.MoviesList
import com.example.movies_app_compose.domain.model.details.MovieDetails
import com.example.movies_app_compose.domain.model.trailers.Trailers
import com.example.movies_app_compose.utils.ApiResponse
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    suspend fun getAllMovies(page: Int): Flow<ApiResponse<MoviesList>>
    suspend fun getMoviesDetails(movieId: Int): Flow<ApiResponse<MovieDetails>>
    suspend fun fetchMovies()
    suspend fun getMovieTrailers(movieId: Int):  Flow<ApiResponse<Trailers>>
}