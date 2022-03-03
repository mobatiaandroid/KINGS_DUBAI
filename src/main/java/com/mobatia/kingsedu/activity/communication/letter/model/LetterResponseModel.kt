package com.mobatia.kingsedu.activity.communication.letter.model

import com.google.gson.annotations.SerializedName
import com.mobatia.kingsedu.activity.communication.newsletter.model.NewLetterListDetailModel
import com.mobatia.kingsedu.activity.communication.newsletter.model.NewsLetterResponseModel

class LetterResponseModel (
    @SerializedName("status") val status: Int,
    @SerializedName("responseArray") val responseArrayList: List<LetterResponseListModel>
)