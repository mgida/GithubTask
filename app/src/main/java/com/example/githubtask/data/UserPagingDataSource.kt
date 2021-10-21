package com.example.githubtask.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.githubtask.data.model.UserModel
import com.example.githubtask.data.model.UserResponse
import com.example.githubtask.data.network.RetrofitInstance
import com.example.githubtask.utils.Constant.Companion.STARTING_POSITION
import com.example.githubtask.utils.Constant.Companion.TAG


class UserPagingDataSource(private val query: String) : PagingSource<Int, UserModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserModel> {

        val position = params.key ?: STARTING_POSITION
        return try {
            val response: UserResponse =
                RetrofitInstance.userService.searchUsers(
                    querySearch = query,
                    page = position,
                    perPage = params.loadSize
                )

            val users: List<UserModel> = response.users!!

            LoadResult.Page(
                data = users,
                prevKey = if (position == STARTING_POSITION) null else position - 1,
                nextKey = if (users.isEmpty()) null else position + 1
            )
        } catch (e: Exception) {
            Log.d(TAG, "Error occurred $e")
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, UserModel>): Int? {
        return state.anchorPosition
    }

}