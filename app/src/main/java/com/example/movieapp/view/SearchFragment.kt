package com.example.movieapp.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView.OnQueryTextListener
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.movieapp.R
import com.example.movieapp.adapter.SearchAdapter
import com.example.movieapp.databinding.FragmentHomeBinding
import com.example.movieapp.databinding.FragmentSearchBinding
import com.example.movieapp.util.Resource
import com.example.movieapp.util.viewBinding
import com.example.movieapp.viewmodel.HomeViewModel
import com.example.movieapp.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {

    private val binding by viewBinding(FragmentSearchBinding::bind)
    private val viewModel: SearchViewModel by viewModels()
    private lateinit var adapter : SearchAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = SearchAdapter()
        binding.rvSearch.adapter = adapter

        binding.edSearch.setOnQueryTextListener(object : OnQueryTextListener,
            SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                viewModel.search(newText)
                return true
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.search(query)
                return true
            }
        })

        observeData()


    }

    private fun observeData(){
        viewModel.observeSearchList().observe(viewLifecycleOwner){response->
            when(response){
                is Resource.Success->{
                    response.data?.let {movie->
                        adapter.submitList(movie.results)
                        adapter.onItemClick ={
                            val id = it.id
                            if(id!=null)
                            {
                                val action = SearchFragmentDirections.actionSearchFragmentToDetailFragment(id)
                                findNavController().navigate(action)
                            }
                            else{
                                Toast.makeText(requireContext(),"Error",Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                }is Resource.Loading ->{
            }
                is Resource.Error ->{
                    response.message?.let {
                        Log.e("Search Fragment",it)
                    }
                }

            }

        }
    }




}