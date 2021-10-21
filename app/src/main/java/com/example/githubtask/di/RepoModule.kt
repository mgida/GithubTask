package com.example.githubtask.di

import com.example.githubtask.data.network.UserService
import com.example.githubtask.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {

    @Provides
    @Singleton
    fun provideRepoDependencies(
        userService: UserService
    )
            : UserRepository {
        return UserRepository(
            userService = userService
        )
    }
}