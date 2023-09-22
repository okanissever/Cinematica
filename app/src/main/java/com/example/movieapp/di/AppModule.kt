package com.example.movieapp.di

import android.content.Context
import androidx.room.Room
import com.example.movieapp.BuildConfig
import com.example.movieapp.api.RetrofitAPI
import com.example.movieapp.local.MovieDatabase
import com.example.movieapp.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun injectRoomDatabase(@ApplicationContext context : Context) = Room.databaseBuilder(context,MovieDatabase::class.java,"movieDb").fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun injectDao(database: MovieDatabase) = database.movieDao()


    @Singleton
    @Provides
    fun injectRetrofitAPI(okHttpClient: OkHttpClient) : RetrofitAPI{
        return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(BASE_URL).client(okHttpClient).build().create(RetrofitAPI::class.java)
    }

}