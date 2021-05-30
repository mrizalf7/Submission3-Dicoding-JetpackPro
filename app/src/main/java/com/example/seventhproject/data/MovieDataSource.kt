package com.example.seventhproject.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.example.seventhproject.data.source.local.entity.MoviesEntity
import com.example.seventhproject.data.source.local.entity.TvShowsEntity
import com.example.seventhproject.vo.Resource

interface MovieDataSource {

    fun getAllMovies(): LiveData<Resource<PagedList<MoviesEntity>>>

    fun getAllTvShows(): LiveData<Resource<PagedList<TvShowsEntity>>>

    fun getMovieById(movieId: String): LiveData<MoviesEntity>

    fun getTvShowById(tvShowId: String): LiveData<TvShowsEntity>

    fun getFavoriteMovies(): LiveData<PagedList<MoviesEntity>>

    fun getFavoriteTvShows(): LiveData<PagedList<TvShowsEntity>>

    fun setFavoriteMovie(movie: MoviesEntity)

    fun setFavoriteTvShow(tvShow: TvShowsEntity)





}







