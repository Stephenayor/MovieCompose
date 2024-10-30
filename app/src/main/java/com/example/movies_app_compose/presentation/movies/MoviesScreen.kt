package com.example.movies_app_compose.presentation.movies

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.movies_app_compose.utils.ApiResponse

@Composable
fun MoviesScreen(
    viewModel: MoviesViewModel = hiltViewModel()
) {
//    val moviesList by viewModel.moviesListResponse.colle
    LaunchedEffect(Unit) {
        viewModel.getAllMovies(1)
    }

//    val moviesList by viewModel.moviesListResponse.toString()

//    when (moviesList) {
//        is ApiResponse.Loading -> {
//            // Show a loading indicator
//            CircularProgressIndicator()
//        }
//        is ApiResponse.Success<*> -> {
//           Text(text = "")
//        }
//        is ApiResponse.Failure -> {
//            val error = (moviesState as ApiResponse.Failure).e
//            Text(text = "Error: ${error?.message ?: "Unknown error"}")
//        }
//    }

    when(val moviesResponse = viewModel.moviesListResponse) {
        is ApiResponse.Loading -> Unit

        is ApiResponse.Success -> moviesResponse.data?.let {
            Text(text = moviesResponse.data.toString())
        }

        is ApiResponse.Failure -> LaunchedEffect(Unit) {
            print(moviesResponse.e)
        }
    }

}