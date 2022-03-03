package com.mobatia.kingsedu.activity.term_dates.model

import com.google.gson.annotations.SerializedName
import com.mobatia.kingsedu.fragment.termdates.model.TermDatesListDetailModel

data class TermDateDetailsResponseArray (
    @SerializedName("termdates") val termdates: TermDatesDetailArrayList
)

