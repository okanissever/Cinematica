package com.example.movieapp.view.tabLayout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.movieapp.R
import com.example.movieapp.adapter.TabAdapter
import com.example.movieapp.adapter.WatchMovieAdapter
import com.example.movieapp.databinding.FragmentHomeBinding
import com.example.movieapp.databinding.FragmentNowPlayingBinding
import com.example.movieapp.util.Constants.NOW_PLAYING
import com.example.movieapp.util.Resource
import com.example.movieapp.util.viewBinding
import com.example.movieapp.view.HomeFragmentDirections
import com.example.movieapp.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NowPlayingFragment : Fragment(R.layout.fragment_now_playing) {
    private val binding by viewBinding(FragmentNowPlayingBinding::bind)
    private val viewModel: HomeViewModel by viewModels()

    private lateinit var adapter : TabAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = TabAdapter()
        binding.rvNowPlaying.adapter = adapter
        viewModel.fetchMovies(NOW_PLAYING)
        observeData()

    }


    private fun observeData(){
        viewModel.observeMovie().observe(viewLifecycleOwner){response->
            when(response){
                is Resource.Success->{
                    response.data?.let {newsResponse->
                        hideProgressBar()
                        adapter.submitList(newsResponse.results!!.toList())
                        adapter.onItemClick = {
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

    private var isLoading = false
    private fun hideProgressBar(){
        binding.progressBar.visibility = View.INVISIBLE
        binding.rvNowPlaying.visibility = View.VISIBLE
        isLoading = false
    }
    private fun showProgressBar(){
        binding.progressBar.visibility = View.VISIBLE
        binding.rvNowPlaying.visibility = View.GONE
        isLoading = true
    }
}