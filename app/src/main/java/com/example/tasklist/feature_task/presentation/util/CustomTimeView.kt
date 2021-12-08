package com.example.tasklist.feature_task.presentation.util

import android.view.ContextThemeWrapper
import android.widget.TimePicker
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.example.tasklist.R

@Composable
fun CustomTimeView(onTimeSelected: (String) -> Unit) {
    AndroidView(
        modifier = Modifier.wrapContentSize(),
        factory = { context ->
            TimePicker(ContextThemeWrapper(context, R.style.CalendarViewCustom))
        },
        update = { view ->
            view.setOnTimeChangedListener { _, hour, minute ->
                onTimeSelected(
                    "$hour:$minute"
                )
            }
        }
    )
}