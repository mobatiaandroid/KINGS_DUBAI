package com.mobatia.kingsedu.fragment.student_information.model

import com.google.gson.annotations.SerializedName

data class ResponseArray (
    @SerializedName("student_list") val studentList: List<StudentList>

)