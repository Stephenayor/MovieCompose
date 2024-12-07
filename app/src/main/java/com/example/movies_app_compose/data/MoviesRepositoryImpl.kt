package com.example.movies_app_compose.data

import android.util.Log
import com.example.movies_app_compose.domain.network.ApiInterface
import com.example.movies_app_compose.domain.model.MoviesList
import com.example.movies_app_compose.domain.model.details.MovieDetails
import com.example.movies_app_compose.domain.model.trailers.Trailers
import com.example.movies_app_compose.domain.repository.MoviesRepository
import com.example.movies_app_compose.utils.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val apiInterface: ApiInterface
) : MoviesRepository {

    override suspend fun getAllMovies(page: Int): Flow<ApiResponse<MoviesList>> = flow {
        emit(ApiResponse.Loading) // Emit loading state before the network call
        try {
            val response = apiInterface.getMovies(
                includeAdult = false,
                includeVideo = false,
                language = "en-US",
                page = 1,
                sortBy = "popularity.desc"
            )
            emit(ApiResponse.Success(response))
        } catch (e: Exception) {
            Log.d("MoviesRepository", "Error fetching movies: ${e.message}", e)
            emit(ApiResponse.Failure(e)) // Emit failure state with the exception
        }
    }

    override suspend fun getMoviesDetails(movieId: Int): Flow<ApiResponse<MovieDetails>> = flow {
        emit(ApiResponse.Loading)
        try {
            val response = apiInterface.getMovieDetailsById(movieId)
            emit(ApiResponse.Success(response))
        } catch (e: Exception) {
            emit(ApiResponse.Failure(e))
        }
    }

    override suspend fun getMovieTrailers(movieId: Int): Flow<ApiResponse<Trailers>> = flow {
        emit(ApiResponse.Loading)
        try {
            val response = apiInterface.getMovieTrailers(movieId)
            emit(ApiResponse.Success(response))
        } catch (e: Exception) {
            emit(ApiResponse.Failure(e))
        }
    }

    override suspend fun fetchMovies() {
        val response = apiInterface.getMovies(
            includeAdult = false,
            includeVideo = false,
            language = "en-US",
            page = 1,
            sortBy = "popularity.desc"
        )
        Log.d("Movies", response.results.toString())
    }


}