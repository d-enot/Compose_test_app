package com.sklymenko.composetestapp.source

import com.sklymenko.composetestapp.api.Movie
import com.sklymenko.composetestapp.api.Poster
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val remoteDataSource: MovieDataSource,
    private val localDataSource: MovieDataSource
) {

    suspend fun getComingSoonMovies(): Result<List<Movie>> {
        val localResult = localDataSource.getComingSoonMovies()
        return if (localResult is Result.Success && localResult.data.isNotEmpty()) {
            localResult
        } else {
            val remoteData = remoteDataSource.getComingSoonMovies()
            if (remoteData is Result.Success) {
                localDataSource.setComingSoonMovies(remoteData.data)
            }
            remoteData
        }
    }

    suspend fun getPlayingNowMovies(): Result<List<Movie>> {
        val localResult = localDataSource.getPlayingNowMovies()
        return if (localResult is Result.Success && localResult.data.isNotEmpty()) {
            localResult
        } else {
            val remoteData = remoteDataSource.getPlayingNowMovies()
            if (remoteData is Result.Success) {
                localDataSource.setPlayingNowMovies(remoteData.data)
            }
            remoteData
        }
    }

    suspend fun getMovieImages(movieId: Int): Result<List<Poster>> {
        return remoteDataSource.getMovieImages(movieId.toString())
    }
}

