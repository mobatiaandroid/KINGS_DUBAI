package com.mobatia.kingsedu.fragment.socialmedia.model

import com.google.gson.annotations.SerializedName
import com.mobatia.kingsedu.fragment.report_absence.model.AbsenceResponseArray

data class SocialMediaListModel (
    @SerializedName("status") val status: Int,
    @SerializedName("responseArray") val responseArray: SocialMediaResponseArray

)



//SocialMediaListModel