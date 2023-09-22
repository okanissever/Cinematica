package com.example.movieapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.databinding.ItemSearchBinding
import com.example.movieapp.util.Constants
import com.example.movieapp.model.movielist.Result

class SearchAdapter : ListAdapter<Result, SearchAdapter.MovieHolder>(NoteDiffCallback) {

    var onItemClick :(Result) -> Unit = {}

    object NoteDiffCallback : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }
    }
    class MovieHolder(val binding : ItemSearchBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val binding = ItemSearchBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MovieHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        val item = currentList[position]

        Glide.with(holder.itemView).load(Constants.POSTER_BASE_URL +item.posterPath).into(holder.binding.imgMovie)

        holder.binding.tvMovieDate.text = item.releaseDate
        holder.binding.tvMovieName.text = item.title
        val rating = item.voteAverage
        val roundedRating = "%.1f".format(rating)
        holder.binding.tvMovieStar.text = roundedRating
        holder.binding.tvMovieLanguage.text = item.originalLanguage

        holder.binding.root.setOnClickListener {
            onItemClick(item)
        }
    }
}