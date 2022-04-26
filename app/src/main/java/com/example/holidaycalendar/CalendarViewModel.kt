package com.example.holidaycalendar

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class CalendarViewModel: ViewModel() {
    val holidayLiveData: LiveData<List<Holiday>>

    init{
        holidayLiveData = CalendarFetchr().fetchHolidays()
    }
}