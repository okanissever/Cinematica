package com.example.movieapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.model.moviecast.MovieActors
import com.example.movieapp.model.moviedetail.MovieDetails
import com.example.movieapp.repo.MovieRepo
import com.example.movieapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActorViewModel @Inject constructor(val repo : MovieRepo) : ViewModel() {

    private val actorList : MutableLiveData<Resource<MovieActors>> = MutableLiveData()

    fun fetchActors(id : Int){
        actorList.value = Resource.Loading()

        viewModelScope.launch {
            try {
                val response = repo.getMovieActors(id)
                if(response.isSuccessful){
                    response.body()?.let {
                        actorList.value = Resource.Success(it)
                    }
                }else{
                    val errorMsg = response.errorBody()?.string() ?: "Unknown error"
                    actorList.value = Resource.Error(errorMsg)
                    Log.e("ActorViewModel", "API Error: $errorMsg")
                    println(errorMsg)
                }

            }catch (e : java.lang.Exception){
                actorList.value = Resource.Error(e.message.toString())
                Log.e("ActorViewModel", "Exception Error: ", e)
                println(e)

            }
        }
    }

    fun observeActorList() : MutableLiveData<Resource<MovieActors>>{
        return actorList
    }

}