package com.sklymenko.composetestapp.details

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.unit.dp
import com.sklymenko.composetestapp.EXTRA_MOVIE_ID
import com.sklymenko.composetestapp.PostersViewModel
import com.sklymenko.composetestapp.api.Poster
import com.sklymenko.composetestapp.movies.ErrorView
import com.sklymenko.composetestapp.movies.LoadingView
import com.sklymenko.composetestapp.movies.ViewState
import com.sklymenko.composetestapp.ui.ComposeTestAppTheme
import com.sklymenko.composetestapp.ui.views.NetworkImage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetails : AppCompatActivity() {

    private val viewModel: PostersViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent.let {
            val movieId = it.getIntExtra(EXTRA_MOVIE_ID, 0)
            viewModel.fetchMoviePosters(movieId)
        }
        setContent {
            ComposeTestAppTheme {
                DetailsScreen(viewModel) { this.finish() }
            }
        }
    }
}

@Composable
fun DetailsScreen(viewModel: PostersViewModel, backClick: () -> Unit) {
    val state: ViewState<List<Poster>> by viewModel.posters.observeAsState(ViewState.Loading)
    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(text = "Details")
            },
            elevation = 16.dp,
            navigationIcon = {
                IconButton(onClick = { backClick.invoke() }) {
                    Icon(Icons.Filled.ArrowBack)
                }
            }
        )
    }) {
        when (state) {
            is ViewState.Success -> {
                LazyGridFor(items = (state as ViewState.Success<List<Poster>>).data, 2) { item ->
                    NetworkImage(url = item.filePath, modifier = Modifier)
                }
            }
            is ViewState.Error -> ErrorView(errorTxt = (state as ViewState.Error).exception.message!!)
            ViewState.Loading -> LoadingView()
        }
    }
}

@Composable
fun <T> LazyGridFor(
    items: List<T>,
    rowSize: Int = 1,
    itemContent: @Composable BoxScope.(T) -> Unit,
) {
    val rows = items.chunked(rowSize)
    LazyColumnFor(rows) { row ->
        Row(Modifier.fillParentMaxWidth()) {
            for ((index, item) in row.withIndex()) {
                Box(Modifier.fillMaxWidth(1f / (rowSize - index)).padding(4.dp)) {
                    Card(elevation = 8.dp, ) {
                        itemContent(item)
                    }
                }
            }
        }
    }
}