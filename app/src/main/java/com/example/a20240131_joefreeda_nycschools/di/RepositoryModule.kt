package com.example.a20240131_joefreeda_nycschools.di

import com.example.a20240131_joefreeda_nycschools.remote.ApiService
import com.example.a20240131_joefreeda_nycschools.repository.SchoolList
import com.example.a20240131_joefreeda_nycschools.repository.SchoolListImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideSchoolListRepository(apiService: ApiService): SchoolList {
        return SchoolListImpl(apiService)
    }

}