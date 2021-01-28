package com.sklymenko.composetestapp.ui.views

import android.R
import android.widget.RatingBar
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.sklymenko.composetestapp.api.Movie

@Composable
fun MovieCell(movie: Movie, modifier: Modifier, itemClick: (movieId: Int) -> Unit) {
    Card(elevation = 8.dp, modifier = modifier.clickable(onClick = { itemClick.invoke(movie.id) })) {
        Row(modifier = Modifier.padding(8.dp)) {
            NetworkImage(url = movie.posterPath, modifier = modifier.width(80.dp).height(120.dp))
            Column(modifier = modifier.padding(start = 8.dp)) {
                Text(text = movie.title, modifier = modifier.fillMaxWidth())
                Row(modifier = modifier.fillMaxWidth()) {
                    AndroidView(viewBlock = { context ->
                        RatingBar(context, null, R.attr.ratingBarStyleSmall).apply {
                            numStars = 5
                            stepSize = 0.5f
                            rating = movie.vote / 2
                        }
                    })
                    Text(
                        text = "${movie.vote}/10",
                        modifier = modifier.padding(start = 8.dp),
                        style = MaterialTheme.typography.h1
                    )
                    Box(modifier = modifier.fillMaxWidth().wrapContentSize(Alignment.CenterEnd)) {
                        Text(text = movie.releaseDate, style = MaterialTheme.typography.h1)
                    }
                }
                Text(
                    text = movie.overview,
                    textAlign = TextAlign.Justify,
                    modifier = modifier.padding(top = 4.dp),
                    maxLines = 5,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.h1
                )
            }
        }
    }
    Spacer(modifier = modifier.fillMaxWidth().heightIn(4.dp))
}