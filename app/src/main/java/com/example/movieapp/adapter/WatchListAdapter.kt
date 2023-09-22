package com.example.movieapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.databinding.ItemWatchlistBinding
import com.example.movieapp.model.moviedetail.MovieDetails
import com.example.movieapp.util.Constants.POSTER_BASE_URL

class WatchListAdapter: ListAdapter<MovieDetails, WatchListAdapter.MovieHolder>(NoteDiffCallback) {

    var onItemClick :(MovieDetails) -> Unit = {}

    object NoteDiffCallback : DiffUtil.ItemCallback<MovieDetails>() {
        override fun areItemsTheSame(oldItem: MovieDetails, newItem: MovieDetails): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieDetails, newItem: MovieDetails): Boolean {
            return oldItem == newItem
        }
    }
    class MovieHolder(val binding : ItemWatchlistBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val binding = ItemWatchlistBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MovieHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        val item = currentList[position]
        Glide.with(holder.itemView).load(POSTER_BASE_URL +item.posterPath).into(holder.binding.imgMovie)

        holder.binding.tvMovieGenre.text = item.genres?.firstOrNull()?.name.orEmpty()
        holder.binding.tvMovieDate.text = item.releaseDate
        holder.binding.tvMovieName.text = item.title
        val rating = item.voteAverage
        val roundedRating = "%.1f".format(rating)
        holder.binding.tvMovieStar.text = roundedRating
        holder.binding.tvMovieTime.text = item.runtime.toString() + " Minutes"

        holder.binding.root.setOnClickListener {
            onItemClick(item)
        }
    }
}