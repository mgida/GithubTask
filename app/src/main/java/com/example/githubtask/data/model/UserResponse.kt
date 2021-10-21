package com.example.githubtask.data.model

import com.google.gson.annotations.SerializedName

data class UserResponse(
    val incomplete_results: Boolean,
    @SerializedName("items")
    val users: List<UserModel>?,
    val total_count: Int
)