package com.gargas.recyclerProject.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.filter
import androidx.paging.rxjava2.cachedIn
import com.gargas.recyclerProject.models.Posts
import com.gargas.recyclerProject.repository.GetPostsRxRepository
import io.reactivex.Flowable
import kotlinx.coroutines.ExperimentalCoroutinesApi

class GetPostsRxViewModel(private val repository: GetPostsRxRepository) : ViewModel() {
    @ExperimentalCoroutinesApi
    fun getPosts(): Flowable<PagingData<Posts.Data>> {
        return repository
            .getPosts()
            .map { pagingData -> pagingData.filter { it.body != null } }
            .cachedIn(viewModelScope)
    }
}