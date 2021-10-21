package com.example.githubtask.data.network


import com.example.githubtask.data.model.FollowersResponse
import com.example.githubtask.data.model.FollowingResponse
import com.example.githubtask.data.model.UserResponse
import com.example.githubtask.utils.Constant.Companion.ACCEPT_HEADER
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface UserService {

    @Headers(ACCEPT_HEADER)
    @GET("/search/users")
    suspend fun searchUsers(
        @Query("q") querySearch: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ): UserResponse

    @Headers(ACCEPT_HEADER)
    @GET("/users/{username}/followers")
    suspend fun getUserFollowers(
        @Path("username") userName: String,
    ): FollowersResponse

    @Headers(ACCEPT_HEADER)
    @GET("/users/{username}/following")
    suspend fun getUserFollowing(
        @Path("username") userName: String,
    ): FollowingResponse


}

