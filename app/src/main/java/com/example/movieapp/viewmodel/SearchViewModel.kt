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
class SearchViewModel @Inject constructor(val repo : MovieRepo) : ViewModel() {

    private val searchList : MutableLiveData<Resource<Movie>> = MutableLiveData()

    fun search(query : String){
        searchList.value = Resource.Loading()
        viewModelScope.launch {
               try {
                   val response = repo.getMovieSearch(query)
                   if(response.isSuccessful){
                       response.body()?.let {
                            searchList.value = Resource.Success(it)
                       }
                   }
               }catch (e : Exception){
                   searchList.value = Resource.Error(e.message.toString())
               }
        }
    }

    fun observeSearchList() : MutableLiveData<Resource<Movie>>{
        return searchList
    }
}