package com.example.tasklist.feature_task.presentation.tasks.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.tasklist.feature_task.domain.model.Task
import com.example.tasklist.feature_task.presentation.ui.theme.TaskListAppTheme

@Composable
fun TaskItem(
    task: Task,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 0.dp)
            .clickable { onClick() },
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (task.state == "done") {
            Text(
                text = task.content,
                textDecoration = TextDecoration.LineThrough,
                color = MaterialTheme.colors.secondary
            )
            Text(
                text = task.hour,
                textDecoration = TextDecoration.LineThrough,
                color = MaterialTheme.colors.secondary
            )
        } else {
            Text(
                text = task.content,
            )
            Text(
                text = task.hour,
            )
        }
    }
    Divider(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 0.dp)
            .fillMaxWidth(),
        color = if (task.state == "todo")
            MaterialTheme.colors.onBackground else MaterialTheme.colors.secondary,
        thickness = 1.dp
    )
    Spacer(modifier = Modifier.height(TaskListAppTheme.paddings.defaultPadding))
}