    package com.example.movies_app_compose.presentation.main

    import androidx.compose.foundation.background
    import androidx.compose.foundation.layout.height
    import androidx.compose.foundation.layout.padding
    import androidx.compose.material.BottomNavigation
    import androidx.compose.material.BottomNavigationItem
    import androidx.compose.material.Icon
    import androidx.compose.material.MaterialTheme
    import androidx.compose.material.Scaffold
    import androidx.compose.material.Text
    import androidx.compose.material.TopAppBar
    import androidx.compose.material.icons.Icons
    import androidx.compose.material.icons.filled.Home
    import androidx.compose.material.icons.filled.Person
    import androidx.compose.material.icons.filled.Search
    import androidx.compose.material.primarySurface
    import androidx.compose.runtime.Composable
    import androidx.compose.runtime.getValue
    import androidx.compose.runtime.mutableStateOf
    import androidx.compose.runtime.remember
    import androidx.compose.runtime.setValue
    import androidx.compose.ui.Alignment
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.graphics.Color
    import androidx.compose.ui.graphics.vector.ImageVector
    import androidx.compose.ui.res.stringResource
    import androidx.compose.ui.text.font.FontWeight
    import androidx.compose.ui.tooling.preview.Preview
    import androidx.compose.ui.unit.dp
    import androidx.compose.ui.unit.sp
    import androidx.navigation.NavController
    import androidx.navigation.compose.rememberNavController
    import com.example.movies_app_compose.R
    import com.example.movies_app_compose.presentation.movies.MoviesScreen
    import com.example.movies_app_compose.ui.theme.Purple40

    @Composable
    fun MainScreen(navController: NavController) {
        var selectedTab by remember { mutableStateOf<Screen>(Screen.Movies) }

        Scaffold(
            modifier = Modifier.background(MaterialTheme.colors.primarySurface),
            topBar = { MainAppBar() },
            bottomBar = {
                BottomNavigationBar(selectedTab) { screen ->
                    selectedTab = screen
                }
            }
        ) { innerPadding ->
            // Content goes here based on selectedTab
            when (selectedTab) {
                Screen.Movies -> MoviesScreen()
                Screen.Search -> SearchScreen()
                Screen.Profile -> ProfileScreen()
            }
        }

    }

    @Composable
    fun HomeScreen() {
        Text("Home Screen")
    }

    @Composable
    fun SearchScreen() {
        Text("Search Screen")
    }

    @Composable
    fun ProfileScreen() {
        Text("Profile Screen")
    }

    @Composable
    fun BottomNavigationBar(
        selectedTab: Screen,
        onTabSelected: (Screen) -> Unit
    ) {
        BottomNavigation(
            backgroundColor = MaterialTheme.colors.primarySurface,
            contentColor = Color.White
        ) {
            val items = listOf(Screen.Movies, Screen.Search, Screen.Profile)
            items.forEach { screen ->
                BottomNavigationItem(
                    icon = { Icon(screen.icon, contentDescription = screen.label) },
                    label = { Text(screen.label) },
                    selected = selectedTab == screen,
                    onClick = { onTabSelected(screen) }
                )
            }
        }
    }

    @Preview
    @Composable
    fun MainAppBar() {
        TopAppBar(
            elevation = 6.dp,
            backgroundColor = MaterialTheme.colors.primarySurface,
            modifier = Modifier.height(58.dp)
        ) {
            Text(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterVertically),
                text = stringResource(R.string.appname),
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }

    sealed class Screen(val route: String, val label: String, val icon: ImageVector) {
        object Movies : Screen("movies", "Movies", Icons.Filled.Home)
        object Search : Screen("search", "Search", Icons.Filled.Search)
        object Profile : Screen("profile", "Profile", Icons.Filled.Person)
    }