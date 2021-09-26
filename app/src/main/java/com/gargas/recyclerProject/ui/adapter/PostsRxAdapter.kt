
package com.gargas.recyclerProject.ui.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.gargas.recyclerProject.models.Posts

class PostsRxAdapter : PagingDataAdapter<Posts.Data, PostsViewHolder>(
    COMPARATOR
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsViewHolder {
        return PostsViewHolder.create(
            parent
        )
    }

    override fun onBindViewHolder(holder:PostsViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Posts.Data>() {
            override fun areItemsTheSame(oldItem: Posts.Data, newItem: Posts.Data): Boolean {
                return oldItem.postId == newItem.postId
            }

            override fun areContentsTheSame(oldItem: Posts.Data, newItem: Posts.Data): Boolean {
                return oldItem == newItem
            }
        }
    }
}