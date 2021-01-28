package com.sklymenko.composetestapp.source

sealed class Result<out T> {

    data class Success<out T>(val data: T) : Result<T>()
    data class Erorr(val exception: Exception) : Result<Nothing>()
}