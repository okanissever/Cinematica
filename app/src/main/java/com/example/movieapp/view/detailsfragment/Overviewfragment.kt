package com.example.movieapp.view.detailsfragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentOverviewBinding
import com.example.movieapp.util.Resource
import com.example.movieapp.util.viewBinding
import com.example.movieapp.viewmodel.DetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Overviewfragment : Fragment(R.layout.fragment_overview) {

    private val binding by viewBinding(FragmentOverviewBinding::bind)
    private val viewModel: DetailsViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movieId = arguments?.getInt("movieId")

        if (movieId != null) {
            viewModel.loadMovieDetails(movieId)
        }
        else{
            println(movieId)
        }

        observeData()

    }

    private fun observeData(){
        viewModel.observeMovieDetails().observe(viewLifecycleOwner){response->
            when(response){
                is Resource.Success->{
                    response.data?.let {
                        binding.overViewTV.text = it.overview
                    }
                }
                is Resource.Error->{
                    response.message?.let {
                        Log.e("OverviewFragment",it)
                    }
                }
                is Resource.Loading ->{

                }
            }

        }
    }



}