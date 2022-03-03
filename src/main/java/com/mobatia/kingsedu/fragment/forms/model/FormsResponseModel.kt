package com.mobatia.kingsedu.fragment.forms.model

import com.google.gson.annotations.SerializedName
import com.mobatia.kingsedu.fragment.messages.model.MessageListDetailModel
import com.mobatia.kingsedu.fragment.messages.model.MessageResponseArrayModel

class FormsResponseModel (
    @SerializedName("status") val status: Int,
    @SerializedName("responseArray") val responseArrayList: List<FormsResponseArrayDetail>
)
