package com.example.movieapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.model.moviereviews.AuthorResult
import com.example.movieapp.model.moviereviews.Reviews
import com.example.movieapp.repo.MovieRepo
import com.example.movieapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ReviewsViewModel @Inject constructor(val repo : MovieRepo) : ViewModel() {

    private val reviewList : MutableLiveData<Resource<Reviews>> = MutableLiveData()

    fun fetchAuthor(movieId : Int){
        reviewList.value = Resource.Loading()
        viewModelScope.launch {
            try {
                val response = repo.getMovieReviews(movieId)
                if(response.isSuccessful){
                    response.body()?.let{
                        reviewList.value = Resource.Success(it)
                    }
                }else{
                    reviewList.value = Resource.Error(response.message().toString())
                }
            }catch (e : java.lang.Exception){
                reviewList.value = Resource.Error(e.message.toString())
            }
        }
    }

    fun observeReviewsList() : MutableLiveData<Resource<Reviews>>{
        return reviewList
    }



}