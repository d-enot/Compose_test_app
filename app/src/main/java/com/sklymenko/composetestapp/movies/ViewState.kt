package com.sklymenko.composetestapp.movies

sealed class ViewState<out T> {
    data class Success<out T>(val data: T) : ViewState<T>()
    data class Error(val exception: Exception) : ViewState<Nothing>()
    object Loading : ViewState<Nothing>()
}