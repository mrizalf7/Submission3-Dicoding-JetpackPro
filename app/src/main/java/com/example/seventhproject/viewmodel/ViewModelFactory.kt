package com.example.seventhproject.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.seventhproject.data.MovieRepository
import com.example.seventhproject.di.Injection
import com.example.seventhproject.ui.favorites.movies.FavoriteMoviesViewModel
import com.example.seventhproject.ui.favorites.tvshows.FavoriteTvShowsViewModel
import com.example.seventhproject.ui.moviedetail.MovieDetailViewModel
import com.example.seventhproject.ui.movies.MovieViewModel
import com.example.seventhproject.ui.tvshowdetail.TvShowsDetailViewModel
import com.example.seventhproject.ui.tvshows.TvShowsViewModel

class ViewModelFactory private constructor(private val mMovieRepository: MovieRepository) : ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context)).apply {
                    instance = this
                }
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(MovieViewModel::class.java) -> {
                return MovieViewModel(mMovieRepository) as T
            }
            modelClass.isAssignableFrom(MovieDetailViewModel::class.java) -> {
                return MovieDetailViewModel(mMovieRepository) as T
            }
            modelClass.isAssignableFrom(TvShowsViewModel::class.java) -> {
                return TvShowsViewModel(mMovieRepository) as T
            }
            modelClass.isAssignableFrom(TvShowsDetailViewModel::class.java) -> {
                return TvShowsDetailViewModel(mMovieRepository) as T
            }
            modelClass.isAssignableFrom(FavoriteMoviesViewModel::class.java) -> {
                return FavoriteMoviesViewModel(mMovieRepository) as T
            }
            modelClass.isAssignableFrom(FavoriteTvShowsViewModel::class.java) -> {
                return FavoriteTvShowsViewModel(mMovieRepository) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }

    }
}













