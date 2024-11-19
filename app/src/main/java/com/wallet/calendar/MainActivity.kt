package com.wallet.calendar

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.sultanayubi.customcalendarview.PastDatesResponse
import com.sultanayubi.customcalendarview.TransactionDatesAdapter
import com.sultanayubi.customcalendarview.databinding.TransactionHistoryCalendarBinding
import com.wallet.calendar.SafeClicking.setSafeOnClickListener
import com.wallet.calendar.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private lateinit var transactionDatesAdapter: TransactionDatesAdapter
    private var selectedDate = ""
    private var selectedPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showCalendarDialog()
    }

    private val pastDateListEntries = arrayListOf(
        PastDatesResponse(1, "Today"),
        PastDatesResponse(2, "Yesterday"),
        PastDatesResponse(3, "Last 30 days"),
        PastDatesResponse(4, "Last 60 days"),
        PastDatesResponse(5, "Last 90 days"),
        PastDatesResponse(6, "Last 180 days"),
        PastDatesResponse(7, "Custom")
    )

    private fun showCalendarDialog() {

        val calendarDialogBinding: TransactionHistoryCalendarBinding = DataBindingUtil.inflate(
            LayoutInflater.from(this),
            com.sultanayubi.customcalendarview.R.layout.transaction_history_calendar,
            null,
            false
        )

        val dialog = AlertDialog.Builder(this).setView(calendarDialogBinding.root).create()

        val selectedColor = ResourcesCompat.getColor(resources, R.color.navyBlue, null)
        calendarDialogBinding.customCalendarView.setSelectedDateColor(selectedColor)// Change this to your desired color

        transactionDatesAdapter = TransactionDatesAdapter(this) { item ->
            // Update the selected position and date when an item is clicked
            selectedPosition = item.id - 1 // Assuming ID is 1-based
            selectedDate = item.pastDate

            // Enable or disable date range selection based on the selected position
            if (selectedPosition in 0..5) {
                calendarDialogBinding.customCalendarView.disableDateRangeSelection()
            } else {
                calendarDialogBinding.customCalendarView.enableDateRangeSelection()
            }
        }

        //change textviewbackground
        transactionDatesAdapter.setTextViewBackground(R.drawable.white_bg_calendar)

        // Set default colors for the adapter
        val selectedTextColor = ResourcesCompat.getColor(resources, R.color.navyBlue, null)
        transactionDatesAdapter.setSelectedTextColor(selectedTextColor)

        val unselectedTextColor = ResourcesCompat.getColor(resources, R.color.text_grey, null)
        transactionDatesAdapter.setUnselectedTextColor(unselectedTextColor)

        // Add items to the adapter and set it to the RecyclerView
        transactionDatesAdapter.addItems(pastDateListEntries)
        calendarDialogBinding.recordDateView.layoutManager = LinearLayoutManager(this)
        calendarDialogBinding.recordDateView.adapter = transactionDatesAdapter

        //to set month text color
        val monthTextColor = ResourcesCompat.getColor(resources, R.color.navyBlue, null)
        calendarDialogBinding .month.setTextColor(monthTextColor)
        calendarDialogBinding .month.text = calendarDialogBinding.customCalendarView.getCurrentMonthName()

        calendarDialogBinding.forwardimage.setImageResource(R.drawable.ic_arrow_forward)//to change arrows of your choice
        calendarDialogBinding.nextMonth.setSafeOnClickListener {
            calendarDialogBinding.customCalendarView.nextMonth(calendarDialogBinding.month)
        }

        calendarDialogBinding.previousimage.setImageResource(R.drawable.ic_arrow_back)
        calendarDialogBinding.previousMonth.setSafeOnClickListener {
            calendarDialogBinding.customCalendarView.previousMonth(calendarDialogBinding.month)
        }

        calendarDialogBinding.cancel.setBackgroundResource(R.drawable.blue_stroke_button)//to change cancel button background
        calendarDialogBinding.cancel.setSafeOnClickListener {
            dialog.dismiss()
        }

        calendarDialogBinding.pickDate.setBackgroundResource(R.drawable.blue_radiant_button)//to change ok button background
        calendarDialogBinding.pickDate.setSafeOnClickListener {

            when (selectedPosition) {
                0 -> {
                    val todayDate = calendarDialogBinding.customCalendarView.getToday()
                    Toast.makeText(this, "Today: $todayDate", Toast.LENGTH_SHORT).show()
                }

                1 -> {
                    val yesterdayDate = calendarDialogBinding.customCalendarView.getYesterday()
                    Toast.makeText(this, "Yesterday: $yesterdayDate", Toast.LENGTH_SHORT).show()
                }

                2 -> {
                    val (startDate, endDate) = calendarDialogBinding.customCalendarView.getLastXDays(30)
                    Toast.makeText(this, "Last 30 days: $startDate to $endDate", Toast.LENGTH_SHORT).show()
                }

                3 -> {
                    val (startDate, endDate) = calendarDialogBinding.customCalendarView.getLastXDays(60)
                    Toast.makeText(this, "Last 30 days: $startDate to $endDate", Toast.LENGTH_SHORT).show()
                }

                4 -> {
                    val (startDate, endDate) = calendarDialogBinding.customCalendarView.getLastXDays(90)
                    Toast.makeText(this, "Last 30 days: $startDate to $endDate", Toast.LENGTH_SHORT).show()
                }

                5 -> {
                    val (startDate, endDate) = calendarDialogBinding.customCalendarView.getLastXDays(180)
                    Toast.makeText(this, "Last 30 days: $startDate to $endDate", Toast.LENGTH_SHORT).show()
                }

                6 -> {
                    val (startDat, endDat) = calendarDialogBinding.customCalendarView.getSelectedDateRange()
                    if (startDat == null && endDat == null) {
                        Toast.makeText(this, "No dates selected", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this, "$startDat to $endDat", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            dialog.dismiss()
        }

        dialog.setCancelable(false)
        val inset = InsetDrawable(ColorDrawable(Color.TRANSPARENT), 10)
        dialog.window?.setBackgroundDrawable(inset)
        dialog.window?.attributes?.windowAnimations = R.style.animated_popup

        dialog.show()
    }
}