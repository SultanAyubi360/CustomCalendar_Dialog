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


