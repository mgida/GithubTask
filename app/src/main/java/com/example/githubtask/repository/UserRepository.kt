package com.example.githubtask.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.githubtask.data.UserPagingDataSource
import com.example.githubtask.data.model.FollowersResponse
import com.example.githubtask.data.model.FollowingResponse
import com.example.githubtask.data.network.UserService


class UserRepository constructor(
    private val userService: UserService
) {
    fun searchUsers(query: String) =
        Pager(
            config = PagingConfig(
                pageSize = 50,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { UserPagingDataSource(query, userService = userService) }
        ).liveData

    suspend fun getUserFollowers(userName: String): FollowersResponse =
        userService.getUserFollowers(userName = userName)

    suspend fun getUserFollowing(userName: String): FollowingResponse =
        userService.getUserFollowing(userName = userName)
}