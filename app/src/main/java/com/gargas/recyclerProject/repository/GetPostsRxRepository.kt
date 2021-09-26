package com.gargas.recyclerProject.repository

import androidx.paging.PagingData
import com.gargas.recyclerProject.models.Posts
import io.reactivex.Flowable

interface GetPostsRxRepository {
    fun getPosts(): Flowable<PagingData<Posts.Data>>
}