package com.example.a20240131_joefreeda_nycschools.repository

import com.example.a20240131_joefreeda_nycschools.model.SchoolDetail
import com.example.a20240131_joefreeda_nycschools.model.Schools
import com.example.a20240131_joefreeda_nycschools.remote.ApiResponse
import com.example.a20240131_joefreeda_nycschools.remote.ApiService
import com.example.a20240131_joefreeda_nycschools.remote.SchoolDetailResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SchoolListImpl(private val apiService: ApiService) : SchoolList{
    override fun getSchoolList(schoolResponse : (ApiResponse) -> Unit) {
        apiService.getSchoolList().enqueue(object : Callback<List<Schools>> {
            override fun onResponse(call: Call<List<Schools>>, response: Response<List<Schools>>) {
                response.body()?.let {
                    schoolResponse(ApiResponse.Success(it))
                }
            }

            override fun onFailure(call: Call<List<Schools>>, t: Throwable) {
                t.message?.let {
                    schoolResponse(ApiResponse.Error(it))
                }
            }
        })
    }

    override fun getSchoolDetail(dbn:String,schoolDetailResponse: (SchoolDetailResponse) -> Unit) {
        apiService.getSchoolDetail().enqueue(object : Callback<List<SchoolDetail>>{
            override fun onResponse(call: Call<List<SchoolDetail>>, response: Response<List<SchoolDetail>>) {
                response.body()?.let {
                    val responseResult : List<SchoolDetail> = it
                    for (item in responseResult) {
                        val filteredItems = item.dbn
                        if (filteredItems == dbn) {
                            schoolDetailResponse(SchoolDetailResponse.Success(item))
                            break
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<SchoolDetail>>, t: Throwable) {
                t.message?.let {
                    schoolDetailResponse(SchoolDetailResponse.Error(it))
                }
            }

        })
    }
}