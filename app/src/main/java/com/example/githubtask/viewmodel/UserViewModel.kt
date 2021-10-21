package com.example.githubtask.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.githubtask.data.model.UserModel
import com.example.githubtask.repository.UserRepository
import com.example.githubtask.utils.Constant.Companion.SEARCH_QUERY
import com.example.githubtask.utils.Constant.Companion.TAG

class UserViewModel(private val repository: UserRepository) : ViewModel() {

    lateinit var usersResponse: LiveData<PagingData<UserModel>>

    init {
        searchUsers(query = SEARCH_QUERY)
    }

    private fun searchUsers(query: String) {
        try {
            usersResponse = repository.searchUsers(query = query).cachedIn(viewModelScope)
        } catch (e: Exception) {
            Log.d(TAG, "error occurred $e")
        }
    }
}