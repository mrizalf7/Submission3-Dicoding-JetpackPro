package com.example.seventhproject.ui.favorites.tvshows

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.seventhproject.data.MovieRepository
import com.example.seventhproject.data.source.local.entity.TvShowsEntity

class FavoriteTvShowsViewModel(private val tvShowRepository: MovieRepository): ViewModel() {

    fun getFavoriteTvShows(): LiveData<PagedList<TvShowsEntity>> = tvShowRepository.getFavoriteTvShows()
}