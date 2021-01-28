package com.sklymenko.composetestapp.source

import com.sklymenko.composetestapp.api.Movie
import com.sklymenko.composetestapp.api.Poster

class MovieLocalDataSource : MovieDataSource {

    private var soonMovies = mutableListOf<Movie>()
    private var nowMovies = mutableListOf<Movie>()

    override suspend fun getComingSoonMovies(): Result<List<Movie>> {
        return Result.Success(soonMovies)
    }

    override suspend fun getPlayingNowMovies(): Result<List<Movie>> {
        return Result.Success(nowMovies)
    }

    override suspend fun getMovieImages(movieId: String): Result<List<Poster>> {
        TODO("Not yet implemented")
    }

    override suspend fun setComingSoonMovies(movies: List<Movie>) {
        soonMovies.clear()
        soonMovies.addAll(movies)
    }

    override suspend fun setPlayingNowMovies(movies: List<Movie>) {
        nowMovies.clear()
        nowMovies.addAll(movies)
    }
}