package com.example.seventhproject.ui.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.seventhproject.data.source.local.entity.MoviesEntity
import com.example.seventhproject.data.MovieRepository
import com.example.seventhproject.vo.Resource

class MovieViewModel(private val movieRepository: MovieRepository): ViewModel() {

    fun getMovies(): LiveData<Resource<PagedList<MoviesEntity>>> = movieRepository.getAllMovies()
}