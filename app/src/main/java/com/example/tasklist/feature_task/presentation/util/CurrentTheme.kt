package com.example.tasklist.feature_task.presentation.util

import com.example.tasklist.R

fun currentTheme(value: String?): Int {
    return when(value) {
        "purple" -> { R.style.CustomPickerViewPurple }
        "blue" -> { R.style.CustomPickerViewBlue }
        "black" -> { R.style.CustomPickerViewBlack }
        "white" -> { R.style.CustomPickerViewWhite }
        else -> { R.style.CustomPickerViewPurple }
    }
}