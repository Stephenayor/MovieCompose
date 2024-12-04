package com.example.movies_app_compose.presentation.movies

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies_app_compose.domain.model.MoviesList
import com.example.movies_app_compose.domain.repository.MoviesRepository
import com.example.movies_app_compose.utils.ApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository
): ViewModel() {

    var moviesListResponse by mutableStateOf<ApiResponse<MoviesList>>(ApiResponse.Loading)
        private set


    init {
//        getAllMovies(1)
//        getMovies()
    }

     fun getAllMovies(page: Int) = viewModelScope.launch {
        moviesRepository.getAllMovies(page).collect { response ->
            moviesListResponse = response
        }
    }
    fun getMovies() = viewModelScope.launch {
        moviesRepository.fetchMovies()
    }
}