package com.mobatia.kingsedu.fragment.time_table.model.apimodel

import com.google.gson.annotations.SerializedName
import com.mobatia.kingsedu.fragment.termdates.model.TermDatesListDetailModel
import com.mobatia.kingsedu.fragment.time_table.model.apimodel.RangeApiModel

class TimeTableResponseArray (
    @SerializedName("range") val range: RangeApiModel,
    @SerializedName("field") val field1List: List<FieldApiListModel>,
    @SerializedName("pdf_timetable") val pdf_timetable:String = "",
    @SerializedName("Timetable") val timeTableList: List<TimeTableApiListModel>
)