package com.sklymenko.composetestapp.movies

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sklymenko.composetestapp.ComingSoonViewModel
import com.sklymenko.composetestapp.api.Movie
import com.sklymenko.composetestapp.ui.views.MovieCell

@Composable
fun SoonScreen(viewModel: ComingSoonViewModel, itemClick: (movieId: Int) -> Unit) {
    val state: ViewState<List<Movie>> by viewModel.movies.observeAsState(ViewState.Loading)
    when (state) {
        is ViewState.Success -> SoonList(
            movies = (state as ViewState.Success<List<Movie>>).data,
            modifier = Modifier,
            itemClick = itemClick
        )
        is ViewState.Error -> ErrorView(errorTxt = (state as ViewState.Error).exception.message!!)
        ViewState.Loading -> LoadingView()
    }
}

@Composable
fun SoonList(movies: List<Movie>, modifier: Modifier, itemClick: (movieId: Int) -> Unit) {
    LazyColumnFor(items = movies,
        contentPadding = PaddingValues(start = 8.dp, end = 8.dp),
        itemContent = { item ->
            MovieCell(movie = item, modifier = modifier, itemClick = itemClick)
        })
}
