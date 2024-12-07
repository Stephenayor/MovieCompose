package com.example.movies_app_compose.domain.network

import com.example.movies_app_compose.domain.model.MoviesList
import com.example.movies_app_compose.domain.model.details.MovieDetails
import com.example.movies_app_compose.domain.model.trailers.Trailers
import com.example.movies_app_compose.utils.ApiResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {
    @GET("discover/movie?")
    suspend fun getMovies(
        @Query("include_adult") includeAdult: Boolean? = false,
        @Query("include_video") includeVideo: Boolean? = false,
        @Query("language") language: String? = "en-US",
        @Query("page") page: Int = 1,
        @Query("sort_by") sortBy: String? = "popularity.desc"
    ): MoviesList

    @GET("movie/{movie_id}")
    suspend fun getMovieDetailsById(
        @Path("movie_id")id: Int
    ): MovieDetails

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieTrailers(
        @Path("movie_id")id: Int
    ): Trailers

}