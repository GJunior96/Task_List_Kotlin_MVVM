package com.example.tasklist.feature_task.presentation.util

import java.text.SimpleDateFormat
import java.util.*

private val locale = Locale("pt","BR")

fun Date.dayAndMonth(): String {
    return SimpleDateFormat("dd/MM", locale).format(this)
}

fun Date.month(): String {
    return SimpleDateFormat("MMMM", locale).format(this)
}

fun Date.date(): String {
    return SimpleDateFormat("MMM dd yyyy", locale).format(this)
}