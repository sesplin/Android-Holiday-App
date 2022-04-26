package com.example.holidaycalendar

import com.google.gson.annotations.SerializedName

data class Holiday (
    var name: String = "",
    var description: String = "",
    var date: DateResponse
)
