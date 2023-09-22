package com.example.movieapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapp.databinding.ItemActorBinding
import com.example.movieapp.model.moviecast.Cast
import com.example.movieapp.util.Constants

class ActorsAdapter() : ListAdapter<Cast, ActorsAdapter.ActorHolder>(NoteDiffCallback) {

    object NoteDiffCallback : DiffUtil.ItemCallback<Cast>() {
        override fun areItemsTheSame(oldItem: Cast, newItem: Cast): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Cast, newItem: Cast): Boolean {
            return oldItem == newItem
        }
    }

    class ActorHolder(val binding : ItemActorBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorHolder {
        val binding = ItemActorBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ActorHolder(binding)
    }

    override fun onBindViewHolder(holder: ActorHolder, position: Int) {
        val poster = Constants.POSTER_BASE_URL + currentList[position].profilePath
        Glide.with(holder.itemView).load(poster).into(holder.binding.imgActor)

        holder.binding.actorNameTV.text = currentList[position].name

    }

}