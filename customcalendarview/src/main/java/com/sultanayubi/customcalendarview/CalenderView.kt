package com.sultanayubi.customcalendarview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.ColorUtils
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class CustomCalendarView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var selectedDateColor: Int = ResourcesCompat.getColor(resources, R.color.js_orange, null)

    private val paint = Paint()
    private val calendar = Calendar.getInstance()
    private val dateFormat = SimpleDateFormat("MMM yyyy", Locale.getDefault())



    private val today = Calendar.getInstance()
    private val sixMonthsAgo = Calendar.getInstance().apply {
        add(Calendar.MONTH, -6)
    }



    private val topMargin = 0 // Margin from the top of the view to the month name
    private val headerMargin = 80 // Margin between the month name and the days of the week
    private val cellSize: Int
        get() = width / 10

    private var startDate: Calendar? = null
    private var endDate: Calendar? = null
    private var isRangeEnabled = false // Flag to enable/disable range selection

    init {
        val typeface = ResourcesCompat.getFont(context, R.font.manrope_regular)
        paint.typeface = typeface
        paint.color = Color.BLACK
        paint.textSize = 25f
        paint.textAlign = Paint.Align.CENTER
        paint.strokeWidth = 2f
    }

    fun setSelectedDateColor(color: Int) {
        selectedDateColor = color
        invalidate() // Redraw the view to apply the new color
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawDaysOfWeek(canvas)
        drawDaysOfMonth(canvas)
    }

    private fun drawDaysOfWeek(canvas: Canvas) {
        val daysOfWeek = arrayOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
        paint.color = Color.BLACK // Ensure weekdays are always black
        for (i in daysOfWeek.indices) {
            canvas.drawText(
                daysOfWeek[i],
                (i * cellSize + cellSize / 2).toFloat(),
                (topMargin + headerMargin).toFloat(),
                paint
            )
        }
    }

    private fun drawDaysOfMonth(canvas: Canvas) {
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        val firstDayOfMonth = calendar.get(Calendar.DAY_OF_WEEK) - 1
        val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

        for (day in 1..daysInMonth) {
            val column = (day + firstDayOfMonth - 1) % 7
            val row = (day + firstDayOfMonth - 1) / 7
            val dayX = (column * cellSize + cellSize / 2).toFloat()
            val dayY = (50 + headerMargin + row * cellSize + cellSize / 2).toFloat()

            val dayCalendar = Calendar.getInstance().apply {
                set(Calendar.DAY_OF_MONTH, day)
                set(Calendar.MONTH, calendar.get(Calendar.MONTH))
                set(Calendar.YEAR, calendar.get(Calendar.YEAR))
            }

            // Set color for disabled dates
            paint.color = if (dayCalendar.before(sixMonthsAgo) || dayCalendar.after(today)) {
                Color.LTGRAY // Disabled color for dates out of range
            } else {
                Color.BLACK // Normal color for regular days
            }

            // Highlight the current date (today)
            if (day == today.get(Calendar.DAY_OF_MONTH) &&
                calendar.get(Calendar.MONTH) == today.get(Calendar.MONTH) &&
                calendar.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
                startDate == null && endDate == null
            ) {
//                paint.color = ResourcesCompat.getColor(resources, R.color.js_orange, null)
                paint.color = selectedDateColor // Use the custom selected color
                canvas.drawCircle(
                    dayX,
                    dayY - paint.textSize / 2,
                    30f,
                    paint
                ) // Circle background for today
                paint.color = Color.WHITE // Text color for today's date
                canvas.drawText(day.toString(), dayX, dayY, paint)
                paint.color = Color.WHITE // Reset text color
            } else if(day == today.get(Calendar.DAY_OF_MONTH) &&
                calendar.get(Calendar.MONTH) == today.get(Calendar.MONTH) &&
                calendar.get(Calendar.YEAR) == today.get(Calendar.YEAR)) {
                canvas.drawText(day.toString(), dayX, dayY, paint)
                paint.color = Color.BLACK // Reset text color
            }else {
                canvas.drawText(day.toString(), dayX, dayY, paint)
            }

            // Highlight the startDate if selected, even if endDate is not selected
            if (isRangeEnabled && startDate != null && endDate == null) {
                if (dayCalendar.get(Calendar.YEAR) == startDate!!.get(Calendar.YEAR) &&
                    dayCalendar.get(Calendar.MONTH) == startDate!!.get(Calendar.MONTH) &&
                    dayCalendar.get(Calendar.DAY_OF_MONTH) == startDate!!.get(Calendar.DAY_OF_MONTH)
                ) {
//                    paint.color = ResourcesCompat.getColor(resources, R.color.js_orange, null)
                    paint.color = selectedDateColor // Use the custom selected color
                    canvas.drawCircle(
                        dayX,
                        dayY - paint.textSize / 2,
                        30f,
                        paint
                    ) // Circle background for startDate
                    paint.color = Color.WHITE // Text color for startDate
                    canvas.drawText(day.toString(),dayX,dayY,paint)
                    paint.color = Color.WHITE
                }else{
                    canvas.drawText(day.toString(),dayX,dayY,paint)
                }
            }

           // Highlight the startDate and endDate if both are selected
            if (isRangeEnabled && startDate != null && endDate != null) {
                if ((dayCalendar.get(Calendar.YEAR) == startDate!!.get(Calendar.YEAR) &&
                            dayCalendar.get(Calendar.MONTH) == startDate!!.get(Calendar.MONTH) &&
                            dayCalendar.get(Calendar.DAY_OF_MONTH) == startDate!!.get(Calendar.DAY_OF_MONTH)) ||
                    (dayCalendar.get(Calendar.YEAR) == endDate!!.get(Calendar.YEAR) &&
                            dayCalendar.get(Calendar.MONTH) == endDate!!.get(Calendar.MONTH) &&
                            dayCalendar.get(Calendar.DAY_OF_MONTH) == endDate!!.get(Calendar.DAY_OF_MONTH))
                ) {
//                    paint.color = ResourcesCompat.getColor(resources, R.color.js_orange, null)
                    paint.color = selectedDateColor // Use the custom selected color
                    canvas.drawCircle(
                        dayX,
                        dayY - paint.textSize / 2,
                        30f,
                        paint
                    ) // Circle background for startDate/endDate
                    paint.color = Color.WHITE // Text color for selected dates
                    canvas.drawText(day.toString(),dayX,dayY,paint)
                    paint.color = Color.WHITE
                }else{
                    canvas.drawText(day.toString(),dayX,dayY,paint)
                }
            }

           // Highlight the range between startDate and endDate
            if (isRangeEnabled && startDate != null && endDate != null &&
                !dayCalendar.before(startDate) && !dayCalendar.after(endDate)
            ) {
                //paint.color = ResourcesCompat.getColor(resources, R.color.js_orange, null)
                paint.color = selectedDateColor // Use the custom selected color
                canvas.drawCircle(
                    dayX,
                    dayY - paint.textSize / 2,
                    30f,
                    paint
                ) // Background for range
                paint.color = Color.BLACK // Text color for range
                canvas.drawText(day.toString(),dayX,dayY,paint)
                paint.color = Color.WHITE
            }else{
                canvas.drawText(day.toString(),dayX,dayY,paint)
            }

            // Draw the day number
            canvas.drawText(day.toString(), dayX, dayY, paint)
        }
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val cellSize = measuredWidth / 7 // Adjusted to 7 columns for days of the week
        val rows = (calendar.getActualMaximum(Calendar.DAY_OF_MONTH) + calendar.get(Calendar.DAY_OF_WEEK) - 2) / 7 + 1
        val desiredHeight = topMargin + headerMargin + rows * cellSize + cellSize

        setMeasuredDimension(measuredWidth, desiredHeight)
    }

    fun nextMonth(monthText: TextView) {
        calendar.add(Calendar.MONTH, 1)
        monthText.text = dateFormat.format(calendar.time)
        invalidate()
    }

    fun previousMonth(monthText: TextView) {
        calendar.add(Calendar.MONTH, -1)
        monthText.text = dateFormat.format(calendar.time)
        invalidate()
    }

    fun getCurrentMonthName(): String {
        return dateFormat.format(calendar.time)
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN && isRangeEnabled) {
            val x = event.x
            val y = event.y

            val column = (x / cellSize).toInt()
            var row = ((y - (topMargin + headerMargin)) / cellSize).toInt()
            val firstDayOfMonth = calendar.get(Calendar.DAY_OF_WEEK) - 1
            var day = column + row * 7 - firstDayOfMonth + 1

            // Adjust the row calculation to take into account the height of the cell
            // and the position of the touch event within the cell
            val cellHeight = cellSize
            val cellTop = row * cellHeight + topMargin + headerMargin
            val cellBottom = cellTop + cellHeight
            if (y > cellTop + cellHeight / 2 && y < cellBottom) {
                // do nothing, use the current row
            } else if (y < cellTop + cellHeight / 2 && row > 0) {
                row--
                day = column + row * 7 - firstDayOfMonth + 1
            } else if (y > cellBottom && row < 5) {
                row++
                day = column + row * 7 - firstDayOfMonth + 1
            }

            if (day in 1..calendar.getActualMaximum(Calendar.DAY_OF_MONTH)) {
                val clickedDate = Calendar.getInstance().apply {
                    set(Calendar.YEAR, calendar.get(Calendar.YEAR))
                    set(Calendar.MONTH, calendar.get(Calendar.MONTH))
                    set(Calendar.DAY_OF_MONTH, day)
                }

                if (!clickedDate.before(sixMonthsAgo) && clickedDate.before(today) || clickedDate.get(Calendar.YEAR) == today.get(Calendar.YEAR) && clickedDate.get(Calendar.MONTH) == today.get(Calendar.MONTH) && clickedDate.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH)) {
                    if (startDate == null) {
                        startDate = clickedDate
                    } else if (endDate == null) {
                        if (clickedDate.after(startDate)) {
                            endDate = clickedDate
                        } else {
                            startDate = clickedDate
                            endDate = null
                        }
                    } else {
                        startDate = clickedDate
                        endDate = null
                    }
                    invalidate()
                }
            }
        }
        return super.onTouchEvent(event)
    }

    fun enableDateRangeSelection() {
        isRangeEnabled = true
    }

    fun disableDateRangeSelection() {
        isRangeEnabled = false
        startDate = null
        endDate = null
        invalidate()
    }


    private val dateFormat1 = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())

    // Method for today
    fun getToday(): String {
        val calendar = Calendar.getInstance() // Create a new calendar instance for today
        return dateFormat1.format(calendar.time)
    }

    // Method for yesterday
    fun getYesterday(): String {
        val calendar = Calendar.getInstance() // Always start from the current date
        calendar.add(Calendar.DAY_OF_YEAR, -1) // Move to yesterday
        return dateFormat1.format(calendar.time)
    }

    fun getLastXDays(days: Int): Pair<String, String> {
        val calendar = Calendar.getInstance() // Create a new calendar instance for current date
        val endDate = dateFormat1.format(calendar.time) // Today's date
        calendar.add(Calendar.DAY_OF_YEAR, -days) // Move back by the specified number of days
        val startDate = dateFormat1.format(calendar.time) // Start date
        return Pair(startDate, endDate) // Return the date range
    }

    fun getSelectedDateRange(): Pair<Any?, Any?> {
        if (startDate == null){
            val start = dateFormat1.format(today.time)
            return Pair(start, start)
        }else if (endDate == null){
            val start = dateFormat1.format(startDate!!.time)
            val end = dateFormat1.format(startDate!!.time)
            return Pair(start, end)
        }
        val startdate = dateFormat1.format(startDate!!.time)
        val enddate = dateFormat1.format(endDate!!.time)
        return Pair(startdate, enddate)
    }

}
