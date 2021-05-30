package com.example.seventhproject.data.source.remote.response


data class TvShowsResponse (
    var tvShowId: String,
    var title: String,
    var image:String,
    var synopsis:String,
    var firstEpisode:String,
    var lastEpisode:String,
    var totalEpisode:String,
    var totalSeason:String,
    var score : String
)