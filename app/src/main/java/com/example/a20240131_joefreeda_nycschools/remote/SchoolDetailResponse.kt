package com.example.a20240131_joefreeda_nycschools.remote

import com.example.a20240131_joefreeda_nycschools.model.SchoolDetail

sealed class SchoolDetailResponse {
    data class Success(val schoolDetail: SchoolDetail) : SchoolDetailResponse()
    data class Error(val errorMessage: String) : SchoolDetailResponse()
}