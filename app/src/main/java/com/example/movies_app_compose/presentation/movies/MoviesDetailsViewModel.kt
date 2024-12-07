package com.example.movies_app_compose.presentation.movies

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies_app_compose.domain.model.details.MovieDetails
import com.example.movies_app_compose.domain.model.trailers.Trailers
import com.example.movies_app_compose.domain.repository.MoviesRepository
import com.example.movies_app_compose.utils.ApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesDetailsViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository
): ViewModel() {

    var moviesDetailsResponse by mutableStateOf<ApiResponse<MovieDetails>>(ApiResponse.Loading)
        private set

    var moviesTrailersResponse by mutableStateOf<ApiResponse<Trailers>>(ApiResponse.Loading)
        private set

    init {

    }

    fun getMovieDetails(movieId: Int) = viewModelScope.launch {
        moviesRepository.getMoviesDetails(movieId).collect { response ->
            moviesDetailsResponse = response
        }
    }

    fun getMovieTrailers(movieId: Int) = viewModelScope.launch {
        moviesRepository.getMovieTrailers(movieId).collect { response ->
            moviesTrailersResponse = response
        }
    }
}