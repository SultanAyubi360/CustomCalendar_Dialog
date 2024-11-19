# CustomCalendar_Dialog
A highly customizable calendar library for Android.The CustomCalendarView provides an easy and customizable calendar to create a Calendar.To use the CustomCalendarView in your application, you first need to add the library to your application. You can do this either from Gradle, or Maven or by directly downloading the source code from GitHub

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
<br>
<br>

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


