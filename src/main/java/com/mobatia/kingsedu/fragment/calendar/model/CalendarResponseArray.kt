package com.mobatia.kingsedu.fragment.calendar.model

import com.google.gson.annotations.SerializedName
import com.mobatia.kingsedu.fragment.messages.model.MessageListDetailModel

data class CalendarResponseArray (
    @SerializedName("calender") val calendarDetail: CalendarListDetailModel

)
/* @SerializedName("calender") val calenderList: List<CalendarListDetailModel>*/