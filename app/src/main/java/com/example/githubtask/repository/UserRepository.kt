package com.example.githubtask.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.githubtask.data.UserPagingDataSource


class UserRepository {

    fun searchUsers(query: String) =
        Pager(
            config = PagingConfig(
                pageSize = 50,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { UserPagingDataSource(query) }
        ).liveData
}