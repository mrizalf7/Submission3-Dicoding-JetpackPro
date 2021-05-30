package com.example.seventhproject.ui.moviedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.seventhproject.data.source.local.entity.MoviesEntity
import com.example.seventhproject.data.MovieRepository


class MovieDetailViewModel(private val movieDetail: MovieRepository): ViewModel() {

    fun getMovie(movieId:String): LiveData<MoviesEntity> =
        movieDetail.getMovieById(movieId)

    fun setFavoriteMovie(movie: MoviesEntity){
        movieDetail.setFavoriteMovie(movie)
    }
}