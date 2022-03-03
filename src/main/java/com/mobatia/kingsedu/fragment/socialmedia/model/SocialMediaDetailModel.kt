package com.mobatia.kingsedu.fragment.socialmedia.model

import com.google.gson.annotations.SerializedName

data class SocialMediaDetailModel (

    @SerializedName("id") val id: String,
    @SerializedName("url") val url: String,
    @SerializedName("tab_type") val tab_type: String,
    @SerializedName("image") val image: String,
    @SerializedName("page_id") val page_id: String
)

//SocialMediaDetailModel