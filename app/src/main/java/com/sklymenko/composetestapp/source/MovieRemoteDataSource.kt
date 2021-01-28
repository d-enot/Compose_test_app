package com.sklymenko.composetestapp.source

import com.sklymenko.composetestapp.api.Api
import com.sklymenko.composetestapp.api.Movie
import com.sklymenko.composetestapp.api.Poster
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieRemoteDataSource constructor(
    private val apiKey: String,
    private val api: Api,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : MovieDataSource {

    override suspend fun getComingSoonMovies(): Result<List<Movie>> = withContext(dispatcher) {
        return@withContext try {
            Result.Success(api.getComingSoon(apiKey, "en_EN", 1, "US").results)
        } catch (exception: Exception) {
            Result.Erorr(exception = exception)
        }
    }

    override suspend fun getPlayingNowMovies(): Result<List<Movie>> = withContext(dispatcher) {
        return@withContext try {
            Result.Success(api.getNowPlaying(apiKey, "en_EN", 1, "US").results)
        } catch (exception: Exception) {
            Result.Erorr(exception = exception)
        }
    }

    override suspend fun getMovieImages(movieId: String): Result<List<Poster>> = withContext(dispatcher) {
        return@withContext try {
            Result.Success(api.getMovieImages(movieId, apiKey, "en", "en-US").posters)
        } catch (exception: Exception) {
            Result.Erorr(exception = exception)
        }
    }

    override suspend fun setComingSoonMovies(movies: List<Movie>) {
        TODO("Not yet implemented")
    }

    override suspend fun setPlayingNowMovies(movies: List<Movie>) {
        TODO("Not yet implemented")
    }
}