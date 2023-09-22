package com.example.movieapp.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.movieapp.model.moviedetail.MovieDetails

@Database(entities = [MovieDetails::class], version = 2)
@TypeConverters(Converters::class)
abstract class MovieDatabase : RoomDatabase(){
    abstract fun movieDao(): MovieDao
}