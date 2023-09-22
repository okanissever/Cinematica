package com.example.movieapp.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.movieapp.model.moviedetail.MovieDetails
import retrofit2.Response


@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovie(movie : MovieDetails)

    @Delete
    suspend fun deleteMovie(movie : MovieDetails)

    @Query("SELECT * FROM movie")
    fun getAll() : List<MovieDetails>

}