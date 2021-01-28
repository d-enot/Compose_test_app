package com.sklymenko.composetestapp

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sklymenko.composetestapp.api.Movie
import com.sklymenko.composetestapp.movies.ViewState
import com.sklymenko.composetestapp.source.MovieRepository
import com.sklymenko.composetestapp.source.Result
import kotlinx.coroutines.launch

class ComingSoonViewModel @ViewModelInject constructor(private val repository: MovieRepository) : ViewModel() {

    private val _movies: MutableLiveData<ViewState<List<Movie>>> = MutableLiveData()
    val movies: LiveData<ViewState<List<Movie>>> = _movies

    init {
        getComingSoonMovies()
    }

    private fun getComingSoonMovies() {
        _movies.value = ViewState.Loading
        viewModelScope.launch {
            when (val result = repository.getComingSoonMovies()) {
                is Result.Success -> _movies.value = ViewState.Success(result.data)
                is Result.Erorr -> _movies.value = ViewState.Error(result.exception)
            }
        }
    }
}

