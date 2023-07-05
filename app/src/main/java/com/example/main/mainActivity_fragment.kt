package com.example.main

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDate


class mainActivity_fragment : Fragment(), CalendarAdapter.OnItemListener {

    private var monthYearText: TextView? = null
    private var calendarRecyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setMonthView() {
        monthYearText!!.text = CalendarUtils.monthYearFromDate(CalendarUtils.selectedDate)
        val daysInMonth = CalendarUtils.daysInMonthArray(CalendarUtils.selectedDate)
        val calendarAdapter = CalendarAdapter(daysInMonth, this)
        val layoutManager: RecyclerView.LayoutManager =
            GridLayoutManager(context, 7)
        calendarRecyclerView!!.layoutManager = layoutManager
        calendarRecyclerView!!.adapter = calendarAdapter
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val rootView = inflater.inflate(
            R.layout.fragment_main_activity_fragment,
            container,
            false
        ) as ViewGroup


        fun initWidgets() {
            calendarRecyclerView = rootView.findViewById<RecyclerView>(R.id.calendarRecyclerView)
            monthYearText = rootView.findViewById<TextView>(R.id.monthYearTV)
        }



        initWidgets()
        CalendarUtils.selectedDate = LocalDate.now()
        setMonthView()

        val imageButton: Button = rootView.findViewById(R.id.daily_button) as Button
        imageButton.setOnClickListener {
            val intent = Intent(context, WeekViewActivity::class.java)
            startActivity(intent)
        }

        //이전달로 이동
        val previousMonth = rootView.findViewById<TextView>(R.id.previousMonth)
        previousMonth.setOnClickListener{
            CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusMonths(1)
            setMonthView()
        }
        //다음달로 이동
        val nextMonth = rootView.findViewById<TextView>(R.id.nextMonth)
        nextMonth.setOnClickListener{
            CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusMonths(1)
            setMonthView()
        }

        return rootView
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onItemClick(position: Int, date: LocalDate?) {
        if (date != null) {
            CalendarUtils.selectedDate = date
            setMonthView()
        }
    }

    override fun initWidgets() {
        TODO("Not yet implemented")
    }


}