package com.example.seventhproject.ui.favorites.movies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.example.seventhproject.data.MovieRepository
import com.example.seventhproject.data.source.local.entity.MoviesEntity
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FavoriteMoviesViewModelTest {

    private lateinit var viewModel: FavoriteMoviesViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieRepository: MovieRepository

    @Mock
    private lateinit var observerMovie: Observer<PagedList<MoviesEntity>>


    @Mock
    private lateinit var movieFavPagedList: PagedList<MoviesEntity>



    @Before
    fun setUp() {
        viewModel = FavoriteMoviesViewModel(movieRepository)
    }

    @Test
    fun getFavoriteMovies() {

        val dummyMovie = movieFavPagedList
        Mockito.`when`(dummyMovie.size).thenReturn(4)
        val movie = MutableLiveData<PagedList<MoviesEntity>>()
        movie.value = dummyMovie

        Mockito.`when`(movieRepository.getFavoriteMovies()).thenReturn(movie)
        val movieEntity = viewModel.getFavoriteMovies().value
        Mockito.verify(movieRepository).getFavoriteMovies()
        assertNotNull(movieEntity)
        assertEquals(4, movieEntity?.size)

        viewModel.getFavoriteMovies().observeForever(observerMovie)
        Mockito.verify(observerMovie).onChanged(dummyMovie)

    }
}