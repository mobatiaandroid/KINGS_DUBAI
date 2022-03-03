package com.mobatia.kingsedu.activity.term_dates.model

import com.google.gson.annotations.SerializedName
import com.mobatia.kingsedu.fragment.termdates.model.TermDateResponseArray

data class TermDatesDetailModel (

    @SerializedName("status") val status: Int,
    @SerializedName("responseArray") val responseArray: TermDatesDetailArrayList
)