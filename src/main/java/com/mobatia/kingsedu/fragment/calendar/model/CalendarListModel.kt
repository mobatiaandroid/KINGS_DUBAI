package com.mobatia.kingsedu.fragment.calendar.model

import com.google.gson.annotations.SerializedName

class CalendarListModel (
    @SerializedName("status") val status: Int,
    @SerializedName("responseArray") val responseArray: CalendarResponseArray
    )