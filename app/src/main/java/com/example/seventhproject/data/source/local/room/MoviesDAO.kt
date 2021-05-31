package com.example.seventhproject.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.example.seventhproject.data.source.local.entity.MoviesEntity
import com.example.seventhproject.data.source.local.entity.TvShowsEntity
import com.example.seventhproject.vo.Resource

@Dao
interface MoviesDAO {

    @Query("SELECT * FROM moviesEntity")
    fun getMovies(): DataSource.Factory<Int,MoviesEntity>

    @Query("SELECT * FROM tvShowsEntity")
    fun getTvShows(): DataSource.Factory<Int,TvShowsEntity>

    @Query("SELECT * FROM moviesEntity where favorite = 1")
    fun getFavoriteMovies(): DataSource.Factory<Int,MoviesEntity>

    @Query("SELECT * FROM tvShowsEntity where favorite = 1")
    fun getFavoriteTvShows(): DataSource.Factory<Int,TvShowsEntity>


    @Query("SELECT * FROM moviesEntity WHERE movieId = :movieId")
    fun getMovieById(movieId: String): LiveData<MoviesEntity>

    @Query("SELECT * FROM tvShowsEntity WHERE tvShowId = :tvShowId")
    fun getTvShowById(tvShowId: String): LiveData<TvShowsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies: List<MoviesEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTvShows(tvShows: List<TvShowsEntity>)

    @Update
    fun updateMovie(movie: MoviesEntity)

    @Update
    fun updateTvShow(tvShow: TvShowsEntity)

}