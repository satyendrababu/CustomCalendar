package com.demo.customcalendar

import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.demo.customcalendar.databinding.ActivityMainBinding
import org.joda.time.DateTime

class MainActivity : AppCompatActivity(), DateItemClickListener {

    private var currDate = DateTime()
    private lateinit var binding: ActivityMainBinding
    private lateinit var calendarAdapter: CalendarAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            rvCalendar.layoutManager = GridLayoutManager(this@MainActivity,7)
            rvCalendar.addItemDecoration(object : ItemDecoration(){
                override fun getItemOffsets(
                    outRect: Rect,
                    view: View,
                    parent: RecyclerView,
                    state: RecyclerView.State
                ) {
                    outRect.top = 0
                    outRect.bottom = 0
                }
            })
            rvCalendar.setHasFixedSize(true)

            monthNavigateLeft.setOnClickListener {
                currDate = currDate.minusMonths(1)
                setCalendar(currDate)
                monthYear.text = getMonth(currDate.monthOfYear,this@MainActivity) + ", " + currDate.year

            }
            monthNavigateRight.setOnClickListener {
                currDate = currDate.plusMonths(1)
                setCalendar(currDate)
                monthYear.text = getMonth(currDate.monthOfYear,this@MainActivity) + ", " + currDate.year
            }
        }
        setCalendar(currDate)
    }
    private var date: DateTime? = null
    private var dateList: MutableList<CalendarModel>? = null

    private fun setCalendar(currDate: DateTime) {
        binding.monthYear.text = getMonth(currDate.monthOfYear,this@MainActivity) + ", " + currDate.year
        date = currDate.withTime(0,0,0,0)
        val numOfDaysInThisMonth = date!!.dayOfMonth().maximumValue
        date = date!!.minusDays(date!!.dayOfMonth().get() - 1)
        var dayOfWeek = date!!.dayOfWeek
        if (dayOfWeek == 7) {
            dayOfWeek = 0
        }
        if (dateList == null) {
            dateList = ArrayList()
        }else {
            dateList!!.clear()
        }
        for (i in 1..(numOfDaysInThisMonth + dayOfWeek)) {
            var model: CalendarModel

            model = if (i <= dayOfWeek) {
                CalendarModel(0,"", -1)
            }else {
                val dateTemp = createDate(i - dayOfWeek, currDate.monthOfYear, currDate.year)

                when (isItToday(dateTemp!!)) {
                    0 -> {
                        CalendarModel(i - dayOfWeek, dateTemp.toLocalDate().toString(), 3)
                    }
                    1 -> {
                        CalendarModel(i - dayOfWeek, dateTemp.toLocalDate().toString(), 2)
                    }
                    else -> {
                        CalendarModel(i - dayOfWeek, dateTemp.toLocalDate().toString(), 1)
                    }
                }
            }
            dateList!!.add(model)
        }
        calendarAdapter = CalendarAdapter(dateList as ArrayList<CalendarModel>, this)
        binding.rvCalendar.adapter = calendarAdapter


    }

    override fun onDateClick(calendarModel: CalendarModel) {
        Toast.makeText(this, "Date: "+calendarModel.date, Toast.LENGTH_SHORT).show()
    }
}