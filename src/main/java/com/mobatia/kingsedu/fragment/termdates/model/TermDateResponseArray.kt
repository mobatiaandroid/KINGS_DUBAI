package com.mobatia.kingsedu.fragment.termdates.model

import com.google.gson.annotations.SerializedName
import com.mobatia.kingsedu.fragment.report_absence.model.AbsenceRequestListDetailModel

data class TermDateResponseArray (
    @SerializedName("termdates") val termDatesList: List<TermDatesListDetailModel>
)
