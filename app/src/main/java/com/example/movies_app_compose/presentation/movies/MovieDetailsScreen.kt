package com.example.movies_app_compose.presentation.movies

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayCircleFilled
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.movies_app_compose.R
import com.example.movies_app_compose.domain.model.details.MovieDetails
import com.example.movies_app_compose.domain.model.trailers.Results
import com.example.movies_app_compose.domain.model.trailers.Trailers
import com.example.movies_app_compose.presentation.custom.RatingBar
import com.example.movies_app_compose.utils.Api
import com.example.movies_app_compose.utils.Api.BASE_BACKDROP_PATH
import com.example.movies_app_compose.utils.Api.YOUTUBE_THUMBNAIL_URL
import com.example.movies_app_compose.utils.Api.YOUTUBE_VIDEO_URL
import com.example.movies_app_compose.utils.ApiResponse


@Composable
fun MovieDetails(
    movieId: Int, navController: NavController,
    viewModel: MoviesDetailsViewModel = hiltViewModel()
) {

    // Fetch the movie details
    LaunchedEffect(movieId) {
        viewModel.getMovieDetails(movieId)
        viewModel.getMovieTrailers(movieId)
    }

    val movieDetailsResponse = viewModel.moviesDetailsResponse

    val movieTrailersResponse = viewModel.moviesTrailersResponse
    var allMovieDetails = remember { mutableStateOf<MovieDetails?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = ("Movie Details")) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                backgroundColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White
            )
        }

    ) { innerPadding ->
        when (movieDetailsResponse) {
            is ApiResponse.Loading -> {
                // Show a loading indicator
                Text(
                    text = "Loading...",
                    modifier = Modifier.padding(innerPadding)
                )
            }

            is ApiResponse.Success -> {
                // Render the UI with movie details
                movieDetailsResponse.data?.let { movieDetails ->
                    MovieDetailHeader(
                        movieDetails = movieDetails,
                        modifier = Modifier.padding(innerPadding),
                        null
                    )
                    allMovieDetails.value = movieDetails
                }
            }

            is ApiResponse.Failure -> {
                // Show an error message
                Text(
                    text = movieDetailsResponse.e?.message ?: "Error loading movie details",
                    color = Color.Red,
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }

        when (movieTrailersResponse) {
            is ApiResponse.Loading -> {
                // Show a loading indicator
                Text(
                    text = "Loading videos...",
                    modifier = Modifier.padding(innerPadding)
                )
            }

            is ApiResponse.Success -> {
                movieTrailersResponse.data?.let { trailers ->
                    allMovieDetails.value?.let {
                        MovieDetailHeader(
                            it,
                            modifier = Modifier.padding(innerPadding),
                            trailers
                        )
                    }
                }
            }

            is ApiResponse.Failure -> {
                // Show an error message

            }
        }
    }
}

@Composable
fun MovieDetailHeader(
    movieDetails: MovieDetails, modifier: Modifier = Modifier,
    trailers: Trailers?
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(5.dp)
            .background(Color.Gray)
    )
    {

        // Movie Poster
        Image(
            painter = rememberImagePainter(data = Api.BASE_POSTER_PATH + movieDetails.posterPath),
            contentDescription = "Movie Poster",
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Log.d("movieId", movieDetails.id.toString())

        //Movie Title
        Text(
            text = movieDetails.originalTitle ?: "Unknown Title",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White,
            textAlign = TextAlign.Center,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        )


        //Release Date
        Text(
            text = "Release Date: ${movieDetails.releaseDate ?: "N/A"}",
            style = MaterialTheme.typography.bodySmall,
            color = Color.White,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        )

        //Rating
        RatingBar(
            rating = (movieDetails.voteAverage?.toFloat() ?: 0f) / 2f,
            modifier = Modifier
                .padding(top = 5.dp)
                .height(15.dp)
                .align(Alignment.CenterHorizontally)
        )

        // Trailers Section
        Text(
            text = "Trailers",
            style = MaterialTheme.typography.titleMedium,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 8.dp, bottom = 8.dp)
        )
        Log.d("trailers", trailers?.results.toString())
        if (trailers != null) {
            HorizontalTrailerList(trailers = trailers)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Summary Section
        Text(
            text = "Summary",
            style = MaterialTheme.typography.titleMedium,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 8.dp, bottom = 8.dp)
        )
        Text(
            text = movieDetails.overview ?: "No summary available.",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        )
    }
}

@Composable
fun HorizontalTrailerList(trailers: Trailers) {
    androidx.compose.foundation.lazy.LazyRow(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(trailers.results) { trailer ->
            TrailerCard(trailer = trailer)
        }
    }
}



@Composable
fun TrailerCard(trailer: Results) {
    val youtubeThumbnailUrl = "${YOUTUBE_THUMBNAIL_URL}${trailer.key}/0.jpg"
    val youtubeVideoUrl = "$YOUTUBE_VIDEO_URL${trailer.key}"
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .padding(8.dp)
            .width(150.dp)
    ) {
        // Box for stacking the thumbnail and play button
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16 / 9f)
                .clickable {
                    // Open the YouTube video in a browser or YouTube app
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeVideoUrl))
                    context.startActivity(intent)
                }
        ) {
            // Thumbnail image
            Image(
                painter = rememberImagePainter(data = youtubeThumbnailUrl),
                contentDescription = "Trailer Thumbnail",
                modifier = Modifier.fillMaxSize()
            )

            // Play button overlay
            Icon(
                imageVector = Icons.Default.PlayCircleFilled,
                contentDescription = "Play Button",
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(50.dp)
                .shadow(8.dp, RectangleShape),
            tint = Color.Red
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Trailer title
        Text(
            text = trailer.name ?: "Trailer",
            style = MaterialTheme.typography.bodySmall,
            color = Color.White,
            modifier = Modifier.padding(top = 4.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}









