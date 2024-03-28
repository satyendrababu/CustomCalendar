package com.demo.customcalendar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.demo.customcalendar.databinding.CalendarDateLayoutBinding

class CalendarAdapter(
    private val list: ArrayList<CalendarModel>,
    private val dateItemClickListener: DateItemClickListener
): RecyclerView.Adapter<CalendarAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CalendarDateLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        val lp = RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
        )
        binding.root.apply {
            layoutParams = lp
        }
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    inner class ViewHolder(private val binding: CalendarDateLayoutBinding):
        RecyclerView.ViewHolder(binding.root){

            private val layout = binding.root

            fun bind(calendarModel: CalendarModel) {
                val day = calendarModel.day
                binding.apply {
                    num.text = "$day"

                    when (calendarModel.state) {
                        -1 -> {
                            calendarDateLayout.visibility = View.INVISIBLE
                        }
                        0 -> {
                            num.setTextColor(ContextCompat.getColor(itemView.context, R.color.grey))
                            today.visibility = View.INVISIBLE
                        }
                        1 -> {
                            num.setTextColor(ContextCompat.getColor(itemView.context, R.color.green))
                            num.background = ContextCompat.getDrawable(itemView.context, R.drawable.shape_circle_green)
                            today.visibility = View.INVISIBLE
                            layout.setOnClickListener {
                                dateItemClickListener.onDateClick(calendarModel)
                            }
                        }
                        2 -> {
                            num.setTextColor(ContextCompat.getColor(itemView.context, R.color.red))
                            num.background = ContextCompat.getDrawable(itemView.context, R.drawable.shape_circle_red)
                            today.visibility = View.VISIBLE
                            layout.setOnClickListener {
                                dateItemClickListener.onDateClick(calendarModel)
                            }
                        }
                        3 -> {
                            num.setTextColor(ContextCompat.getColor(itemView.context, R.color.grey))
                            num.background = ContextCompat.getDrawable(itemView.context, R.drawable.shape_circle_grey)
                            today.visibility = View.INVISIBLE
                            layout.setOnClickListener {
                                dateItemClickListener.onDateClick(calendarModel)
                            }
                            //here you can manage future date as per your requirement
                        }

                    }
                }
            }
    }
}