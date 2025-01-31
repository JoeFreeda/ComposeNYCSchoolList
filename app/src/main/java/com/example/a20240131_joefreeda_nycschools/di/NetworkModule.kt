package com.example.a20240131_joefreeda_nycschools.di

import com.example.a20240131_joefreeda_nycschools.remote.ApiService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideRetrofit(url: String, gson: Gson): ApiService {
        return Retrofit.Builder().baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create(gson)).build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun getUrl(): String {
        return "https://data.cityofnewyork.us/"
    }

    @Provides
    @Singleton
    fun getGson(): Gson {
        return GsonBuilder()
            .setLenient() // Optional: Allows parsing of malformed JSON
            .create()
    }

}