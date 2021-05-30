package com.example.seventhproject.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "tvShowsEntity")
data class TvShowsEntity(

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "tvShowId")
    var tvShowId: String,
    @ColumnInfo(name = "title")
    var title: String,
    @ColumnInfo(name = "image")
    var image:String,
    @ColumnInfo(name = "synopsis")
    var synopsis:String,
    @ColumnInfo(name = "firstEpisode")
    var firstEpisode:String,
    @ColumnInfo(name = "lastEpisode")
    var lastEpisode:String,
    @ColumnInfo(name = "totalEpisode")
    var totalEpisode:String,
    @ColumnInfo(name = "totalSeason")
    var totalSeason:String,
    @ColumnInfo(name = "score")
    var score : String,
    @ColumnInfo(name="favorite")
    var favorite : Boolean = false
)

