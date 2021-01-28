package com.sklymenko.composetestapp

import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import com.sklymenko.composetestapp.api.Movie
import com.sklymenko.composetestapp.movies.ErrorView
import com.sklymenko.composetestapp.movies.LoadingView
import com.sklymenko.composetestapp.movies.ViewState
import com.sklymenko.composetestapp.ui.views.MovieCell

@Composable
fun NowScreen(viewModel: PlayingNowViewModel, itemClick: (movieId: Int) -> Unit) {
    val state: ViewState<List<Movie>> by viewModel.movies.observeAsState(ViewState.Loading)
    when (state) {
        is ViewState.Success -> PlayingNowList(
            movies = (state as ViewState.Success<List<Movie>>).data,
            modifier = Modifier,
            itemClick
        )
        is ViewState.Error -> ErrorView(errorTxt = (state as ViewState.Error).exception.message!!)
        ViewState.Loading -> LoadingView()
    }
}

@Composable
fun PlayingNowList(movies: List<Movie>, modifier: Modifier, itemClick: (movieId: Int) -> Unit) {
    LazyColumnFor(items = movies,
        itemContent = { item ->
            MovieCell(movie = item, modifier = modifier, itemClick)
        })
}