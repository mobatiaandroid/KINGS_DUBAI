package com.mobatia.kingsedu.fragment.attendance.model

import com.google.gson.annotations.SerializedName

class AttendanceListModel (
    @SerializedName("status") val status: Int,
    @SerializedName("responseArray") val responseArray: AttendanceResponseArray
)
