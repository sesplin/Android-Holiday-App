package com.example.holidaycalendar

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

private const val ARG_GALLERY_ITEM_NAME = "holiday_name"
private const val ARG_GALLERY_ITEM_DATE = "holiday_date"
private const val ARG_GALLERY_ITEM_DESC = "holiday_desc"

class HolidayFragment: Fragment() {

    private lateinit var nameTextView: TextView
    private lateinit var dateTextView: TextView
    private lateinit var descTextView: TextView

    private lateinit var holidayName: String
    private lateinit var holidayDate: String
    private lateinit var holidayDesc: String

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        holidayName = arguments?.getSerializable(ARG_GALLERY_ITEM_NAME) as String
        holidayDate = arguments?.getSerializable(ARG_GALLERY_ITEM_DATE) as String
        holidayDesc = arguments?.getSerializable(ARG_GALLERY_ITEM_DESC) as String
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_holiday, container, false)

        nameTextView = view.findViewById(R.id.titleTextView)
        dateTextView = view.findViewById(R.id.dateTextView)
        descTextView = view.findViewById(R.id.DescriptionTextView)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nameTextView.text = holidayName
        dateTextView.text = holidayDate
        descTextView.text = holidayDesc
    }

    companion object{
        fun newInstance(holiday: Holiday): HolidayFragment{
            val fragment = HolidayFragment()

            val arguments = Bundle()
            arguments.putSerializable(ARG_GALLERY_ITEM_NAME, holiday.name)
            arguments.putSerializable(ARG_GALLERY_ITEM_DATE, holiday.date.datetime.day.toString() + "/" + holiday.date.datetime.month.toString() + "/" + holiday.date.datetime.year.toString())
            arguments.putSerializable(ARG_GALLERY_ITEM_DESC, holiday.description)
            fragment.arguments = arguments

            return fragment
        }
    }
}