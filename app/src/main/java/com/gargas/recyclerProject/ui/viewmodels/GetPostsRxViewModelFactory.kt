package com.gargas.recyclerProject.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gargas.recyclerProject.repository.GetPostsRxRepository

class GetPostsRxViewModelFactory(private val repository: GetPostsRxRepository): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GetPostsRxViewModel::class.java)) {
            return GetPostsRxViewModel(
                repository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}