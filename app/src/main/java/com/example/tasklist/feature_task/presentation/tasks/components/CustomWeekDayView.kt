package com.example.tasklist.feature_task.presentation.tasks.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.tasklist.feature_task.presentation.tasks.WeekDay
import com.example.tasklist.feature_task.presentation.tasks.normalizedItemPosition
import kotlin.math.absoluteValue

@Composable
fun CustomWeekDayView(
    weekDay: WeekDay,
    modifier: Modifier = Modifier,
    boxModifier: Modifier = Modifier,
    emptyBoxModifier: Modifier = Modifier,
    pointer: MutableState<Int>)
{
    val clicked = remember { pointer }
    if (weekDay.day > 0) {
        Card(
            shape = MaterialTheme.shapes.large,
            modifier = modifier
                .clip(MaterialTheme.shapes.large)
                .size(85.dp)
                .background(MaterialTheme.colors.background)
                //.clickable { weekDay.clicked = true },
        ) {
            Box(
                modifier = boxModifier
                    .clip(MaterialTheme.shapes.large)
                    .border(
                        1.dp,
                        MaterialTheme.colors.onBackground,
                        MaterialTheme.shapes.large
                    )
                    .background(
                        if (clicked.value != weekDay.day) MaterialTheme.colors.background
                        else MaterialTheme.colors.onBackground
                    )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Column {
                        Text(
                            text = weekDay.weekDay,
                            style = MaterialTheme.typography.h2,
                            color = if (clicked.value != weekDay.day) MaterialTheme.colors.onBackground
                                    else MaterialTheme.colors.background,
                            textAlign = TextAlign.Center
                        )
                    }
                    Column {
                        Text(
                            text = weekDay.day.toString(),
                            style = MaterialTheme.typography.h3,
                            color = if (clicked.value != weekDay.day) MaterialTheme.colors.onBackground
                                    else MaterialTheme.colors.background,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    } else {
        Box(modifier = emptyBoxModifier) { }
    }
}