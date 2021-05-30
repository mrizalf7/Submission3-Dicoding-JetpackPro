package com.example.seventhproject.ui.tvshows

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.seventhproject.databinding.FragmentTvShowsBinding
import com.example.seventhproject.viewmodel.ViewModelFactory
import com.example.seventhproject.vo.Status


class TvShowsFragment: Fragment() {

    private lateinit var fragmentTvShowBinding: FragmentTvShowsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        fragmentTvShowBinding = FragmentTvShowsBinding.inflate(layoutInflater, container, false)
        return fragmentTvShowBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            val tvShowsAdapter = TvShowsAdapter()
            val factory = ViewModelFactory.getInstance(requireActivity())
            val viewModel = ViewModelProvider(this,factory)[TvShowsViewModel::class.java]
            (activity as AppCompatActivity).supportActionBar?.title = "TvShows"

            viewModel.getTvShows().observe(viewLifecycleOwner,{ tvShows->
                if(tvShows!=null){
                    when(tvShows.status){
                        Status.LOADING ->fragmentTvShowBinding.progressBar.visibility = View.VISIBLE
                        Status.SUCCESS -> {
                            fragmentTvShowBinding.progressBar.visibility = View.GONE
                            tvShowsAdapter.submitList(tvShows.data)
                        }
                        Status.ERROR ->{
                            fragmentTvShowBinding.progressBar.visibility = View.GONE
                            Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })

            with(fragmentTvShowBinding.rvTvShows) {
                layoutManager = GridLayoutManager(requireContext(),3)
                setHasFixedSize(true)
                adapter = tvShowsAdapter
            }
        }
    }
}