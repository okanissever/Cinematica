package com.example.movieapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.model.moviedetail.MovieDetails
import com.example.movieapp.repo.MovieRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class WatchListViewModel @Inject constructor(val repo : MovieRepo) : ViewModel() {

    private val watchList : MutableLiveData<List<MovieDetails>> = MutableLiveData()

    fun getAllMovie(){
        viewModelScope.launch(Dispatchers.IO) {
            val list = repo.getAllMovie()
            withContext(Dispatchers.Main) {
                watchList.value = list
            }
        }
        }

    fun deleteMovie(movie:MovieDetails){
        viewModelScope.launch {
            repo.deleteMovie(movie)
            getAllMovie()
        }
    }

    fun addMovie(movie : MovieDetails){
        viewModelScope.launch {
            repo.insertMovie(movie)
            getAllMovie()
        }
    }


    fun observeWatchList() : MutableLiveData<List<MovieDetails>>{
        return watchList
    }
    }
