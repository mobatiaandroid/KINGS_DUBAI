package com.mobatia.kingsedu.fragment.home.model

import com.google.gson.annotations.SerializedName

class CountryResponseArray (
    @SerializedName("countries") val countriesList: List<CountryiesDetailModel>
)