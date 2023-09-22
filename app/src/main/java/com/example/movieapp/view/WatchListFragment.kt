package com.example.movieapp.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.R
import com.example.movieapp.adapter.WatchListAdapter
import com.example.movieapp.databinding.FragmentWatchListBinding
import com.example.movieapp.util.viewBinding
import com.example.movieapp.viewmodel.WatchListViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WatchListFragment : Fragment(R.layout.fragment_watch_list) {

    private val binding by viewBinding(FragmentWatchListBinding::bind)
    private val viewModel: WatchListViewModel by viewModels()

    lateinit var adapter : WatchListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = WatchListAdapter()
        binding.rvWatchList.layoutManager = LinearLayoutManager(requireContext())
        binding.rvWatchList.adapter = adapter

        viewModel.getAllMovie()
        observeLiveData()

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val movie = adapter.currentList[position]
                viewModel.deleteMovie(movie)
                Snackbar.make(view,"Successfully deleted movie", Snackbar.LENGTH_LONG).apply {
                    setAction("Undo"){
                        viewModel.addMovie(movie)

                    }.show()
                }
                observeLiveData()
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.rvWatchList)
        }


    }
   private fun observeLiveData(){
       viewModel.observeWatchList().observe(viewLifecycleOwner){list->
           list?.let {movie->
               adapter.submitList(movie)
               adapter.onItemClick = {
                   val action = WatchListFragmentDirections.actionWatchListFragmentToDetailFragment(it.id!!)
                   findNavController().navigate(action)
               }
           }

       }
   }

}