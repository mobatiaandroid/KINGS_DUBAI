package com.mobatia.kingsedu.fragment.calendar.model

import com.google.gson.annotations.SerializedName

data class VEVENT(
    @SerializedName("DTSTART") val DTSTART: String,
    @SerializedName("DTEND") val DTEND: String,
    @SerializedName("SUMMARY") val SUMMARY: String,
    @SerializedName("DESCRIPTION") val DESCRIPTION: String,
    @SerializedName("LOCATION") val LOCATION: String)


