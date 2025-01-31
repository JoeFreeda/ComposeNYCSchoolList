package com.example.a20240131_joefreeda_nycschools.remote

import com.example.a20240131_joefreeda_nycschools.model.SchoolDetail
import com.example.a20240131_joefreeda_nycschools.model.Schools
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("/resource/s3k6-pzi2.json")
    fun getSchoolList(): Call<List<Schools>>

    @GET("/resource/f9bf-2cp4.json")
    fun getSchoolDetail():Call<List<SchoolDetail>>

}