package com.mobatia.kingsedu.fragment.home.model

import com.google.gson.annotations.SerializedName

class CountryListModel(
    @SerializedName("status") val status: Int,
    @SerializedName("responseArray") val responseArray: CountryResponseArray
)