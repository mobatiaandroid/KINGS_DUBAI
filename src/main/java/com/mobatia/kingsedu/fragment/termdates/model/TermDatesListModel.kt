package com.mobatia.kingsedu.fragment.termdates.model

import com.google.gson.annotations.SerializedName

data class TermDatesListModel (

    @SerializedName("status") val status: Int,
    @SerializedName("responseArray") val responseArray: TermDateResponseArray

)
