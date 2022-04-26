package com.example.holidaycalendar.api

import com.example.holidaycalendar.Holiday
import com.google.gson.annotations.SerializedName

class HolidayResponse {
    @SerializedName("holidays")
    lateinit var holidayList: List<Holiday>
}