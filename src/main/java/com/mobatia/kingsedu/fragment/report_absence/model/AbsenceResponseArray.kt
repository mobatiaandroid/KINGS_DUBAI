package com.mobatia.kingsedu.fragment.report_absence.model

import com.google.gson.annotations.SerializedName
import com.mobatia.kingsedu.fragment.student_information.model.StudentList

data class AbsenceResponseArray (
    @SerializedName("request") val requestList: List<AbsenceRequestListDetailModel>

)
