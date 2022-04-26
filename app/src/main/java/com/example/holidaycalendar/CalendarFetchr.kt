package com.example.holidaycalendar

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.holidaycalendar.api.CalendarificApi
import com.example.holidaycalendar.api.CalendarificResponse
import com.example.holidaycalendar.api.HolidayResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val TAG = "CalendarFetchr"

class CalendarFetchr {

    private val calendarificApi: CalendarificApi

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://calendarific.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        calendarificApi = retrofit.create(CalendarificApi::class.java)
    }

    fun fetchHolidays(): MutableLiveData<List<Holiday>> {
        val responseLiveData: MutableLiveData<List<Holiday>> = MutableLiveData()
        val calendarificRequest: Call<CalendarificResponse> = calendarificApi.fetchHolidays()

        calendarificRequest.enqueue(object : Callback<CalendarificResponse> {

            override fun onFailure(call: Call<CalendarificResponse>, t: Throwable) {
                Log.e(TAG, "Failed to fetch holidays", t)
            }

            override fun onResponse(
                call: Call<CalendarificResponse>,
                response: Response<CalendarificResponse>
            ) {
                Log.d(TAG, "Response received")
                val calendarificResponse: CalendarificResponse? = response.body()
                val holidayResponse: HolidayResponse? = calendarificResponse?.response
                var holidayList: List<Holiday> = holidayResponse?.holidayList
                    ?: mutableListOf()
//                holidayList = holidayList.filterNot {
//                    it.url.isBlank()
//                }
                responseLiveData.value = holidayList
            }
        })
        return responseLiveData
    }
}