package com.mobatia.kingsedu.fragment.contact_us.model

import com.google.gson.annotations.SerializedName

 class ContactusModel (
    @SerializedName("status") val status: Int,
    @SerializedName("responseArray") val responseArray: ContactusResponseArray
)