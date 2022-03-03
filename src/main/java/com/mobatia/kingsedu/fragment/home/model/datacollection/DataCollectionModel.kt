package com.mobatia.kingsedu.fragment.home.model.datacollection

import com.google.gson.annotations.SerializedName
import com.mobatia.kingsedu.fragment.home.model.TilesResponseArray

class DataCollectionModel (
    @SerializedName("status") val status: Int,
    @SerializedName("responseArray") val responseArray: DataCollectionResponseArray
)