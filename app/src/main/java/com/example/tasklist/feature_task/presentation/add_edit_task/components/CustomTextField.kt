package com.example.tasklist.feature_task.presentation.add_edit_task.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.TextFieldDefaults.textFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun CustomTextField(
    text: String,
    hint: String,
    label: String = "",
    onValueChange: (String) -> Unit,
    textStyle: TextStyle = TextStyle(),
    singleLine: Boolean = true,
    isNewTask: Boolean = true,
    onFocusChange: (FocusState) -> Unit
) {
    val input = remember { mutableStateOf(text) }
    Column(modifier = Modifier.padding(8.dp)) {
        if (isNewTask) {
            Text(
                text = label,
                style = MaterialTheme.typography.h3,
                color = MaterialTheme.colors.onBackground
            )
        }

        TextField(
            value = input.value,
            onValueChange = {
                input.value = it
                onValueChange(it)
            },
            colors = textFieldColors(
                backgroundColor = Color.Transparent,
                cursorColor = if (isNewTask)
                    MaterialTheme.colors.primary else MaterialTheme.colors.background,
                textColor = if (isNewTask)
                    MaterialTheme.colors.primary else MaterialTheme.colors.background,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            singleLine = singleLine,
            textStyle = textStyle,
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color.Transparent,
                    shape = MaterialTheme.shapes.large
                )
                .border(
                    width = 1.dp,
                    color = if (isNewTask)
                        MaterialTheme.colors.primary else MaterialTheme.colors.background,
                    shape = MaterialTheme.shapes.large
                )
                .onFocusChanged { onFocusChange(it) },
            shape = MaterialTheme.shapes.large,
            placeholder = {
                Text(
                    text = if (isNewTask) hint else "",
                    color = MaterialTheme.colors.secondary,
                    style = MaterialTheme.typography.h3
                )
            }
        )
    }
}