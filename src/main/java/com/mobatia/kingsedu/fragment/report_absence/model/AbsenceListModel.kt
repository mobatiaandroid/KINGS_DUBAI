package com.mobatia.kingsedu.fragment.report_absence.model

import com.google.gson.annotations.SerializedName
import com.mobatia.kingsedu.fragment.student_information.model.ResponseArray

data class AbsenceListModel (
    @SerializedName("status") val status: Int,
    @SerializedName("responseArray") val responseArray: AbsenceResponseArray

)





//AbsenceListModel