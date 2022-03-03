package com.mobatia.kingsedu.fragment.teacher_contact.model

import com.google.gson.annotations.SerializedName
import com.mobatia.kingsedu.fragment.student_information.model.StudentInfoDetail

data class StaffResponseArray (
    @SerializedName("staff_list") val staff_info: List<StaffInfoDetail>
)
