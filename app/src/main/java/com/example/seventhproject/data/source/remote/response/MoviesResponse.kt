package com.example.seventhproject.data.source.remote.response


data class MoviesResponse (
    var movieId: String,
    var title: String,
    var image:String,
    var synopsis:String,
    var releaseDate:String,
    var score : String
)