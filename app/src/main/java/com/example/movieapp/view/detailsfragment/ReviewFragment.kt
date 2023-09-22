package com.example.movieapp.view.detailsfragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.movieapp.R
import com.example.movieapp.adapter.ReviewsAdapter
import com.example.movieapp.databinding.FragmentReviewBinding
import com.example.movieapp.util.Resource
import com.example.movieapp.util.viewBinding
import com.example.movieapp.viewmodel.DetailsViewModel
import com.example.movieapp.viewmodel.ReviewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReviewFragment : Fragment(R.layout.fragment_review) {

    private val binding by viewBinding(FragmentReviewBinding::bind)
    private val viewModel: ReviewsViewModel by viewModels()

    lateinit var adapter : ReviewsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ReviewsAdapter()
        binding.recyclerView.adapter = adapter

        val movieId = arguments?.getInt("movieId")

        if (movieId != null) {
            viewModel.fetchAuthor(movieId)
            println("MovieId not null")
        }
        else{
            println(movieId)
        }
        observeData()
    }

    private fun observeData(){
        viewModel.observeReviewsList().observe(viewLifecycleOwner){response->
            when(response){
                is Resource.Success->{
                    response.data?.let {
                        adapter.submitList(it.results)
                    }
                }
                is Resource.Error->{
                    response.message?.let {
                        Log.e("ReviewFragment",it)
                    }
                }
                is Resource.Loading ->{
                }
            }

        }
    }

}