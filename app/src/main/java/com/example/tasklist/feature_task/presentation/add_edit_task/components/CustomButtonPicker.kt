package com.example.tasklist.feature_task.presentation.add_edit_task.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun CustomButtomPicker(
    text: String,
    label: String = "",
    textStyle: TextStyle = TextStyle(),
    enabled: Boolean,
    isNewTask: Boolean = true,
    onClick: () -> Unit
){
    Column(modifier = Modifier.padding(8.dp)){
        if (isNewTask) {
            Text(
                text = label,
                style = MaterialTheme.typography.h3,
                color = MaterialTheme.colors.onBackground
            )
        }
        TextButton(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = if (isNewTask)
                        MaterialTheme.colors.onBackground else MaterialTheme.colors.background,
                    shape = MaterialTheme.shapes.large
                )
                .background(
                    color = if (isNewTask)
                        Color.Transparent else MaterialTheme.colors.primary,
                    shape = MaterialTheme.shapes.large
                )
                .fillMaxWidth(),
            shape = MaterialTheme.shapes.large,
            enabled = enabled,
            onClick = onClick
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = text,
                    color = MaterialTheme.colors.background,
                    style = textStyle
                )
                Icon(
                    modifier = Modifier.align(alignment = CenterVertically),
                    imageVector = Icons.Filled.ArrowDropDown,
                    tint = if (isNewTask)
                        MaterialTheme.colors.primary else MaterialTheme.colors.background,
                    contentDescription = "Dropdown Arrow"
                )
            }
        }
    }
}