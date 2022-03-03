package com.mobatia.kingsedu.activity.settings.termsofservice.model

import com.google.gson.annotations.SerializedName
import com.mobatia.kingsedu.fragment.messages.model.MessageListDetailModel

data class TermsResponseArray (
    //@SerializedName("terms_of_service") val termsOfServiceList: List<TermsOfServiceDetailModel>
    @SerializedName("terms_of_service") val termsofService: TermsOfServiceDetailModel
)