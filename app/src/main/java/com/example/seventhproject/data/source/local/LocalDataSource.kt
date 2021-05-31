package com.example.seventhproject.data.source.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.PagedList
import androidx.room.*
import com.example.seventhproject.data.source.local.entity.MoviesEntity
import com.example.seventhproject.data.source.local.entity.TvShowsEntity
import com.example.seventhproject.data.source.local.room.MoviesDAO
import com.example.seventhproject.vo.Resource

class LocalDataSource private constructor(private val mMovieDao: MoviesDAO)  {

    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(movieDao: MoviesDAO): LocalDataSource =
            INSTANCE ?: LocalDataSource(movieDao)
    }

    fun getAllMovies(): DataSource.Factory<Int,MoviesEntity> =
        mMovieDao.getMovies()


    fun getAllTvShows(): DataSource.Factory<Int,TvShowsEntity> =
        mMovieDao.getTvShows()

    fun getFavoriteMovies(): DataSource.Factory<Int,MoviesEntity> =
        mMovieDao.getFavoriteMovies()

    fun getFavoriteTvShows(): DataSource.Factory<Int, TvShowsEntity> =
        mMovieDao.getFavoriteTvShows()

    fun getMovieById(movieId: String): LiveData<MoviesEntity> =
        mMovieDao.getMovieById(movieId)

    fun getTvShowById(tvShowId: String): LiveData<TvShowsEntity> =
        mMovieDao.getTvShowById(tvShowId)

    fun insertMovies(movies: List<MoviesEntity>) =
        mMovieDao.insertMovies(movies)


    fun insertTvShows(tvShows: List<TvShowsEntity>) =
        mMovieDao.insertTvShows(tvShows)

    fun setFavoriteMovie(movie : MoviesEntity) {
        movie.favorite = !movie.favorite
        mMovieDao.updateMovie(movie)
    }

    fun setFavoriteTvShow(tvShow : TvShowsEntity) {
        tvShow.favorite = !tvShow.favorite
        mMovieDao.updateTvShow(tvShow)
    }


}
