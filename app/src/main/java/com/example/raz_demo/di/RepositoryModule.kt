package com.example.raz_demo.di

import com.example.raz_demo.model.User
import com.example.raz_demo.repository.UserRepository
import com.example.raz_demo.repository.UserRepositoryImp
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun userRepository(repo: UserRepositoryImp) : UserRepository

}
