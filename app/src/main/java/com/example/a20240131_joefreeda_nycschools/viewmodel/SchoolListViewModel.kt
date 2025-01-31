package com.example.a20240131_joefreeda_nycschools.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a20240131_joefreeda_nycschools.remote.ApiResponse
import com.example.a20240131_joefreeda_nycschools.remote.SchoolDetailResponse
import com.example.a20240131_joefreeda_nycschools.repository.SchoolList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SchoolListViewModel @Inject constructor(private val schoolList: SchoolList) : ViewModel() {
    private val _schoolListResult = MutableStateFlow<ApiResponse>(ApiResponse.Success(emptyList()))
    val schoolListResult: StateFlow<ApiResponse> = _schoolListResult

    private val _schoolDetailResult = MutableStateFlow<SchoolDetailResponse>(SchoolDetailResponse.Error(""))
    val schoolDetailResult: StateFlow<SchoolDetailResponse> = _schoolDetailResult

    fun getSchoolList() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                schoolList.getSchoolList {
                    _schoolListResult.value = it
                }
            }
        }
    }

    fun getSchoolDetail(dbn: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                schoolList.getSchoolDetail(dbn) {
                    _schoolDetailResult.value = it
                }
            }
        }
    }

}