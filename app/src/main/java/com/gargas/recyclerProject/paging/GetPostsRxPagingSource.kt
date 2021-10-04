package com.gargas.recyclerProject.paging

import androidx.paging.PagingState
import androidx.paging.rxjava2.RxPagingSource
import com.gargas.recyclerProject.models.Posts
import com.gargas.recyclerProject.network.APIService
import com.gargas.recyclerProject.repository.db.mapping.PostsMapper
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class GetPostsRxPagingSource(
    private val service: APIService,
    private val mapper: PostsMapper,
) : RxPagingSource<Int, Posts.Data>() {

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Posts.Data>> {
        val position = params.key ?: 1

        return service.popularPostRx(position)
            .subscribeOn(Schedulers.io())
            .map { mapper.transform(it) }
            .map { toLoadResult(it, position) }
            .onErrorReturn { LoadResult.Error(it) }
    }

    private fun toLoadResult(data: Posts, position: Int): LoadResult<Int, Posts.Data> {
        return LoadResult.Page(
            data = data.posts,
            prevKey = if (position == 1) null else position - 1,
            nextKey = if (position == data.total) null else position + 1
        )
    }

    override fun getRefreshKey(state: PagingState<Int, Posts.Data>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}