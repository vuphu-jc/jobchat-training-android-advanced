package com.example.advancedandroid

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics

object Utils {
    fun getDisplayMetrics(activity: Activity) : DisplayMetrics {
        val displayMetrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics
    }
}