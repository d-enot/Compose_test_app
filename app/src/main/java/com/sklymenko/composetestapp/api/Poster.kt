package com.sklymenko.composetestapp.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Poster(@Json(name = "file_path") val filePath: String)

@JsonClass(generateAdapter = true)
data class Posters(val posters: List<Poster>)