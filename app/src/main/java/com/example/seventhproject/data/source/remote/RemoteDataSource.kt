package com.example.seventhproject.data.source.remote

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.seventhproject.data.source.remote.response.MoviesResponse
import com.example.seventhproject.data.source.remote.response.TvShowsResponse
import com.example.seventhproject.utils.EspressoIdlingResource
import com.example.seventhproject.utils.JsonHelper

class RemoteDataSource private constructor(private val jsonHelper: JsonHelper) {

    private val handler = Handler(Looper.getMainLooper())


    companion object {

        private const val SERVICE_LATENCY_IN_MILLIS: Long = 2000

        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(helper: JsonHelper): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource(helper).apply { instance = this }
            }
    }

    fun getAllMovies(): LiveData<ApiResponse<List<MoviesResponse>>> {
        EspressoIdlingResource.increment()
        val resultMovie = MutableLiveData<ApiResponse<List<MoviesResponse>>>()
        handler.postDelayed({
            resultMovie.value = ApiResponse.success(jsonHelper.loadMovies())
            EspressoIdlingResource.decrement()
                            }, SERVICE_LATENCY_IN_MILLIS)
            return resultMovie
    }
    fun getAllTvShows():LiveData<ApiResponse<List<TvShowsResponse>>>{
        EspressoIdlingResource.increment()
        val resultTvShow = MutableLiveData<ApiResponse<List<TvShowsResponse>>>()
        handler.postDelayed({ resultTvShow.value= ApiResponse.success(jsonHelper.loadTvShows())
            EspressoIdlingResource.decrement()}, SERVICE_LATENCY_IN_MILLIS)
        return resultTvShow
    }

    fun getMovieById(movieId: String): LiveData<ApiResponse<MoviesResponse>> {
        EspressoIdlingResource.increment()
        val resultMovie = MutableLiveData<ApiResponse<MoviesResponse>>()
        handler.postDelayed({
            val movies = jsonHelper.loadMovies()

            for (movie in movies) {
                if (movie.movieId == movieId) {
                    val movieResult = MoviesResponse(movie.movieId,
                        movie.title,
                        movie.image,
                        movie.synopsis,
                        movie.releaseDate,
                        movie.score)
                    resultMovie.value = ApiResponse.success( movieResult )
                }
            }
            EspressoIdlingResource.decrement()
        }, SERVICE_LATENCY_IN_MILLIS)
        return resultMovie
    }


    fun getTvShowById(tvShowId: String): LiveData<ApiResponse<TvShowsResponse>> {
        EspressoIdlingResource.increment()
        val resultTvShow = MutableLiveData<ApiResponse<TvShowsResponse>>()
        handler.postDelayed({
            val tvShows = jsonHelper.loadTvShows()

            for (tvShow in tvShows) {
                if (tvShow.tvShowId== tvShowId) {
                    val tvShowResult = TvShowsResponse(tvShow.tvShowId,
                        tvShow.title,
                        tvShow.image,
                        tvShow.synopsis,
                        tvShow.firstEpisode,
                        tvShow.lastEpisode,
                        tvShow.totalEpisode,
                        tvShow.totalSeason,
                        tvShow.score)
                    resultTvShow.value = ApiResponse.success( tvShowResult )
                }
            }
            EspressoIdlingResource.decrement()
        }, SERVICE_LATENCY_IN_MILLIS)
        return resultTvShow
    }
}
