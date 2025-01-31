package com.example.a20240131_joefreeda_nycschools

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.a20240131_joefreeda_nycschools.remote.ApiResponse
import com.example.a20240131_joefreeda_nycschools.remote.SchoolDetailResponse
import com.example.a20240131_joefreeda_nycschools.ui.theme._20240131JoeFreedaNYCSchoolsTheme
import com.example.a20240131_joefreeda_nycschools.viewmodel.SchoolListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            _20240131JoeFreedaNYCSchoolsTheme {
                val viewModel: SchoolListViewModel = hiltViewModel()
                viewModel.getSchoolList()
                val schoolList = viewModel.schoolListResult.collectAsState()
                val schoolDetail = viewModel.schoolDetailResult.collectAsState()
                Greeting(
                    schools = schoolList.value, viewModel, schoolDetail.value
                )
            }
        }
    }
}
@Composable
fun Greeting(
    schools: ApiResponse,
    viewModel: SchoolListViewModel,
    schoolDetail: SchoolDetailResponse
) {

    val isShowDialog = remember { mutableStateOf(false) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {


        LazyColumn {
            when (schools) {
                is ApiResponse.Error -> {}
                is ApiResponse.Success -> {
                    items(schools.schoolList.size) {
                        Text(
                            text = schools.schoolList[it].school_name,
                            modifier = Modifier.clickable {
                                isShowDialog.value = true
                                onItemClick(schools.schoolList[it].dbn, viewModel)
                            })
                        Text(text = schools.schoolList[it].phone_number)
                        HorizontalDivider(thickness = 2.dp)

                    }
                }
            }


        }
        if(isShowDialog.value) {
            CustomAlertDialog(schoolDetail,
                onDismiss = {
                    isShowDialog.value = false
                    println(isShowDialog.value)
                }
            )
        }
    }
}

@Composable
fun CustomAlertDialog(schoolDetail:SchoolDetailResponse,onDismiss: () -> Unit) {
    when (schoolDetail) {
            is SchoolDetailResponse.Error -> {}
            is SchoolDetailResponse.Success -> {
                AlertDialog(
                    onDismissRequest = onDismiss,
                    title = { Text(schoolDetail.schoolDetail.school_name) },
                    text = { Text("numOfSatTestTakers: ${schoolDetail.schoolDetail.num_of_sat_test_takers}") },
                    confirmButton = {
                        Button(onClick = {
                            // Handle the confirmation action
                            onDismiss()
                        }) {
                            Text("Okay")
                        }
                    },
                    dismissButton = {
                        Button(onClick = onDismiss) {
                            Text("Cancel")
                        }
                    }
                )
            }
        }

}

fun showToast(
    current: Context,
    schoolName: String,
    numOfSatTestTakers: String,
    satCriticalReadingAvgScore: String,
    satMathAvgScore: String,
    satWritingAvgScore: String
) {
    Toast.makeText(
        current,
        "SchoolName : $schoolName , numOfSatTestTakers: $numOfSatTestTakers," +
                " satCriticalReadingAvgScore : $satCriticalReadingAvgScore,satMathAvgScore $satMathAvgScore,satWritingAvgScore:$satWritingAvgScore",
        Toast.LENGTH_LONG
    ).show()
}

fun onItemClick(dbn: String, viewModel: SchoolListViewModel) {
    viewModel.getSchoolDetail(dbn)
}

