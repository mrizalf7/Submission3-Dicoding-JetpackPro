package com.example.seventhproject.ui.movies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.paging.PositionalDataSource
import com.example.seventhproject.data.MovieRepository
import com.example.seventhproject.data.source.local.entity.MoviesEntity
import com.example.seventhproject.utils.NotDataDummy
import com.example.seventhproject.vo.Resource
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.Executors

@RunWith(MockitoJUnitRunner::class)
class MovieViewModelTest {

    private lateinit var viewModel: MovieViewModel

    @Before
    fun setUp() {
        viewModel = MovieViewModel(movieRepository)
    }

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var observer: Observer<Resource<PagedList<MoviesEntity>>>

    @Mock
    private lateinit var movieRepository: MovieRepository

    @Test
    fun getMovies() {
        val notDummyMovies = PagedTestDataSources.snapshot(NotDataDummy.generateMovies())
        val movies = MutableLiveData<Resource<PagedList<MoviesEntity>>>()
        movies.value = Resource.success((notDummyMovies))

        `when`(movieRepository.getAllMovies()).thenReturn(movies)
        val movieEntities = viewModel.getMovies().value
        verify(movieRepository).getAllMovies()
        assertNotNull(movieEntities)
        assertEquals(movies.value?.data?.size, movieEntities?.data?.size)
        assertEquals(movies.value?.data, movieEntities?.data)
        viewModel.getMovies().observeForever(observer)
        verify(observer).onChanged(movies.value)
    }


    class PagedTestDataSources private constructor(private val items: List<MoviesEntity>) :
        PositionalDataSource<MoviesEntity>() {
        companion object {
            fun snapshot(items: List<MoviesEntity> = listOf()): PagedList<MoviesEntity> {
                return PagedList.Builder(PagedTestDataSources(items), 10)
                    .setNotifyExecutor(Executors.newSingleThreadExecutor())
                    .setFetchExecutor(Executors.newSingleThreadExecutor())
                    .build()
            }
        }

        override fun loadInitial(
            params: LoadInitialParams,
            callback: LoadInitialCallback<MoviesEntity>
        ) {
            callback.onResult(items, 0, items.size)
        }

        override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<MoviesEntity>) {
            val start = params.startPosition
            val end = params.startPosition + params.loadSize
            callback.onResult(items.subList(start, end))
        }
    }
}