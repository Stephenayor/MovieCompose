package com.example.movies_app_compose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.movies_app_compose.domain.model.Movie
import com.example.movies_app_compose.presentation.main.BannerScreen
import com.example.movies_app_compose.presentation.main.MainScreen
import com.example.movies_app_compose.presentation.movies.MovieDetails

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "Banner screen") {
        composable("Banner screen") {
            BannerScreen(navController = navController)
        }
        composable("Main screen") {
            MainScreen(navController = navController)
        }
        composable(
            route = "movieDetails/{movieId}",
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getInt("movieId")
            movieId?.let { MovieDetails(movieId = it,  navController = navController) }
        }
    }
}