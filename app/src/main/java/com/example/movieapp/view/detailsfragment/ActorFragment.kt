package com.example.movieapp.view.detailsfragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.movieapp.R
import com.example.movieapp.adapter.ActorsAdapter
import com.example.movieapp.databinding.FragmentCastBinding
import com.example.movieapp.util.Resource
import com.example.movieapp.util.viewBinding
import com.example.movieapp.viewmodel.ActorViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActorFragment : Fragment(R.layout.fragment_cast) {

    private val binding by viewBinding(FragmentCastBinding::bind)
    private val viewModel: ActorViewModel by viewModels()

    lateinit var actorAdapter : ActorsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        actorAdapter = ActorsAdapter()
        binding.recyclerView.adapter = actorAdapter

        val movieId = arguments?.getInt("movieId")

        viewModel.fetchActors(movieId!!)
        observeData()
    }

    private fun observeData(){
        viewModel.observeActorList().observe(viewLifecycleOwner){response->
            when(response){
                is Resource.Success->{
                    response.data?.let {
                        actorAdapter.submitList(it.cast?.toList())
                    }
                }
                is Resource.Error->{
                    response.message?.let {
                        Log.e("ActorFragment",it)
                    }
                }
                is Resource.Loading ->{
                }
            }
        }
    }
}