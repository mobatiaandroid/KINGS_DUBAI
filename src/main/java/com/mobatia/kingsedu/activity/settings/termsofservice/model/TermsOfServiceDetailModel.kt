package com.mobatia.kingsedu.activity.settings.termsofservice.model

import com.google.gson.annotations.SerializedName

data class TermsOfServiceDetailModel (
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String
)
