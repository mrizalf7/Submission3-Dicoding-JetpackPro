package com.example.seventhproject.ui.moviedetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.seventhproject.R
import com.example.seventhproject.data.source.local.entity.MoviesEntity
import com.example.seventhproject.databinding.ActivityMovieDetailBinding
import com.example.seventhproject.viewmodel.ViewModelFactory
import com.google.android.material.snackbar.Snackbar

class MovieDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_MOVIE = "extra_movie"
    }
    private lateinit var movieId : String
    private lateinit var viewModel : MovieDetailViewModel
    private lateinit var movieDetailBinding:ActivityMovieDetailBinding
    private lateinit var movieDetails : MoviesEntity
    private var menu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieDetailBinding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(movieDetailBinding.root)
        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this,factory)[MovieDetailViewModel::class.java]

        val actionbar = supportActionBar
        val extras = intent.extras
        if (extras != null) {

            movieId = extras.getString(EXTRA_MOVIE).toString()

            actionbar?.title=null
            movieDetailBinding.progressBar.visibility= View.VISIBLE
            movieDetailBinding.movieDetailPoster.visibility=View.INVISIBLE

            viewModel.getMovie(movieId).observe(this,{movieDetail->

                            movieDetails = movieDetail
                            populateMovie(movieDetail)
                            actionbar?.title=movieDetail.title
                            movieDetailBinding.progressBar.visibility=View.INVISIBLE
                            movieDetailBinding.movieDetailPoster.visibility=View.VISIBLE


                })
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    private fun populateMovie(moviesEntity:MoviesEntity) {

        movieDetailBinding.movieTitle.text = moviesEntity.title
        movieDetailBinding.movieSynopsis.text = moviesEntity.synopsis
        movieDetailBinding.movieReleaseDate.text = resources.getString(R.string.release_date,moviesEntity.releaseDate)
        movieDetailBinding.movieScore.text = resources.getString(R.string.score,moviesEntity.score)


        Glide.with(this)
            .load(moviesEntity.image)
            .transform(RoundedCorners(120))
            .into(movieDetailBinding.movieDetailPoster)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_detail, menu)
        this.menu = menu
        viewModel.getMovie(movieId).observe(this, { movieDetail ->
        val state = movieDetail.favorite
        setFavoriteState(state)})
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_favorite) {
                if (movieDetails.favorite) {
                    showSnackBar("${movieDetails.title} Removed from favorite")
                } else {
                    showSnackBar("${movieDetails.title} Added to favorite")
                }
            viewModel.setFavoriteMovie(movieDetails)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setFavoriteState(state: Boolean) {
        if (menu == null) return
        val menuItem = menu?.findItem(R.id.action_favorite)
        if (state) {

            menuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_true)
        } else {

            menuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_false)
        }
    }

    private fun showSnackBar(msg: String) {
        Snackbar.make(findViewById(android.R.id.content), msg, Snackbar.LENGTH_SHORT).show()
    }
    override fun onSupportNavigateUp(): Boolean {
        finish()
        onBackPressed()
        return super.onSupportNavigateUp()
    }

}