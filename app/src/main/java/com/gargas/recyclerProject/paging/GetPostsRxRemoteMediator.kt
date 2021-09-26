import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.rxjava2.RxRemoteMediator
import com.gargas.recyclerProject.models.Posts
import com.gargas.recyclerProject.network.APIService
import com.gargas.recyclerProject.repository.db.dao.PostDatabase
import com.gargas.recyclerProject.repository.db.mapping.PostsMapper
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.io.InvalidObjectException

@ExperimentalPagingApi
@OptIn(ExperimentalPagingApi::class)
class GetPostsRxRemoteMediator(
    private val service: APIService,
    private val database: PostDatabase,
    private val mapper: PostsMapper,
) : RxRemoteMediator<Int, Posts.Data>() {

    override fun loadSingle(
        loadType: LoadType,
        state: PagingState<Int, Posts.Data>
    ): Single<MediatorResult> {
        return Single.just(loadType)
            .subscribeOn(Schedulers.io())
            .map {
                when (it) {
                    LoadType.REFRESH -> {
                        val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)

                        remoteKeys?.nextKey?.minus(1) ?: 1
                    }
                    LoadType.PREPEND -> {
                        val remoteKeys = getRemoteKeyForFirstItem(state)
                            ?: throw InvalidObjectException("Result is empty")

                        remoteKeys.prevKey ?: INVALID_PAGE
                    }
                    LoadType.APPEND -> {
                        val remoteKeys = getRemoteKeyForLastItem(state)
                            ?: throw InvalidObjectException("Result is empty")

                        remoteKeys.nextKey ?: INVALID_PAGE
                    }
                }
            }
            .flatMap { page ->
                if (page == INVALID_PAGE) {
                    Single.just(MediatorResult.Success(endOfPaginationReached = true))
                } else {
                    service.popularMovieRx(page = page)
                        .map { mapper.transform(it) }
                        .map { insertToDb(page, loadType, it) }
                        .map<MediatorResult> { MediatorResult.Success(endOfPaginationReached = it.endOfPage) }
                        .onErrorReturn { MediatorResult.Error(it) }
                }

            }
            .onErrorReturn { MediatorResult.Error(it) }

    }

    @Suppress("DEPRECATION")
    private fun insertToDb(page: Int, loadType: LoadType, data: Posts): Posts {
        database.beginTransaction()

        try {
            if (loadType == LoadType.REFRESH) {
                database.postRemoteKeysRxDao().clearRemoteKeys()
                database.postsRxDao().clearMovies()
            }

            val prevKey = if (page == 1) null else page - 1
            val nextKey = if (data.endOfPage) null else page + 1
            val keys = data.posts.map {
                Posts.PostsRemoteKeys(movieId = it.id, prevKey = prevKey, nextKey = nextKey)
            }
            database.postRemoteKeysRxDao().insertAll(keys)
            database.postsRxDao().insertAll(data.posts)
            database.setTransactionSuccessful()

        } finally {
            database.endTransaction()
        }

        return data
    }

    private fun getRemoteKeyForLastItem(state: PagingState<Int, Posts.Data>): Posts.PostsRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { repo ->
            database.postRemoteKeysRxDao().remoteKeysByMovieId(repo.id)
        }
    }

    private fun getRemoteKeyForFirstItem(state: PagingState<Int, Posts.Data>): Posts.PostsRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { movie ->
            database.postRemoteKeysRxDao().remoteKeysByMovieId(movie.id)
        }
    }

    private fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, Posts.Data>): Posts.PostsRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                database.postRemoteKeysRxDao().remoteKeysByMovieId(id)
            }
        }
    }

    companion object {
        const val INVALID_PAGE = -1
    }
}