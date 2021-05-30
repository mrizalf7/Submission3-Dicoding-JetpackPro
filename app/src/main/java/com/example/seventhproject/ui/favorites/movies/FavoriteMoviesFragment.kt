package com.example.seventhproject.ui.favorites.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.seventhproject.databinding.FragmentFavoriteMoviesBinding
import com.example.seventhproject.viewmodel.ViewModelFactory


class FavoriteMoviesFragment : Fragment() {


    private lateinit var fragmentFavMovieBinding:FragmentFavoriteMoviesBinding
    val favMovieAdapter = FavoriteMoviesAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        fragmentFavMovieBinding =FragmentFavoriteMoviesBinding.inflate(layoutInflater, container, false)
        return fragmentFavMovieBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentFavMovieBinding.progressBar.visibility = View.VISIBLE
        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireActivity())
            val viewModel = ViewModelProvider(this, factory)[FavoriteMoviesViewModel::class.java]



            viewModel.getFavoriteMovies().observe(viewLifecycleOwner, { favMovies ->

                favMovieAdapter.submitList(favMovies)

            })
            fragmentFavMovieBinding.progressBar.visibility = View.GONE
            with(fragmentFavMovieBinding.rvFavMovie) {
                layoutManager = GridLayoutManager(requireContext(),3)
                setHasFixedSize(true)
                adapter = favMovieAdapter
            }
        }
    }
}