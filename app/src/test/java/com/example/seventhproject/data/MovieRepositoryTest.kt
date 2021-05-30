package com.example.seventhproject.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.seventhproject.data.source.local.LocalDataSource
import com.example.seventhproject.data.source.local.entity.MoviesEntity
import com.example.seventhproject.data.source.local.entity.TvShowsEntity
import com.example.seventhproject.data.source.remote.RemoteDataSource
import com.example.seventhproject.utils.AppExecutors
import com.example.seventhproject.utils.LiveDataTestUtil
import com.example.seventhproject.utils.NotDataDummy
import com.example.seventhproject.utils.PagedListUtil
import com.example.seventhproject.vo.Resource
import org.junit.Test
import org.junit.Assert.*
import org.mockito.Mockito.*
import com.nhaarman.mockitokotlin2.verify
import org.junit.Rule
import org.mockito.Mockito


class MovieRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = mock(RemoteDataSource::class.java)
    private val local = mock(LocalDataSource::class.java)
    private val appExecutors = mock(AppExecutors::class.java)

    private val movieRepository = FakeMovieRepository(remote,local,appExecutors)

    private val movieResponses = NotDataDummy.generateRemoteMovies()
    private val tvShowsResponse = NotDataDummy.generateRemoteTvShows()



    @Test
    fun getAllMovies() {

        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MoviesEntity>
        `when`(local.getAllMovies()).thenReturn(dataSourceFactory)
        movieRepository.getAllMovies()

        val movieEntities = Resource.success(PagedListUtil.mockPagedList(NotDataDummy.generateMovies()))
        verify(local).getAllMovies()
        assertNotNull(movieEntities.data)
        assertEquals(movieResponses.size.toLong(), movieEntities.data?.size?.toLong())
    }

    @Test
    fun getAllTvShows() {

        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TvShowsEntity>
        `when`(local.getAllTvShows()).thenReturn(dataSourceFactory)
        movieRepository.getAllTvShows()

        val tvShowEntities = Resource.success(PagedListUtil.mockPagedList(NotDataDummy.generateTvShows()))
        verify(local).getAllTvShows()
        assertNotNull(tvShowEntities.data)
        assertEquals(tvShowsResponse.size.toLong(), tvShowEntities.data?.size?.toLong())
    }

    @Test
    fun getMovieById() {

        val dummyMovie = MutableLiveData<MoviesEntity>()
        dummyMovie.value= NotDataDummy.generateMovies()[0]
        `when`(local.getMovieById(NotDataDummy.generateMovies()[0].movieId)).thenReturn(dummyMovie)

        val data = LiveDataTestUtil.getValue(movieRepository.getMovieById(NotDataDummy.generateMovies()[0].movieId))
        Mockito.verify(local).getMovieById(NotDataDummy.generateMovies()[0].movieId)
        assertNotNull(data)
        assertEquals(NotDataDummy.generateMovies()[0].movieId, data.movieId)
    }

    @Test
    fun getTvShowById() {

        val dummyTvShow = MutableLiveData<TvShowsEntity>()
        dummyTvShow.value= NotDataDummy.generateTvShows()[0]
        `when`(local.getTvShowById(NotDataDummy.generateTvShows()[0].tvShowId)).thenReturn(dummyTvShow)

        val data = LiveDataTestUtil.getValue(movieRepository.getTvShowById(NotDataDummy.generateTvShows()[0].tvShowId))
        Mockito.verify(local).getTvShowById(NotDataDummy.generateTvShows()[0].tvShowId)
        assertNotNull(data)
        assertEquals(NotDataDummy.generateTvShows()[0].tvShowId, data.tvShowId)
    }

    @Test
    fun getFavoriteMovies() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MoviesEntity>
        `when`(local.getFavoriteMovies()).thenReturn(dataSourceFactory)
        movieRepository.getFavoriteMovies()

        val movieEntity = Resource.success(PagedListUtil.mockPagedList(NotDataDummy.generateMovies()))
        Mockito.verify(local).getFavoriteMovies()
        assertNotNull(movieEntity.data)
        assertEquals(NotDataDummy.generateMovies().size.toLong(), movieEntity.data?.size?.toLong())
    }

    @Test
    fun getFavoriteTvShows() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TvShowsEntity>
        `when`(local.getFavoriteTvShows()).thenReturn(dataSourceFactory)
        movieRepository.getFavoriteTvShows()

        val tvShowEntity = Resource.success(PagedListUtil.mockPagedList(NotDataDummy.generateTvShows()))
        Mockito.verify(local).getFavoriteTvShows()
        assertNotNull(tvShowEntity.data)
        assertEquals(NotDataDummy.generateTvShows().size.toLong(), tvShowEntity.data?.size?.toLong())
    }
}