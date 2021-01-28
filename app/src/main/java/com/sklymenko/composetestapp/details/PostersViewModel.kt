package com.sklymenko.composetestapp

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sklymenko.composetestapp.api.Poster
import com.sklymenko.composetestapp.movies.ViewState
import com.sklymenko.composetestapp.source.MovieRepository
import com.sklymenko.composetestapp.source.Result
import kotlinx.coroutines.launch

class PostersViewModel @ViewModelInject constructor(private val repository: MovieRepository) : ViewModel() {

    private val _posters: MutableLiveData<ViewState<List<Poster>>> = MutableLiveData()
    val posters: LiveData<ViewState<List<Poster>>> = _posters

    fun fetchMoviePosters(movieId: Int) {
        _posters.value = ViewState.Loading
        viewModelScope.launch {
            when (val result = repository.getMovieImages(movieId)) {
                is Result.Success -> _posters.value = ViewState.Success(result.data)
                is Result.Erorr -> _posters.value = ViewState.Error(result.exception)
            }
        }
    }
}

