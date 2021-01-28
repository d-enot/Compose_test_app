package com.sklymenko.composetestapp.api

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @GET("movie/upcoming")
    suspend fun getComingSoon(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int,
        @Query("region") region: String
    ): MovieResponse

    @GET("movie/now_playing")
    suspend fun getNowPlaying(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int,
        @Query("region") region: String
    ): MovieResponse

    @GET("movie/{movie_id}/images")
    suspend fun getMovieImages(
        @Path("movie_id") id: String,
        @Query("api_key") apiKey: String,
        @Query("include_image_language") includeLanguage: String,
        @Query("language") language: String
    ): Posters

    companion object {
        var BASE_URL = "https://api.themoviedb.org/3/"
        var IMG_URL = "https://image.tmdb.org/t/p/original"
    }
}