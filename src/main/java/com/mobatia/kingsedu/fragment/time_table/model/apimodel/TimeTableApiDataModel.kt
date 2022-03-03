package com.mobatia.kingsedu.fragment.time_table.model.apimodel

import com.google.gson.annotations.SerializedName

class TimeTableApiDataModel (
    @SerializedName("status") val status: Int,
    @SerializedName("responseArray") val responseArray: TimeTableResponseArray
)