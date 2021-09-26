package com.gargas.recyclerProject.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import com.gargas.recyclerProject.models.Posts
import com.gargas.recyclerProject.paging.GetPostsRxPagingSource
import io.reactivex.Flowable

class GetPostsRxRepositoryImpl(private val pagingSource: GetPostsRxPagingSource): GetPostsRxRepository {

    override fun getPosts(): Flowable<PagingData<Posts.Data>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = true,
                maxSize = 30,
                prefetchDistance = 5,
                initialLoadSize = 40),
            pagingSourceFactory = { pagingSource }
        ).flowable
    }
}