package com.example.seventhproject.ui.movies


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.seventhproject.databinding.FragmentMovieBinding
import com.example.seventhproject.viewmodel.ViewModelFactory
import com.example.seventhproject.vo.Status

class MoviesFragment: Fragment() {

    private lateinit var fragmentMovieBinding: FragmentMovieBinding
    var movieAdapter = MoviesAdapter()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        fragmentMovieBinding = FragmentMovieBinding.inflate(layoutInflater, container, false)
        return fragmentMovieBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireActivity())
            val viewModel = ViewModelProvider(this, factory)[MovieViewModel::class.java]
            (activity as AppCompatActivity).supportActionBar?.title = "Movies"

            viewModel.getMovies().observe(viewLifecycleOwner, { movies ->

                if(movies.data!==null) {

                    when(movies.status){
                        Status.LOADING->fragmentMovieBinding.progressBar.visibility = View.VISIBLE
                        Status.SUCCESS->{
                            fragmentMovieBinding.progressBar.visibility = View.GONE
                            movieAdapter.submitList(movies.data)

                        }
                        Status.ERROR->{
                            fragmentMovieBinding.progressBar.visibility = View.GONE
                            Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })
            with(fragmentMovieBinding.rvMovie) {
                layoutManager = GridLayoutManager(context,3)
                setHasFixedSize(true)
                adapter = movieAdapter
            }
        }

    }

    override fun onResume() {
        super.onResume()
        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireActivity())
            val viewModel = ViewModelProvider(this, factory)[MovieViewModel::class.java]
            (activity as AppCompatActivity).supportActionBar?.title = "Movies"

            viewModel.getMovies().observe(viewLifecycleOwner, { movies ->

                if(movies.data!==null) {

                    when(movies.status){
                        Status.LOADING->fragmentMovieBinding.progressBar.visibility = View.VISIBLE
                        Status.SUCCESS->{
                            fragmentMovieBinding.progressBar.visibility = View.GONE
                            movieAdapter.submitList(movies.data)

                        }
                        Status.ERROR->{
                            fragmentMovieBinding.progressBar.visibility = View.GONE
                            Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })
            with(fragmentMovieBinding.rvMovie) {
                layoutManager = GridLayoutManager(context,3)
                setHasFixedSize(true)
                adapter = movieAdapter
            }
        }

    }
}