package com.example.githubtask.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.githubtask.data.model.UserModel
import com.example.githubtask.repository.UserRepository
import com.example.githubtask.utils.Constant.Companion.SEARCH_QUERY
import com.example.githubtask.utils.Constant.Companion.TAG
import com.example.githubtask.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel

class UserViewModel @Inject constructor(private val repository: UserRepository) : ViewModel() {

    lateinit var usersResponse: LiveData<PagingData<UserModel>>

    private var _userFollowers: MutableLiveData<DataState<List<UserModel>>> =
        MutableLiveData<DataState<List<UserModel>>>()
    val userFollowers: LiveData<DataState<List<UserModel>>> get() = _userFollowers

    private var _userFollowing: MutableLiveData<DataState<List<UserModel>>> =
        MutableLiveData<DataState<List<UserModel>>>()
    val userFollowing: LiveData<DataState<List<UserModel>>> get() = _userFollowing

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

    fun getUserFollowers(userName: String) {
        viewModelScope.launch {
            _userFollowers.postValue(DataState.Loading)
            try {
                val response = repository.getUserFollowers(userName)
                _userFollowers.postValue(DataState.Success(response))
            } catch (e: Exception) {
                Log.d(TAG, "error occurred $e")
                _userFollowers.postValue(DataState.Error(e))
            }
        }
    }

    fun getUserFollowing(userName: String) {
        viewModelScope.launch {
            _userFollowing.postValue(DataState.Loading)
            try {
                val response = repository.getUserFollowing(userName)
                _userFollowing.postValue(DataState.Success(response))
            } catch (e: Exception) {
                Log.d(TAG, "error occurred $e")
                _userFollowing.postValue(DataState.Error(e))
            }
        }
    }
}