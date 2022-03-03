package com.mobatia.kingsedu.fragment.messages.model

import com.google.gson.annotations.SerializedName
import com.mobatia.kingsedu.fragment.student_information.model.StudentList

data class MessageResponseArrayModel (
    @SerializedName("notifications") val notificationList: List<MessageListDetailModel>
)
