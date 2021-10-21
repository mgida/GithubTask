package com.example.githubtask.data.network


import com.example.githubtask.data.model.UserResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface UserService {

    @Headers("Accept: application/vnd.github.v3+json")
    @GET("/search/users")
    suspend fun searchUsers(
        @Query("q") querySearch: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ): UserResponse
}

