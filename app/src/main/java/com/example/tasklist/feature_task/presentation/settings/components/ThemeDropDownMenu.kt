package com.example.tasklist.feature_task.presentation.settings.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ThemeDropDownMenu(
    list: List<String>,
    onDismissRequest: () -> Unit,
    selectedItem: (String) -> Unit
) {
    MaterialTheme(shapes = MaterialTheme.shapes.copy(medium = MaterialTheme.shapes.large)) {
        DropdownMenu(
            expanded = true,
            onDismissRequest = { onDismissRequest() },
            modifier = Modifier
                .width(185.dp)
                .background(MaterialTheme.colors.onSurface)
        ) {
            list.forEachIndexed { index, element ->
                DropdownMenuItem(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        selectedItem(element)
                        onDismissRequest()
                    }
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = CenterHorizontally
                    ) {
                        Text(
                            text = element,
                            color = MaterialTheme.colors.onPrimary,
                            style = MaterialTheme.typography.h3
                        )
                    }
                }
                if (index != list.lastIndex) {
                    Divider(
                        modifier = Modifier.padding(horizontal = 30.dp),
                        color = MaterialTheme.colors.surface,
                        thickness = 0.6.dp
                    )
                }
            }
        }
    }
}