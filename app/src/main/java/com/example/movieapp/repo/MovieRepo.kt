package com.example.movieapp.repo

import com.example.movieapp.api.RetrofitAPI
import com.example.movieapp.local.MovieDatabase
import com.example.movieapp.model.moviedetail.MovieDetails
import javax.inject.Inject
import com.example.movieapp.model.movielist.Result
import com.example.movieapp.util.Constants

class MovieRepo @Inject constructor(
    private val dao : MovieDatabase,
    private val api : RetrofitAPI) {

    suspend fun insertMovie(movie : MovieDetails) = dao.movieDao().insertMovie(movie)

    suspend fun deleteMovie(movie : MovieDetails) = dao.movieDao().deleteMovie(movie)

    fun getAllMovie() = dao.movieDao().getAll()

    suspend fun getMovies(movie: String,apiKey : String = Constants.API_KEY) = api.getMovie(movie,apiKey)

    suspend fun getMovieDetails(movieId: Int,apiKey : String = Constants.API_KEY) = api.getMovieDetails(movieId,apiKey)

    suspend fun getMovieActors(movieId: Int,apiKey : String = Constants.API_KEY) = api.getMovieActors(movieId = movieId,apiKey)

    suspend fun getMovieReviews(movieId: Int,apiKey : String = Constants.API_KEY) = api.getMovieReviews(movieId = movieId, apiKey = apiKey)

    suspend fun getMovieSearch(movieSearch: String,apiKey : String = Constants.API_KEY) = api.getMovieSearch(movieSearch, apiKey = apiKey)


}