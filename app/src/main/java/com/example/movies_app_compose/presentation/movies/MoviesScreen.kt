package com.example.movies_app_compose.presentation.movies

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.movies_app_compose.domain.model.Movie
import com.example.movies_app_compose.domain.model.MoviesList
import com.example.movies_app_compose.utils.Api
import com.example.movies_app_compose.utils.ApiResponse

@Composable
fun MoviesScreen(
    navController: NavController,
    viewModel: MoviesViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        viewModel.getAllMovies(1)
    }
    val context = LocalContext.current
    var moviesList: List<Movie>
    val paddingValues = PaddingValues(
        start = 0.dp,
        top = 0.dp,
        end = 0.dp,
        bottom = 0.dp
    )


    when (val moviesResponse = viewModel.moviesListResponse) {
        is ApiResponse.Loading ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }

        is ApiResponse.Success -> {
            moviesResponse.data?.results?.let { movies ->
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(8.dp),
                    modifier = Modifier.padding(paddingValues)
                ) {
                    items(movies) { movie ->
                        MovieItem(movie, navController)
                    }
                }
            }
        }

        is ApiResponse.Failure -> LaunchedEffect(Unit) {
            Toast.makeText(context, moviesResponse.e?.message, Toast.LENGTH_SHORT).show()
        }
    }


}

@Composable
fun MovieItem(movie: Movie, navController: NavController) {
    Column(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .background(Color.DarkGray, RoundedCornerShape(8.dp))
            .clickable {navController.navigate("movieDetails/${movie.id}") },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = rememberImagePainter(data = Api.BASE_POSTER_PATH + movie.poster_path),
            contentDescription = movie.title,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(8.dp))
        )
        Text(
            text = movie.title,
            style = MaterialTheme.typography.subtitle1,
            color = Color.White,
            modifier = Modifier.padding(8.dp)
        )
    }
}


