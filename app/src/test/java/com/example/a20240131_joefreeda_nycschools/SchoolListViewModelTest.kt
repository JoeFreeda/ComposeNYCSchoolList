package com.example.a20240131_joefreeda_nycschools.repository

import com.example.a20240131_joefreeda_nycschools.model.SchoolDetail
import com.example.a20240131_joefreeda_nycschools.model.Schools
import com.example.a20240131_joefreeda_nycschools.remote.ApiResponse
import com.example.a20240131_joefreeda_nycschools.remote.ApiService
import com.example.a20240131_joefreeda_nycschools.remote.SchoolDetailResponse
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll
import junit.framework.TestCase.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SchoolListImplTest {

    private lateinit var apiService: ApiService
    private lateinit var schoolListImpl: SchoolListImpl

    @Before
    fun setup() {
        apiService = mockk(relaxed = true)
        schoolListImpl = SchoolListImpl(apiService)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `getSchoolList success returns ApiResponse Success`() {
        // Given
        val schoolsList = listOf(
           mockk<Schools>()
        )
        val mockCall: Call<List<Schools>> = mockk()
        every { apiService.getSchoolList() } returns mockCall
        every { mockCall.enqueue(any()) } answers {
            val callback = firstArg<Callback<List<Schools>>>()
            callback.onResponse(mockCall, Response.success(schoolsList))
        }

        // When
        var result: ApiResponse? = null
        schoolListImpl.getSchoolList { response -> result = response }

        // Then
        assert(result is ApiResponse.Success)
        assertEquals((result as ApiResponse.Success).schoolList, schoolsList)
    }

    @Test
    fun `getSchoolList failure returns ApiResponse Error`() {
        // Given
        val errorMessage = "Network Error"
        val mockCall: Call<List<Schools>> = mockk()
        every { apiService.getSchoolList() } returns mockCall
        every { mockCall.enqueue(any()) } answers {
            val callback = firstArg<Callback<List<Schools>>>()
            callback.onFailure(mockCall, Throwable(errorMessage))
        }

        // When
        var result: ApiResponse? = null
        schoolListImpl.getSchoolList { response -> result = response }

        // Then
        assert(result is ApiResponse.Error)
        assertEquals((result as ApiResponse.Error).errorMessage, errorMessage)
    }

    @Test
    fun `getSchoolDetail success returns correct school detail`() {
        // Given
        val dbn = "123"
        val schoolDetails = listOf(
           mockk<SchoolDetail>()
        )
        every { schoolDetails.first().dbn } returns dbn
        val mockCall: Call<List<SchoolDetail>> = mockk()
        every { apiService.getSchoolDetail() } returns mockCall
        every { mockCall.enqueue(any()) } answers {
            val callback = firstArg<Callback<List<SchoolDetail>>>()
            callback.onResponse(mockCall, Response.success(schoolDetails))
        }

        // When
        var result: SchoolDetailResponse? = null
        schoolListImpl.getSchoolDetail(dbn) { response -> result = response }

        // Then
        assert(result is SchoolDetailResponse.Success)
        assertEquals((result as SchoolDetailResponse.Success).schoolDetail.dbn, dbn)
    }

}
