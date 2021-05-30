package com.example.seventhproject.ui.favorites.tvshows

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.example.seventhproject.data.MovieRepository
import com.example.seventhproject.data.source.local.entity.TvShowsEntity
import com.example.seventhproject.ui.favorites.movies.FavoriteMoviesViewModel
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FavoriteTvShowsViewModelTest {

    private lateinit var viewModel: FavoriteTvShowsViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieRepository: MovieRepository

    @Mock
    private lateinit var observerTvShow: Observer<PagedList<TvShowsEntity>>


    @Mock
    private lateinit var tvShowFavPagedList: PagedList<TvShowsEntity>



    @Before
    fun setUp() {
        viewModel = FavoriteTvShowsViewModel(movieRepository)
    }

    @Test
    fun getFavoriteTvShows() {

        val dummyMovie = tvShowFavPagedList
        Mockito.`when`(dummyMovie.size).thenReturn(4)
        val tvShow = MutableLiveData<PagedList<TvShowsEntity>>()
        tvShow.value = dummyMovie

        Mockito.`when`(movieRepository.getFavoriteTvShows()).thenReturn(tvShow)
        val movieEntity = viewModel.getFavoriteTvShows().value
        Mockito.verify(movieRepository).getFavoriteTvShows()
        assertNotNull(movieEntity)
        assertEquals(4, movieEntity?.size)

        viewModel.getFavoriteTvShows().observeForever(observerTvShow)
        Mockito.verify(observerTvShow).onChanged(dummyMovie)

    }
}