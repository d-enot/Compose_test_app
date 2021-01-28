package com.sklymenko.composetestapp.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Movie(
    val id: Int,
    @Json(name = "poster_path") val posterPath: String?,
    val overview: String,
    @Json(name = "release_date") val releaseDate: String,
    val title: String,
    @Json(name = "vote_average") val vote: Float
)

@JsonClass(generateAdapter = true)
data class MovieResponse(val results: List<Movie>)