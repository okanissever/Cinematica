package com.example.movieapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.databinding.ItemTabBinding
import com.example.movieapp.databinding.ItemTopMovieBinding
import com.example.movieapp.model.movielist.Result
import com.example.movieapp.util.Constants.POSTER_BASE_URL

class TabAdapter() : ListAdapter<Result, TabAdapter.MovieHolder>(NoteDiffCallback) {

    var onItemClick :(Result) -> Unit = {}

    object NoteDiffCallback : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }
    }
    class MovieHolder(val binding : ItemTabBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val binding = ItemTabBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MovieHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        val poster = POSTER_BASE_URL + currentList[position].posterPath
        Glide.with(holder.itemView).load(poster).into(holder.binding.imgMovie)

        holder.binding.root.setOnClickListener {
            onItemClick(currentList[position])
        }

    }
}