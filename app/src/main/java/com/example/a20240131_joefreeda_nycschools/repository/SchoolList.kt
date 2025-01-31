package com.example.a20240131_joefreeda_nycschools.repository

import com.example.a20240131_joefreeda_nycschools.model.Schools
import com.example.a20240131_joefreeda_nycschools.remote.ApiResponse
import com.example.a20240131_joefreeda_nycschools.remote.SchoolDetailResponse

interface SchoolList {
    fun getSchoolList(schoolResponse:(ApiResponse)->Unit)
    fun getSchoolDetail(dbn:String,schoolDetailResponse: (SchoolDetailResponse) -> Unit)
}