package com.example.seventhproject.ui.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.seventhproject.R
import com.example.seventhproject.databinding.FragmentFavoriteBinding
import com.google.android.material.tabs.TabLayout


class FavoritesFragment : Fragment() {

    private lateinit var fragmentFavoriteBinding: FragmentFavoriteBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        fragmentFavoriteBinding= FragmentFavoriteBinding.inflate(layoutInflater, container, false)
        return fragmentFavoriteBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sectionsPagerAdapterFav = SectionsPagerAdapterFav( requireContext(), childFragmentManager)
       fragmentFavoriteBinding.viewPager.adapter = sectionsPagerAdapterFav
        fragmentFavoriteBinding.tabs.setupWithViewPager(fragmentFavoriteBinding.viewPager)
        (activity as AppCompatActivity).supportActionBar?.title = "Favorites"


//        val viewPager = fragmentFavoriteBinding.viewPager
//        val tabLayout = fragmentFavoriteBinding.tabs
//        val viewPagerAdapter =
//            activity?.let { SectionsPagerAdapterFav(it, childFragmentManager) }
//
//        viewPager.adapter = viewPagerAdapter
//        tabLayout.setupWithViewPager(viewPager)
    }
}