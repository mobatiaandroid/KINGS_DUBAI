package com.mobatia.kingsedu.fragment.calendar.model

import com.google.gson.annotations.SerializedName
import com.mobatia.kingsedu.fragment.apps.model.AppsListDetailModel

class CalendarModel (
    @SerializedName("status") val status: Int,
   @SerializedName("responseArray") val calendarList: List<CalendarListResponse>
)