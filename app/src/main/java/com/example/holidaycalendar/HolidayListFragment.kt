package com.example.holidaycalendar

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.holidaycalendar.api.CalendarificApi
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.util.jar.Attributes

private const val TAG = "HolidayListFragment"


class HolidayListFragment : Fragment() {

    private lateinit var calendarViewModel: CalendarViewModel
    private lateinit var holidayRecyclerView: RecyclerView


    interface Callbacks{
        fun onGalleryItemSelected(holiday: Holiday)
    }

    private var callbacks: Callbacks? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        val calendarLiveData: LiveData<List<Holiday>> = CalendarFetchr().fetchHolidays()
//        calendarLiveData.observe(
//            this,
//            Observer { holidayList ->
//                Log.d(TAG, "Response received: ${holidayList.count()}")
//            })
//    }

        calendarViewModel = ViewModelProviders.of(this).get(CalendarViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_holiday_list, container, false)

        holidayRecyclerView = view.findViewById(R.id.holiday_recycler_view)
        holidayRecyclerView.layoutManager = GridLayoutManager(context, 3)

        calendarViewModel.holidayLiveData.observe(viewLifecycleOwner, Observer { holidayList ->
            Log.d(TAG, "Response from server: received ${holidayList.size} holidays")
            holidayRecyclerView.adapter = HolidayAdapter(holidayList)
        })

        return view
    }

//    override fun onViewCreated( view: View, savedInstanceState: Bundle?){
//        super.onViewCreated(view, savedInstanceState)
//        calendarViewModel.holidayLiveData.observe(
//                viewLifecycleOwner,
//                Observer { holidayList ->
//                    Log.d(TAG, "Have items from ViewModel ${holidayList.size}")
//                    holidayRecyclerView.adapter = HolidayAdapter(holidayList)
//                }
//        )
//    }

    private inner class HolidayHolder(itemView: View)
        :RecyclerView.ViewHolder(itemView), View.OnClickListener{
        // val bindName: (CharSequence) -> Unit = itemTextView::setText
        private lateinit var holiday: Holiday

        val nameTextView: TextView = itemView.findViewById(R.id.holiday_name)
        val dateTextView: TextView = itemView.findViewById(R.id.holiday_date)

        init{
            itemView.setOnClickListener(this)
        }

        fun bind(holiday: Holiday){
            this.holiday = holiday
            nameTextView.text = this.holiday.name
            dateTextView.text = this.holiday.date.datetime.day.toString() + " / " + holiday.date.datetime.month.toString() + " / " + holiday.date.datetime.year.toString()
        }

        override fun onClick(v: View?) {
            callbacks?.onGalleryItemSelected(holiday)
        }
    }
    private inner class HolidayAdapter(private val holidayList: List<Holiday>)
        : RecyclerView.Adapter<HolidayHolder>() {

        override fun onCreateViewHolder(
                parent: ViewGroup,
                viewType: Int
        ): HolidayHolder {
            val view = layoutInflater.inflate(R.layout.holiday_item, parent, false)
            return HolidayHolder(view)
           // val textView = TextView(parent.context)
           // return HolidayHolder(textView)
        }

        override fun getItemCount(): Int = holidayList.size

        override fun onBindViewHolder(holder: HolidayHolder, position: Int) {
            val holiday = holidayList[position]
            holder.bind(holiday)
        }
    }
    companion object {
        fun newInstance(): HolidayListFragment {
            return HolidayListFragment()
        }
    }
}