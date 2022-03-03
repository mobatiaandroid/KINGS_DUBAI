package com.mobatia.kingsedu.fragment.messages.model

import com.google.gson.annotations.SerializedName
import com.mobatia.kingsedu.fragment.student_information.model.ResponseArray

data class MessageListModel (
    @SerializedName("status") val status: Int,
    @SerializedName("responseArray") val responseArray: MessageResponseArrayModel
)
