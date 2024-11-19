# CustomCalendar_Dialog
A highly customizable calendar library for Android.The CustomCalendarView provides an easy and customizable calendar to create a Calendar.To use the CustomCalendarView in your application, you first need to add the library to your application.

<div align="center">
	
[![](https://jitpack.io/v/SultanAyubi360/CustomCalendar_Dialog.svg)](https://jitpack.io/#SultanAyubi360/CustomCalendar_Dialog/1.0)
![Status](https://img.shields.io/badge/Status-Active-brightgreen)  ![Kotlin](https://img.shields.io/badge/Kotlin-100%25-brightgreen)

</div>

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

Reference the View in Kotlin code.

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



## OverView:
* **getToday()**: This is use to get current date means today date.
* **getYesterday()**: This method in library is used to get yesterday date.
* **getLastXDays()**: This method in library is used to get date rangle between days just have to pas number of days as parameter in this method.
* **getSelectedDateRange()**: This method in library is used to get date rangle between start and end means custom date selection.



## Find this library useful? :heart:

However, if you get some profit from this or just want to encourage me to continue creating stuff, there are few ways you can do it. :coffee: :hamburger: :fries: :apple:

Support it by joining stargazers to this. ⭐

Also, [follow me on GitHub](https://github.com/SultanAyubi360) for my next creations! 🤩


