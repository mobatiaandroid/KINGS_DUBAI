package com.mobatia.kingsedu.fragment.forms.model

import com.google.gson.annotations.SerializedName

class FormsResponseArrayDetail (
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("url") val url: String
)
