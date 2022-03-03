package com.mobatia.kingsedu.activity.settings.termsofservice.model

import com.google.gson.annotations.SerializedName
import com.mobatia.kingsedu.fragment.messages.model.MessageResponseArrayModel

data class TermsOfServiceModel (
    @SerializedName("status") val status: Int,
    @SerializedName("responseArray") val responseArray: TermsResponseArray
)
