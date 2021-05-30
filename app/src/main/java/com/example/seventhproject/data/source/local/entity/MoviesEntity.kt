package com.example.seventhproject.data.source.local.entity


import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "moviesEntity")
data class MoviesEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "movieId")
    var movieId: String,
    @ColumnInfo(name="title")
    var title: String,
    @ColumnInfo(name="image")
    var image:String,
    @ColumnInfo(name="synopsis")
    var synopsis:String,
    @ColumnInfo(name="releaseDate")
    var releaseDate:String,
    @ColumnInfo(name="score")
    var score : String,
    @ColumnInfo(name="favorite")
    var favorite : Boolean = false
)

