package com.mobatia.kingsedu.activity.message.model

import com.google.gson.annotations.SerializedName
import com.mobatia.kingsedu.activity.communication.newsletter.model.NewsLetterDetailResponseModel

data class MessageDetailResponseModel (
    @SerializedName("notification") val notificationArray:MessageDetailNotificationModel
)