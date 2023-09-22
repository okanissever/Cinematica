package com.example.movieapp.view

import android.graphics.Typeface
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.movieapp.R
import com.example.movieapp.adapter.WatchMovieAdapter
import com.example.movieapp.databinding.FragmentHomeBinding
import com.example.movieapp.util.Constants.POPULAR
import com.example.movieapp.util.Resource
import com.example.movieapp.util.viewBinding
import com.example.movieapp.view.tabLayout.FragmentPageAdapter
import com.example.movieapp.viewmodel.HomeViewModel
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    private val binding by viewBinding(FragmentHomeBinding::bind)
    private val viewModel: HomeViewModel by viewModels()

    private lateinit var adapter : WatchMovieAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = WatchMovieAdapter()
        binding.topMoviesRV.adapter = adapter
        viewModel.fetchMovies(POPULAR)
        viewPager()
        observeData()

    }

    private fun observeData(){
        viewModel.observeMovie().observe(viewLifecycleOwner){response->
            when(response){
                is Resource.Success->{
                    response.data?.let {newsResponse->
                        hideProgressBar()
                        adapter.submitList(newsResponse.results!!.toList())
                        adapter.onItemClick={
                            val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(it.id!!)
                            findNavController().navigate(action)
                        }
                    }
                }
                is Resource.Loading ->{
                    showProgressBar()
                }
                is Resource.Error ->{
                    response.message?.let {
                        hideProgressBar()
                        println("Home Fragment $it")
                    }
                }
            }
        }

    }

    private fun viewPager(){
        binding.ViewPager.adapter = FragmentPageAdapter(requireActivity())
        TabLayoutMediator(binding.tabLayout,binding.ViewPager){tab,position->
            when(position){
                0->tab.text = "Now Playing"
                1->tab.text = "Upcoming"
                2->tab.text = "Top Rated"
            }
        }.attach()

    }


    private var isLoading = false
    private fun hideProgressBar(){
        binding.progressBar.visibility = View.INVISIBLE
        binding.topMoviesRV.visibility = View.VISIBLE
        binding.ViewPager.visibility = View.VISIBLE
        binding.tabLayout.visibility = View.VISIBLE
        isLoading = false
    }

    private fun showProgressBar(){
        binding.progressBar.visibility = View.VISIBLE
        binding.topMoviesRV.visibility = View.INVISIBLE
        binding.ViewPager.visibility = View.INVISIBLE
        binding.tabLayout.visibility = View.INVISIBLE
        isLoading = true
    }

}