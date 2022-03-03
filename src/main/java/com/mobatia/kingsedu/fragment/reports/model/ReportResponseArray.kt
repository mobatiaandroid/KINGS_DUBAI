package com.mobatia.kingsedu.fragment.reports.model

import com.google.gson.annotations.SerializedName


class ReportResponseArray (

    @SerializedName("Acyear") val Acyear: String,
    //    @SerializedName("data") val data: ReportListDetailModel

    @SerializedName("data") val data: ArrayList<ReportListDetailModel>

)