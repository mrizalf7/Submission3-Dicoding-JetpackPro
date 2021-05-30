package com.example.seventhproject.ui.tvshows

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.paging.PositionalDataSource
import com.example.seventhproject.data.MovieRepository
import com.example.seventhproject.data.source.local.entity.MoviesEntity
import com.example.seventhproject.data.source.local.entity.TvShowsEntity
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
class TvShowsViewModelTest {

    private lateinit var viewModel: TvShowsViewModel

    @Before
    fun setUp() {
        viewModel = TvShowsViewModel(movieRepository)
    }
    @Mock
    private lateinit var movieRepository: MovieRepository

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    @Mock
    private lateinit var observer: Observer<Resource<PagedList<TvShowsEntity>>>

    @Test
    fun getTvShows() {
        val notDummyTvShows = PagedTestDataSources.snapshot(NotDataDummy.generateTvShows())
        val tvShows = MutableLiveData<Resource<PagedList<TvShowsEntity>>>()
        tvShows.value = Resource.success((notDummyTvShows))

        `when`(movieRepository.getAllTvShows()).thenReturn(tvShows)
        val tvShowEntities = viewModel.getTvShows().value//viewModel is as an actual data that we want to test
        verify(movieRepository).getAllTvShows()
        assertNotNull(tvShowEntities)
        assertEquals(tvShows.value?.data?.size, tvShowEntities?.data?.size)
        assertEquals(tvShows.value?.data, tvShowEntities?.data)
        viewModel.getTvShows().observeForever(observer)
        verify(observer).onChanged(tvShows.value)
    }

    class PagedTestDataSources private constructor(private val items: List<TvShowsEntity>) :
        PositionalDataSource<TvShowsEntity>() {
        companion object {
            fun snapshot(items: List<TvShowsEntity> = listOf()): PagedList<TvShowsEntity> {
                return PagedList.Builder(PagedTestDataSources(items), 10)
                    .setNotifyExecutor(Executors.newSingleThreadExecutor())
                    .setFetchExecutor(Executors.newSingleThreadExecutor())
                    .build()
            }
        }

        override fun loadInitial(
            params: LoadInitialParams,
            callback: LoadInitialCallback<TvShowsEntity>
        ) {
            callback.onResult(items, 0, items.size)
        }

        override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<TvShowsEntity>) {
            val start = params.startPosition
            val end = params.startPosition + params.loadSize
            callback.onResult(items.subList(start, end))
        }
    }
}
