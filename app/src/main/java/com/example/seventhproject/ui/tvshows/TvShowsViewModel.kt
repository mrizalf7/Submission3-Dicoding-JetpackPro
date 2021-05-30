package com.example.seventhproject.ui.tvshows

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.seventhproject.data.source.local.entity.TvShowsEntity
import com.example.seventhproject.data.MovieRepository
import com.example.seventhproject.vo.Resource

class TvShowsViewModel(private val tvShowRepository: MovieRepository): ViewModel() {

    fun getTvShows(): LiveData<Resource<PagedList<TvShowsEntity>>> = tvShowRepository.getAllTvShows()

}