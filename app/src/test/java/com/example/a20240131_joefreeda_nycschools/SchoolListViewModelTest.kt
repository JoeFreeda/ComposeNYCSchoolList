package com.example.a20240131_joefreeda_nycschools

import com.example.a20240131_joefreeda_nycschools.remote.ApiResponse
import com.example.a20240131_joefreeda_nycschools.remote.ApiService
import com.example.a20240131_joefreeda_nycschools.repository.SchoolListImpl
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.SpyK
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import javax.inject.Inject

class SchoolListViewModelTest {
    @MockK(relaxed = true)
    @Inject
    lateinit var apiService :ApiService
    @SpyK
    @MockK
    lateinit var schoolListRepository : SchoolListImpl

    @MockK
    lateinit var apiResponse: ApiResponse

    @Before
    fun setUp(){
        MockKAnnotations.init(this)
        schoolListRepository = SchoolListImpl(apiService)
    }

    @Test
    fun `test getSchoolList`(){
        every { schoolListRepository.getSchoolList { mockk() } } returns mockk()
        schoolListRepository.getSchoolList({
            apiResponse
        })

        verify { schoolListRepository.getSchoolList {  } }

    }

}