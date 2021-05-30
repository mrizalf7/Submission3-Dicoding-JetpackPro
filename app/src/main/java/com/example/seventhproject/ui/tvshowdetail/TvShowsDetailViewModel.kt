package com.example.seventhproject.ui.tvshowdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.seventhproject.data.source.local.entity.TvShowsEntity
import com.example.seventhproject.data.MovieRepository
import com.example.seventhproject.data.source.local.entity.MoviesEntity
import com.example.seventhproject.vo.Resource

class TvShowsDetailViewModel(private val tvShowDetail: MovieRepository): ViewModel() {

    fun getTvShow(tvShowId:String): LiveData<TvShowsEntity> =
        tvShowDetail.getTvShowById(tvShowId)

    fun setFavoriteTvShow(tvShow: TvShowsEntity){
        tvShowDetail.setFavoriteTvShow(tvShow)
    }
}