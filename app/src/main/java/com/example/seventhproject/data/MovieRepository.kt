package com.example.seventhproject.data

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.seventhproject.data.source.local.LocalDataSource
import com.example.seventhproject.data.source.local.entity.MoviesEntity
import com.example.seventhproject.data.source.local.entity.TvShowsEntity
import com.example.seventhproject.data.source.remote.RemoteDataSource
import com.example.seventhproject.data.source.remote.ApiResponse
import com.example.seventhproject.data.source.remote.response.MoviesResponse
import com.example.seventhproject.data.source.remote.response.TvShowsResponse
import com.example.seventhproject.utils.AppExecutors
import com.example.seventhproject.vo.Resource

class MovieRepository private constructor
    (private val remoteDataSource: RemoteDataSource
    ,private val localDataSource:LocalDataSource
     ,private val appExecutors:AppExecutors) :MovieDataSource {

    companion object {
        @Volatile
        private var instance: MovieRepository? = null
        fun getInstance(
            remoteData: RemoteDataSource, localData: LocalDataSource,
            appExecutors: AppExecutors
        ): MovieRepository =
            instance ?: synchronized(this) {
                instance ?: MovieRepository(remoteData, localData, appExecutors).apply {
                    instance = this
                }
            }
    }

    override fun getAllMovies(): LiveData<Resource<PagedList<MoviesEntity>>> {
        return object :
            NetworkBoundResource<PagedList<MoviesEntity>, List<MoviesResponse>>(appExecutors) {
            public override fun loadFromDB(): LiveData<PagedList<MoviesEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(localDataSource.getAllMovies(), config).build()
            }

            override fun shouldFetch(data: PagedList<MoviesEntity>?): Boolean =
                data == null

            public override fun createCall(): LiveData<ApiResponse<List<MoviesResponse>>> =
                remoteDataSource.getAllMovies()

            public override fun saveCallResult(data: List<MoviesResponse>) {

                val movieList = ArrayList<MoviesEntity>()
                for (response in data) {
                    val movie = MoviesEntity(
                        response.movieId,
                        response.title,
                        response.image,
                        response.synopsis,
                        response.releaseDate,
                        response.score
                    )
                    movieList.add(movie)
                }
                localDataSource.insertMovies(movieList)
            }
        }.asLiveData()
    }

    override fun getAllTvShows(): LiveData<Resource<PagedList<TvShowsEntity>>> {
        return object :
            NetworkBoundResource<PagedList<TvShowsEntity>, List<TvShowsResponse>>(appExecutors) {
            public override fun loadFromDB(): LiveData<PagedList<TvShowsEntity>> {
                val config = PagedList.Config.Builder().apply {
                    setEnablePlaceholders(false)
                    setInitialLoadSizeHint(4)
                    setPageSize(4)
                }.build()
                return LivePagedListBuilder(localDataSource.getAllTvShows(), config).build()
            }

            override fun shouldFetch(data: PagedList<TvShowsEntity>?): Boolean =
                data == null

            public override fun createCall(): LiveData<ApiResponse<List<TvShowsResponse>>> =
                remoteDataSource.getAllTvShows()

            public override fun saveCallResult(data: List<TvShowsResponse>) {

                val tvList = ArrayList<TvShowsEntity>()

                for (response in data) {
                    val tvShow = TvShowsEntity(
                        response.tvShowId,
                        response.title,
                        response.image,
                        response.synopsis,
                        response.firstEpisode,
                        response.lastEpisode,
                        response.totalEpisode,
                        response.totalSeason,
                        response.score
                    )
                    tvList.add(tvShow)
                }
                localDataSource.insertTvShows(tvList)
            }
        }.asLiveData()
    }

    override fun getMovieById(movieId: String): LiveData<MoviesEntity> =
        localDataSource.getMovieById(movieId)

    override fun getTvShowById(tvShowId: String): LiveData<TvShowsEntity> =
        localDataSource.getTvShowById(tvShowId)

    override fun getFavoriteMovies(): LiveData<PagedList<MoviesEntity>> {
        val config = PagedList.Config.Builder().apply {
            setEnablePlaceholders(false)
            setInitialLoadSizeHint(4)
            setPageSize(4)
        }.build()
        return LivePagedListBuilder(localDataSource.getFavoriteMovies(), config).build()
    }

    override fun getFavoriteTvShows(): LiveData<PagedList<TvShowsEntity>> {
        val config = PagedList.Config.Builder().apply {
            setEnablePlaceholders(false)
            setInitialLoadSizeHint(4)
            setPageSize(4)
        }.build()
        return LivePagedListBuilder(localDataSource.getFavoriteTvShows(), config).build()
    }

    override fun setFavoriteMovie(movie: MoviesEntity) {
        appExecutors.diskIO().execute{localDataSource.setFavoriteMovie(movie)}
    }

    override fun setFavoriteTvShow(tvShow: TvShowsEntity) {
        appExecutors.diskIO().execute {
            localDataSource.setFavoriteTvShow(tvShow)
        }
    }
}