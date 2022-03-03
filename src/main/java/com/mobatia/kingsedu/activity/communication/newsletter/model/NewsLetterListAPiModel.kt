package com.mobatia.kingsedu.activity.communication.newsletter.model

import com.google.gson.annotations.SerializedName

data class NewsLetterListAPiModel (
    @SerializedName("start") val start: Int,
    @SerializedName("limit") val limit: Int
)