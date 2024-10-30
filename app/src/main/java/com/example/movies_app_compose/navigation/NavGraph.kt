package com.example.movies_app_compose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.movies_app_compose.presentation.main.BannerScreen
import com.example.movies_app_compose.presentation.main.MainScreen

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
    }
}