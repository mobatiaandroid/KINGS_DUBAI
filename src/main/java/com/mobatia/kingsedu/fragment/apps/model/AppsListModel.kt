package com.mobatia.kingsedu.fragment.apps.model

import com.google.gson.annotations.SerializedName
import com.mobatia.kingsedu.fragment.messages.model.MessageResponseArrayModel

data class AppsListModel (
    @SerializedName("status") val status: Int,
    @SerializedName("responseArray") val appsList: List<AppsListDetailModel>
)