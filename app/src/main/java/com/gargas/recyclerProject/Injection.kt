package com.gargas.recyclerProject

import GetPostsRxRemoteMediator
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.paging.ExperimentalPagingApi
import com.gargas.recyclerProject.network.APIService
import com.gargas.recyclerProject.paging.GetPostsRxPagingSource
import com.gargas.recyclerProject.repository.GetPostsRxRemoteRepositoryImpl
import com.gargas.recyclerProject.repository.GetPostsRxRepositoryImpl
import com.gargas.recyclerProject.repository.db.dao.PostDatabase
import com.gargas.recyclerProject.repository.db.mapping.PostsMapper
import com.gargas.recyclerProject.ui.viewmodels.GetPostsRxViewModelFactory
import java.util.*

object Injection {
    fun provideDatabase(context: Context): PostDatabase = PostDatabase.getInstance(context)

    fun provideRxViewModel(context: Context): ViewModelProvider.Factory {
        val pagingSource =
            GetPostsRxPagingSource(
                service = APIService.create(),
                mapper = PostsMapper(),
            )

        val repository =
            GetPostsRxRepositoryImpl(
                pagingSource = pagingSource
            )

        return GetPostsRxViewModelFactory(
            repository
        )
    }

    @ExperimentalPagingApi
    fun provideRxRemoteViewModel(context: Context): ViewModelProvider.Factory {
        val remoteMediator =
            GetPostsRxRemoteMediator(
                service = APIService.create(),
                database = provideDatabase(context),
                mapper = PostsMapper(),
            )

        val repository =
            GetPostsRxRemoteRepositoryImpl(
                database = provideDatabase(context),
                remoteMediator = remoteMediator
            )

        return GetPostsRxViewModelFactory(
            repository
        )
    }
}