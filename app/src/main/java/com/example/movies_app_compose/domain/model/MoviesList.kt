package com.example.movies_app_compose.domain.model

data class MoviesList(
    val page: Int,
    val results: List<Movie>
)