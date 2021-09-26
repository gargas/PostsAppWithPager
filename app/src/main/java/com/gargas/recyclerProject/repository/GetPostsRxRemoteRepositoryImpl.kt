package com.gargas.recyclerProject.repository

import GetPostsRxRemoteMediator
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import com.gargas.recyclerProject.models.Posts
import com.gargas.recyclerProject.repository.db.dao.PostDatabase
import io.reactivex.Flowable

class GetPostsRxRemoteRepositoryImpl @ExperimentalPagingApi constructor(
    private val database: PostDatabase,
    private val remoteMediator: GetPostsRxRemoteMediator
): GetPostsRxRepository {

    @ExperimentalPagingApi
    override fun getPosts(): Flowable<PagingData<Posts.Data>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = true,
                maxSize = 30,
                prefetchDistance = 5,
                initialLoadSize = 40),
            remoteMediator = remoteMediator,
            pagingSourceFactory = { database.postsRxDao().selectAll() }
        ).flowable
    }
}