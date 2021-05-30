package com.example.seventhproject.ui.favorites.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.seventhproject.data.MovieRepository
import com.example.seventhproject.data.source.local.entity.MoviesEntity


class FavoriteMoviesViewModel(private val movieRepository: MovieRepository): ViewModel() {

    fun getFavoriteMovies(): LiveData<PagedList<MoviesEntity>> = movieRepository.getFavoriteMovies()
}