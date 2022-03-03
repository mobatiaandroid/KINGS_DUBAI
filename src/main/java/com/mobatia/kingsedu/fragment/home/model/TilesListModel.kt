package com.mobatia.kingsedu.fragment.home.model

import com.google.gson.annotations.SerializedName
import com.mobatia.kingsedu.fragment.calendar.model.CalendarResponseArray

class TilesListModel (
    @SerializedName("status") val status: Int,
    @SerializedName("responseArray") val responseArray: TilesResponseArray
)