package com.sklymenko.composetestapp.source

import com.sklymenko.composetestapp.api.Movie
import com.sklymenko.composetestapp.api.Poster

interface MovieDataSource {

    suspend fun getComingSoonMovies(): Result<List<Movie>>

    suspend fun getPlayingNowMovies(): Result<List<Movie>>

    suspend fun getMovieImages(movieId: String): Result<List<Poster>>

    suspend fun setComingSoonMovies(movies: List<Movie>)

    suspend fun setPlayingNowMovies(movies: List<Movie>)
}