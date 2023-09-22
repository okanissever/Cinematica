package com.example.movieapp.view.detailsfragment

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentDetailBinding
import com.example.movieapp.util.Constants.POSTER_BASE_URL
import com.example.movieapp.util.Resource
import com.example.movieapp.util.viewBinding
import com.example.movieapp.viewmodel.DetailsViewModel
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_detail) {
    private val binding by viewBinding(FragmentDetailBinding::bind)
    private val viewModel: DetailsViewModel by viewModels()

    val args : DetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        viewModel.loadMovieDetails(args.movieId)
        observeLiveData()
        viewPager()

    }

    private fun viewPager(){
        binding.ViewPager.adapter = DetailsPageAdapter(requireActivity(),args.movieId)
        TabLayoutMediator(binding.tabLayout,binding.ViewPager){tab,position->
            when(position){
                0->tab.text = "Overview"
                1->tab.text = "Cast"
                2->tab.text = "Review"
            }
        }.attach()
    }

    private fun observeLiveData(){
        viewModel.observeMovieDetails().observe(viewLifecycleOwner){response->
            when(response){
             is Resource.Success->{
                 response.data?.let {movie->
                  binding.apply {
                      tvMovieName.text = movie.title
                      println("Okan ${movie.title}")
                      tvMovieDate.text = movie.releaseDate
                      tvMovieTime.text = movie.runtime.toString() +" Minutes"
                      tvMovieGenre.text = movie.genres?.firstOrNull()?.name.orEmpty()
                      val rating = movie.voteAverage
                      val roundedRating = "%.1f".format(rating)
                      tvRating.text =roundedRating

                      val poster = POSTER_BASE_URL + movie.posterPath
                      println(poster)
                      Glide.with(root).load(poster).into(imgMovie)

                      val backgroundPoster = POSTER_BASE_URL + movie.backdropPath
                      println(backgroundPoster)
                      Glide.with(root).load(backgroundPoster).into(imgMovieBackground)

                      binding.addMovie.setOnClickListener {
                          viewModel.addMovie(movie)
                          Toast.makeText(requireContext(),"Saved",Toast.LENGTH_LONG).show()
                      }

                  }}
                 hideProgressBar()
             }
                is Resource.Loading ->{
                    showProgressBar()
                }
                is Resource.Error ->{
                    response.message?.let {
                        println(it)
                    }
                    hideProgressBar()
                }
            }
        }
    }


    private var isLoading = false
    private fun hideProgressBar(){
        binding.apply {
            tvMovieName.visibility = View.VISIBLE
            tvMovieName.visibility = View.VISIBLE
            tvMovieName.visibility = View.VISIBLE
            mcvImgMovie.visibility = View.VISIBLE
            mcvImgMovie.visibility = View.VISIBLE
            progressBar.visibility = View.INVISIBLE
            
        }
        isLoading = false
    }

    private fun showProgressBar(){
        binding.apply {
            tvMovieName.visibility = View.INVISIBLE
            tvMovieName.visibility = View.INVISIBLE
            tvMovieName.visibility = View.INVISIBLE
            mcvImgMovie.visibility = View.INVISIBLE
            mcvImgMovie.visibility = View.INVISIBLE
            progressBar.visibility = View.VISIBLE
        }
        isLoading = true
    }
}