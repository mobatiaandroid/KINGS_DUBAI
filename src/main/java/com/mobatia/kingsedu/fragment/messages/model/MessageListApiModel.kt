package com.mobatia.kingsedu.fragment.messages.model

import com.google.gson.annotations.SerializedName

data class MessageListApiModel (
    @SerializedName("start") val start: Int,
    @SerializedName("limit") val limit: Int
)

