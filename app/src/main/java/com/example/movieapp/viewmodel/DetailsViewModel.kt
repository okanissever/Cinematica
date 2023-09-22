package com.example.movieapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.model.moviedetail.MovieDetails
import com.example.movieapp.repo.MovieRepo
import com.example.movieapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    val repo : MovieRepo) : ViewModel() {

    private val movieDetails : MutableLiveData<Resource<MovieDetails>> = MutableLiveData()

    fun loadMovieDetails(id : Int){
        movieDetails.value = Resource.Loading()

        viewModelScope.launch {
            try {
                val response = repo.getMovieDetails(id)
                if(response.isSuccessful){
                    response.body()?.let {
                        movieDetails.value = Resource.Success(it)
                    }
                }else{
                    movieDetails.value = Resource.Error(response.message())
                }

            }catch (e : java.lang.Exception){
                movieDetails.value = Resource.Error(e.message.toString())
            }
        }
    }

    fun addMovie(movie : MovieDetails){
        viewModelScope.launch {
            repo.insertMovie(movie)
        }
    }

    fun observeMovieDetails() : MutableLiveData<Resource<MovieDetails>>{
        return movieDetails
    }

}