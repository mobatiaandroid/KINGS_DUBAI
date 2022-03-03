package com.mobatia.kingsedu.activity.communication.newsletter.model

import com.google.gson.annotations.SerializedName
import com.mobatia.kingsedu.fragment.messages.model.MessageListDetailModel

class NewsLetterResponseModel (
    @SerializedName("campaigns") val campaignsList: List<NewLetterListDetailModel>

)
