package com.gargas.recyclerProject.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gargas.recyclerProject.R
import com.gargas.recyclerProject.databinding.ItemPostsBinding
import com.gargas.recyclerProject.models.Posts

class PostsViewHolder(private val binding: ItemPostsBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(data: Posts.Data) {
        with(data) {
            this.title?.let {
                binding.heading =it
            }
            this.body?.let {
                binding.desc =it
            }
        }
    }

    companion object {
        fun create(parent: ViewGroup): PostsViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_posts,  parent,false)

            val binding = ItemPostsBinding.bind(view)

            return PostsViewHolder(
                binding
            )
        }
    }
}