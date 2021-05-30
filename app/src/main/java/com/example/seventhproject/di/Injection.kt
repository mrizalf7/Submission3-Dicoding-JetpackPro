package com.example.seventhproject.di

import android.content.Context
import com.example.seventhproject.data.MovieRepository
import com.example.seventhproject.data.source.local.LocalDataSource
import com.example.seventhproject.data.source.local.room.MovieDatabase
import com.example.seventhproject.data.source.remote.RemoteDataSource
import com.example.seventhproject.utils.AppExecutors
import com.example.seventhproject.utils.JsonHelper

object Injection {
    fun provideRepository(context: Context): MovieRepository {
        val database = MovieDatabase.getInstance(context)
        val localDataSource = LocalDataSource.getInstance(database.movieDao())
        val appExecutors = AppExecutors()
        val remoteDataSource = RemoteDataSource.getInstance(JsonHelper(context))

        return MovieRepository.getInstance(remoteDataSource,localDataSource,appExecutors)
    }
}
