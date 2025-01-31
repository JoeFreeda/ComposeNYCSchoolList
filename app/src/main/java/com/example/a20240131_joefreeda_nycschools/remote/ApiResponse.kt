package com.example.a20240131_joefreeda_nycschools.remote

import com.example.a20240131_joefreeda_nycschools.model.Schools

sealed class ApiResponse {
    data class Success(val schoolList: List<Schools>) : ApiResponse()
    data class Error(val errorMessage: String) : ApiResponse()
}