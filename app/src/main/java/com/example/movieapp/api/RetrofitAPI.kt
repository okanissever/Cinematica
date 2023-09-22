package com.example.movieapp.api

import com.example.movieapp.model.moviecast.MovieActors
import com.example.movieapp.model.moviedetail.MovieDetails
import com.example.movieapp.model.movielist.Movie
import com.example.movieapp.model.moviereviews.AuthorResult
import com.example.movieapp.model.moviereviews.Reviews
import com.example.movieapp.util.Constants.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitAPI {

    @GET("movie/{movie}")
    suspend fun getMovie(
        @Path("movie") movie: String,
        @Query("api_key") apiKey : String = API_KEY) : Response<Movie>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey : String = API_KEY) : Response<MovieDetails>

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieActors(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey : String = API_KEY) : Response<MovieActors>

    @GET("movie/{movie_id}/reviews")
    suspend fun getMovieReviews(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey : String = API_KEY) : Response<Reviews>

    @GET("search/movie")
    suspend fun getMovieSearch(
        @Query("query") query : String,
        @Query("api_key") apiKey : String = API_KEY) : Response<Movie>



}