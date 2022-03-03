package com.mobatia.kingsedu.fragment.socialmedia.model

import com.google.gson.annotations.SerializedName
import com.mobatia.kingsedu.fragment.report_absence.model.AbsenceRequestListDetailModel

data class SocialMediaResponseArray (
    @SerializedName("data") val dataList: List<SocialMediaDetailModel>,
    @SerializedName("banner_image") val bannerList: List<String>

)