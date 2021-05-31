package com.example.seventhproject.ui.favorites.tvshows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.seventhproject.databinding.FragmentFavoriteTvshowsBinding
import com.example.seventhproject.viewmodel.ViewModelFactory

class FavoriteTvShowsFragment: Fragment() {
    private lateinit var fragmentFavTvShowBinding: FragmentFavoriteTvshowsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        fragmentFavTvShowBinding = FragmentFavoriteTvshowsBinding.inflate(layoutInflater, container, false)
        return fragmentFavTvShowBinding.root
       
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentFavTvShowBinding.progressBar.visibility = View.VISIBLE
        if (activity != null) {
            val tvFavShowsAdapter = FavoriteTvShowsAdapter()
            val factory = ViewModelFactory.getInstance(requireActivity())
            val viewModel = ViewModelProvider(this,factory)[FavoriteTvShowsViewModel::class.java]


            viewModel.getFavoriteTvShows().observe(viewLifecycleOwner,{ tvShowsFav->
                            tvFavShowsAdapter.submitList(tvShowsFav)
            })
            fragmentFavTvShowBinding.progressBar.visibility = View.GONE

            with(fragmentFavTvShowBinding.rvFavTvShows) {
                layoutManager = GridLayoutManager(context,3)
                setHasFixedSize(true)
                adapter = tvFavShowsAdapter
            }
        }
    }
}