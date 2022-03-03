package com.mobatia.kingsedu.fragment.teacher_contact.model

import com.google.gson.annotations.SerializedName
import com.mobatia.kingsedu.fragment.student_information.model.ResponseArray

data class StaffListModel (
    @SerializedName("status") val status: Int,
    @SerializedName("responseArray") val responseArray: StaffResponseArray
)
