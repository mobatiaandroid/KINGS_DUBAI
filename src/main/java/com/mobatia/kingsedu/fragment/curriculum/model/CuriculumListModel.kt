package com.mobatia.kingsedu.fragment.curriculum.model

import com.google.gson.annotations.SerializedName
import com.mobatia.kingsedu.fragment.reports.model.ReportResponseArray

class CuriculumListModel (
    @SerializedName("status") val status: Int,
    @SerializedName("responseArray") val data: List<CuriculumResponseArray>
)