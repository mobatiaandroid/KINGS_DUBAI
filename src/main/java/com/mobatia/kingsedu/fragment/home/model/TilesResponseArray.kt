package com.mobatia.kingsedu.fragment.home.model

import com.google.gson.annotations.SerializedName
import com.mobatia.kingsedu.fragment.calendar.model.CalendarListDetailModel
import com.mobatia.kingsedu.fragment.messages.model.MessageListDetailModel

class TilesResponseArray (
    @SerializedName("titles") val titlesList: List<TitlesArrayList>

)