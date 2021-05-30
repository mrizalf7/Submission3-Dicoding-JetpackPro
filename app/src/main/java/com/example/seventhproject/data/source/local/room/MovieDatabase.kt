package com.example.seventhproject.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.seventhproject.data.source.local.entity.MoviesEntity
import com.example.seventhproject.data.source.local.entity.TvShowsEntity

@Database(entities = [MoviesEntity::class, TvShowsEntity::class],
    version = 1,
    exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MoviesDAO

    companion object {

        @Volatile
        private var INSTANCE: MovieDatabase? = null

        fun getInstance(context: Context): MovieDatabase =
            INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    MovieDatabase::class.java,
                    "Movies.db"
                )
                    .build().apply {
                    INSTANCE = this
                }
            }
    }
}