package com.sklymenko.composetestapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.*
import com.sklymenko.composetestapp.details.MovieDetails
import com.sklymenko.composetestapp.movies.SoonScreen
import com.sklymenko.composetestapp.ui.ComposeTestAppTheme
import dagger.hilt.android.AndroidEntryPoint

const val EXTRA_MOVIE_ID = "movie_id"

@AndroidEntryPoint
class ExampleActivity : AppCompatActivity() {

    private val soonViewModel: ComingSoonViewModel by viewModels()
    private val nowViewModel: PlayingNowViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTestAppTheme {
                Bars(soonViewModel, nowViewModel) { id -> this.openDetails(id) }
            }
        }
    }

    private fun openDetails(movieId: Int) {
        val intent = Intent(this, MovieDetails::class.java).apply {
            putExtra(EXTRA_MOVIE_ID, movieId)
        }
        startActivity(intent)
    }
}

@Composable
fun Bars(soonViewModel: ComingSoonViewModel, nowViewModel: PlayingNowViewModel, itemClick: (movieId: Int) -> Unit) {

    val navController = rememberNavController()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Compose Example")
                },
                elevation = 16.dp
            )
        },
        bottomBar = {
            BottomAppBar {
                val backStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = backStackEntry?.arguments?.getString(KEY_ROUTE)
                getBottomBarItems().forEach { screen ->
                    BottomNavigationItem(
                        icon = { Icon(vectorResource(id = screen.iconId)) },
                        label = { Text(stringResource(id = screen.resId)) },
                        selected = currentRoute == screen.rout,
                        onClick = {
                            navController.popBackStack(navController.graph.startDestination, false)
                            if (currentRoute != screen.rout) {
                                navController.navigate(screen.rout)
                            }
                        }
                    )
                }
            }
        }
    ) {
        NavHost(navController = navController, startDestination = BottomBarItem.Soon.rout) {
            composable(BottomBarItem.Soon.rout) { SoonScreen(soonViewModel, itemClick) }
            composable(BottomBarItem.Now.rout) { NowScreen(nowViewModel, itemClick) }
        }
    }
}

fun getBottomBarItems() = listOf(BottomBarItem.Soon, BottomBarItem.Now)

sealed class BottomBarItem(
    val rout: String,
    @StringRes val resId: Int,
    @DrawableRes val iconId: Int
) {
    object Soon : BottomBarItem("Soon", R.string.bottom_bar_soon, R.drawable.baseline_coronavirus_24)
    object Now : BottomBarItem("Now", R.string.bottom_bar_now, R.drawable.baseline_storefront_24)
}