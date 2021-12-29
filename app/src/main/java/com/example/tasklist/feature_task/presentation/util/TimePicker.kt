package com.example.tasklist.feature_task.presentation.util

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.tasklist.feature_task.presentation.ui.theme.Shapes
import com.example.tasklist.feature_task.presentation.ui.theme.TaskListAppTheme
import com.example.tasklist.R

@Composable
fun TimePicker(onTimeSelected: (String) -> Unit, onDismissRequest: () -> Unit) {
    val selTime = remember { mutableStateOf("") }

    Dialog(onDismissRequest = { onDismissRequest() }, properties = DialogProperties()) {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .background(
                    color = MaterialTheme.colors.surface,
                    shape = Shapes.large
                )
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colors.primary,
                        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                    )
                    .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 0.dp)
            ) {
                Text(
                    text = stringResource(R.string.select_time),
                    style = MaterialTheme.typography.h4,
                    color = MaterialTheme.colors.onPrimary
                )
            }

            CustomTimeView(onTimeSelected = {
                selTime.value = it
            })

            Spacer(modifier = Modifier.size(TaskListAppTheme.paddings.smallPadding))

            Row(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(bottom = 16.dp, end = 16.dp)
            ) {
                TextButton(
                    onClick = onDismissRequest
                ) {
                    Text(
                        text = stringResource(R.string.cancel),
                        style = MaterialTheme.typography.button,
                        color = MaterialTheme.colors.onSurface
                    )
                }
                TextButton(
                    onClick = {
                        onTimeSelected(selTime.value)
                        onDismissRequest()
                    }
                ) {
                    Text(
                        text = stringResource(R.string.ok),
                        style = MaterialTheme.typography.button,
                        color = MaterialTheme.colors.onSurface
                    )
                }
            }
        }
    }
}