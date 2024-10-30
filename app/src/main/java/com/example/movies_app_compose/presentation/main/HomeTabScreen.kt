package com.example.movies_app_compose.presentation.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.primarySurface
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun HomeTabScreen() {
    Scaffold(
        modifier = Modifier.background(MaterialTheme.colors.primarySurface),
        topBar = { MainAppBar() },
    ) {innerPadding ->
        val modifier = Modifier.padding(innerPadding)

    }
}



