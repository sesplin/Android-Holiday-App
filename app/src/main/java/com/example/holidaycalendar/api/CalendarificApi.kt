package com.example.holidaycalendar.api

import retrofit2.Call
import retrofit2.http.GET

interface CalendarificApi {

    @GET(
        "api/v2/holidays?" +
                "api_key=6c69a827640f9ba308623a71db58aeea1e4b8673" +
                "&country=ar" +
                "&year=2019"
    )
    fun fetchHolidays(): Call<CalendarificResponse>
}
