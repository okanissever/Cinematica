package com.example.movieapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.model.movielist.Movie
import com.example.movieapp.repo.MovieRepo
import com.example.movieapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repo : MovieRepo) : ViewModel() {

    private val movie : MutableLiveData<Resource<Movie>> = MutableLiveData()

    fun fetchMovies(movies : String){
        movie.value = Resource.Loading()
        try {
            viewModelScope.launch {
                val response = repo.getMovies(movies)
                if(response.isSuccessful){
                    response.body()?.let {
                        movie.value = Resource.Success(it)
                    }
                }
                else{
                    movie.value = Resource.Error(response.message().toString())
                }
            }
        }catch (e : java.lang.Exception){
            movie.value = Resource.Error(e.message.toString(),null)
        }
    }

    fun observeMovie() : MutableLiveData<Resource<Movie>>{
        return movie
    }

}