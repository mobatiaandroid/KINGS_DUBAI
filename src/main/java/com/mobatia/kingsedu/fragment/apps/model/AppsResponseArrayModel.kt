package com.mobatia.kingsedu.fragment.apps.model

import com.google.gson.annotations.SerializedName
import com.mobatia.kingsedu.fragment.messages.model.MessageListDetailModel

data class AppsResponseArrayModel (
    @SerializedName("apps") val appsList: List<AppsListDetailModel>
)