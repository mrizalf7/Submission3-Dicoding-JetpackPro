package com.example.seventhproject.ui.tvshowdetail

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.seventhproject.R
import com.example.seventhproject.data.source.local.entity.TvShowsEntity
import com.example.seventhproject.databinding.ActivityTvShowDetailBinding
import com.example.seventhproject.viewmodel.ViewModelFactory
import com.example.seventhproject.vo.Status
import com.google.android.material.snackbar.Snackbar

class TvShowDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_TV_SHOWS = "extra_tv_shows"
    }

    private lateinit var activityTvShowDetailBinding : ActivityTvShowDetailBinding
    private lateinit var viewModel:TvShowsDetailViewModel
    private lateinit var tvShowDetails:TvShowsEntity
    private lateinit var tvShowId:String
    private var menu: Menu? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityTvShowDetailBinding = ActivityTvShowDetailBinding.inflate(layoutInflater)

        setContentView(activityTvShowDetailBinding.root)

        val actionbar = supportActionBar
        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[TvShowsDetailViewModel::class.java]

        val extras = intent.extras
        if (extras != null) {
            tvShowId = extras.getString(EXTRA_TV_SHOWS).toString()

            activityTvShowDetailBinding.progressBar.visibility = View.VISIBLE
            viewModel.getTvShow(tvShowId).observe(this, { tvShowDetail ->
                if (tvShowDetail != null) {
                        tvShowDetails=tvShowDetail
                       activityTvShowDetailBinding.progressBar.visibility = View.VISIBLE
                             actionbar?.title=tvShowDetail.title
                            activityTvShowDetailBinding.progressBar.visibility = View.GONE
                            populateTvShows(tvShowDetail)
                            activityTvShowDetailBinding.progressBar.visibility = View.GONE

                }
            })
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

     private fun populateTvShows(tvShowsEntity: TvShowsEntity) {
        activityTvShowDetailBinding.tvShowTitle.text = tvShowsEntity.title
        activityTvShowDetailBinding.tvShowSynopsis.text = tvShowsEntity.synopsis
        activityTvShowDetailBinding.tvShowFirstEpisode.text = resources.getString(R.string.first_episode,tvShowsEntity.firstEpisode)
        activityTvShowDetailBinding.tvShowLastEpisode.text = resources.getString(R.string.last_episode,tvShowsEntity.lastEpisode)
        activityTvShowDetailBinding.tvShowTotalEpisode.text = resources.getString(R.string.total_episode,tvShowsEntity.totalEpisode)
        activityTvShowDetailBinding.tvShowTotalSeason.text = resources.getString(R.string.total_season,tvShowsEntity.totalSeason)
        activityTvShowDetailBinding.tvShowScore.text = resources.getString(R.string.score,tvShowsEntity.score)

        Glide.with(this)
            .load(tvShowsEntity.image)
            .transform(RoundedCorners(20))
            .into(activityTvShowDetailBinding.tvShowDetailPoster)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        this.menu = menu
        viewModel.getTvShow(tvShowId).observe(this, { tvShowDetail ->
            val state = tvShowDetail.favorite
            setFavoriteTvShowState(state) })

        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_favorite) {
                if(tvShowDetails.favorite){
                    showSnackBar("${tvShowDetails.title} Removed from favorite")
                }
                   else{
                    showSnackBar("${tvShowDetails.title} Added to favorite")
                }
                    viewModel.setFavoriteTvShow(tvShowDetails)
                    return true
            }
        return super.onOptionsItemSelected(item)
    }

    private fun setFavoriteTvShowState(state: Boolean) {
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