package com.example.movieapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movieapp.databinding.ItemReviewBinding
import com.example.movieapp.model.moviereviews.AuthorResult

class ReviewsAdapter : androidx.recyclerview.widget.ListAdapter<AuthorResult,ReviewsAdapter.ReviewHolder>(NoteDiffCallback) {

    object NoteDiffCallback : DiffUtil.ItemCallback<AuthorResult>() {
        override fun areItemsTheSame(oldItem: AuthorResult, newItem: AuthorResult): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: AuthorResult, newItem: AuthorResult): Boolean {
            return oldItem == newItem
        }
    }

    class ReviewHolder(val binding : ItemReviewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewHolder {
        val binding = ItemReviewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ReviewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReviewHolder, position: Int) {
        val item = currentList[position]

        holder.binding.authorTV.text = item.author
        holder.binding.contentTV.text = item.content

        if(item.authorDetails?.rating!= null)
            holder.binding.ratingTV.text = item.authorDetails.rating.toString()
        else
            holder.binding.ratingTV.visibility = View.INVISIBLE

    }


}