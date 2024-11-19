# CustomCalendar_Dialog
A highly customizable calendar library for Android.The CustomCalendarView provides an easy and customizable calendar to create a Calendar.To use the CustomCalendarView in your application, you first need to add the library to your application.

<div align="center">
	
[![](https://jitpack.io/v/SultanAyubi360/CustomCalendar_Dialog.svg)](https://jitpack.io/#SultanAyubi360/CustomCalendar_Dialog/1.0)
![Status](https://img.shields.io/badge/Status-Active-brightgreen)  ![Kotlin](https://img.shields.io/badge/Kotlin-100%25-brightgreen)

</div>

## ✨ Screenshots

<kbd>
  <img src="https://i.ibb.co/FbQzjMz/Screenshot-20241119-162242.jpg" width=30% height=30%/>
  <img src="https://i.ibb.co/jHZYkgd/Screenshot-20241119-162215.jpg.jpg" width=30% height=30%/>
  <img src="https://i.ibb.co/SxRVgPp/Screenshot-20241119-162522.jpg" width=30% height=30%/>
  <img src="https://i.ibb.co/J2DYZvb/Screenshot-20241119-163523.jpg" width=30% height=30%/>
  <img src="https://i.ibb.co/qr0xhFt/Screenshot-20241119-163835.jpg" width=30% height=30%/>
</kbd>



## Features

Currently, it supports the following features:

* Next and previous month's navigation.
* Allow various customization including background color for date selectcion,spot color,buttons and title drawables.
* Set custom drawables according to need.
* Show current date selected bydefault.
* Get today,yesterday dates and dates of days just by giving days count.
* Allow you to handle events when the user changes month and day selection means custom date selection.


## Usage

### Step 1

#### In older Gradle Style (Build.gradle (project level))
```
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```

#### In New Project Structure (Settings.gradle)
```
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        ......
        maven { url 'https://jitpack.io' }
        .....
    }
}
```

### Step 2

Include the library as a local library project or add the dependency in your build.gradle.

# For groovy DSL

```
dependencies {
   implementation 'com.github.SultanAyubi360:CustomCalendar_Dialog:1.0'
}
```

# For Kotlin DSL


```
dependencies {
    implementation ("com.github.SultanAyubi360:CustomCalendar_Dialog:1.0")
}
```

---

## Using CustomCalendarView Library

The GitHub project source includes a sample application, that is used for demonstrating the various features currently supported by this library. Once the library is added to your project, you can
include the CustomCalendarView into your activity/fragment layout using the following code snippets.

### Step 3

This is used to add options how many you want to display on calendar.Just change the options you want to change according to your need.

```groovy
 private val pastDateListEntries = arrayListOf(
        PastDatesResponse(1, "Today"),
        PastDatesResponse(2, "Yesterday"),
        PastDatesResponse(3, "Last 30 days"),
        PastDatesResponse(4, "Last 60 days"),
        PastDatesResponse(5, "Last 90 days"),
        PastDatesResponse(6, "Last 180 days"),
        PastDatesResponse(7, "Custom")
    )
```

Let us now initialize the calendar view to control the various other appearances and behavior of calendar using the following methods.

```groovy
private fun showCalendarDialog() {

        val calendarDialogBinding: TransactionHistoryCalendarBinding = DataBindingUtil.inflate(
            LayoutInflater.from(this),
            com.sultanayubi.customcalendarview.R.layout.transaction_history_calendar,//getting layout binding from library
            null,
            false
        )

        val dialog = AlertDialog.Builder(this).setView(calendarDialogBinding.root).create()

        val selectedColor = ResourcesCompat.getColor(resources, R.color.js_orange, null)//to change color of date selection
        calendarDialogBinding.customCalendarView.setSelectedDateColor(selectedColor)// use selectedColor variable in library to change

        transactionDatesAdapter = TransactionDatesAdapter(this) { item ->
            // Update the selected position and date when an item is clicked
            selectedPosition = item.id - 1
            selectedDate = item.pastDate

            // Enable or disable date range selection based on the selected position
            if (selectedPosition in 0..5) {
                calendarDialogBinding.customCalendarView.disableDateRangeSelection()
            } else {
                calendarDialogBinding.customCalendarView.enableDateRangeSelection()
            }
        }//use for setting options in recyclerview and click events

        //change background drawable of calendar options
        transactionDatesAdapter.setTextViewBackground(R.drawable.white_bg_calendar)

        // Set default colors for the adapter
        val selectedTextColor = ResourcesCompat.getColor(resources, R.color.navyBlue, null)//color used for option selected from side recycerview
        transactionDatesAdapter.setSelectedTextColor(selectedTextColor)

        val unselectedTextColor = ResourcesCompat.getColor(resources, R.color.text_grey, null)//color used for options not selected from side recycerview
        transactionDatesAdapter.setUnselectedTextColor(unselectedTextColor)

        // Add items to the adapter and set it to the RecyclerView
        transactionDatesAdapter.addItems(pastDateListEntries)
        calendarDialogBinding.recordDateView.layoutManager = LinearLayoutManager(this)
        calendarDialogBinding.recordDateView.adapter = transactionDatesAdapter

       color used for option selected from side recycerview
        val monthTextColor = ResourcesCompat.getColor(resources, R.color.navyBlue, null)//setting color for month and year textview
        calendarDialogBinding .month.setTextColor(monthTextColor)
        calendarDialogBinding .month.text = calendarDialogBinding.customCalendarView.getCurrentMonthName()

        calendarDialogBinding.forwardimage.setImageResource(R.drawable.ic_arrow_forward)//to change forward arrow of your choice use drawable
        calendarDialogBinding.nextMonth.setSafeOnClickListener {
            calendarDialogBinding.customCalendarView.nextMonth(calendarDialogBinding.month)
        }

        calendarDialogBinding.previousimage.setImageResource(R.drawable.ic_arrow_back)//to change previous arrow of your choice use drawable
        calendarDialogBinding.previousMonth.setSafeOnClickListener {
            calendarDialogBinding.customCalendarView.previousMonth(calendarDialogBinding.month)
        }

        calendarDialogBinding.cancel.setBackgroundResource(R.drawable.blue_stroke_button)//to change cancel button background can change drawable
        calendarDialogBinding.cancel.setSafeOnClickListener {
            dialog.dismiss()
        }

        calendarDialogBinding.pickDate.setBackgroundResource(R.drawable.blue_radiant_button)//to change ok button background can change drawable
        calendarDialogBinding.pickDate.setSafeOnClickListener {

            when (selectedPosition) {
                0 -> {
                    val todayDate = calendarDialogBinding.customCalendarView.getToday() //used to get current date today date
                    Toast.makeText(this, "Today: $todayDate", Toast.LENGTH_SHORT).show()
                }

                1 -> {
                    val yesterdayDate = calendarDialogBinding.customCalendarView.getYesterday() //used to get yesterday date last day date
                    Toast.makeText(this, "Yesterday: $yesterdayDate", Toast.LENGTH_SHORT).show()
                }

                2 -> {
                    val (startDate, endDate) = calendarDialogBinding.customCalendarView.getLastXDays(30) //used to get last 30 days dates just pass 30 as parameter to this method
                    Toast.makeText(this, "Last 30 days: $startDate to $endDate", Toast.LENGTH_SHORT).show()
                }

                3 -> {
                    val (startDate, endDate) = calendarDialogBinding.customCalendarView.getLastXDays(60) //used to get last 60 days dates just pass 60 as parameter to this method
                    Toast.makeText(this, "Last 30 days: $startDate to $endDate", Toast.LENGTH_SHORT).show()
                }

                4 -> {
                    val (startDate, endDate) = calendarDialogBinding.customCalendarView.getLastXDays(90) //used to get last 90 days dates just pass 90 as parameter to this method
                    Toast.makeText(this, "Last 30 days: $startDate to $endDate", Toast.LENGTH_SHORT).show()
                }

                5 -> {
                    val (startDate, endDate) = calendarDialogBinding.customCalendarView.getLastXDays(180) //used to get last 180 days dates just pass 180 as parameter to this method
                    Toast.makeText(this, "Last 30 days: $startDate to $endDate", Toast.LENGTH_SHORT).show()
                }

                6 -> {
                    val (startDat, endDat) = calendarDialogBinding.customCalendarView.getSelectedDateRange() //used to get days dates selected by user start and end date of its choice custom dates
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
        dialog.window?.attributes?.windowAnimations = R.style.animated_popup //animation to animate dilaog can use other animation as you want

        dialog.show()
    }

```

Simply call this method to show dialog in your fragment or activity on button click or in oncreate of activity and onviewcreated of fragment.just add this method and call it anywhere you want and change drawables and backgrounds according to your choices.

---

## OverView:
* **getToday()**: This is use to get current date means today date.
* **getYesterday()**: This method in library is used to get yesterday date.
* **getLastXDays()**: This method in library is used to get date rangle between days just have to pas number of days as parameter in this method.
* **getSelectedDateRange()**: This method in library is used to get date rangle between start and end means custom date selection.



## Find this library useful? :heart:

However, if you get some profit from this or just want to encourage me to continue creating stuff, there are few ways you can do it. :coffee: :hamburger: :fries: :apple:

Support it by joining stargazers to this. ⭐

Also, [follow me on GitHub](https://github.com/SultanAyubi360) for my next creations! 🤩


