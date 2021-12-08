package com.example.tasklist.feature_task.presentation.tasks.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@ExperimentalComposeUiApi
@Composable
fun TaskOptionsMenu(onDismissRequest: () -> Unit, onClick: (String) -> Unit) {
    Dialog(onDismissRequest = { onDismissRequest() }, properties = DialogProperties(usePlatformDefaultWidth = false)) {
        //Box(modifier = Modifier.fillMaxSize()) {
            Surface(
                color = MaterialTheme.colors.background.copy(alpha = 0.57f),
                modifier = Modifier.fillMaxSize().clickable { onDismissRequest() }
            ) {
                Card(
                    modifier = Modifier
                        .wrapContentSize()
                        .clip(MaterialTheme.shapes.large)
                        .clickable {  },
                    shape = MaterialTheme.shapes.large,
                    backgroundColor = MaterialTheme.colors.primary
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp).wrapContentSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            modifier = Modifier.wrapContentSize(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            TextButton(
                                onClick = { onClick("edit") }
                            ) {
                                Text(
                                    text = "Edit Task",
                                    style = MaterialTheme.typography.body1,
                                    color = MaterialTheme.colors.surface
                                )
                            }
                        }
                        Divider(
                            modifier = Modifier
                                .width(200.dp)
                                .padding(horizontal = 16.dp),
                            color = MaterialTheme.colors.surface.copy(alpha = 0.7f),
                            thickness = 1.dp
                        )
                        Row(
                            modifier = Modifier.wrapContentSize(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            TextButton(
                                onClick = { onClick("delete") }
                            ) {
                                Text(
                                    text = "Delete Task",
                                    style = MaterialTheme.typography.body1,
                                    color = MaterialTheme.colors.surface
                                )
                            }
                        }
                        Divider(
                            modifier = Modifier
                                .width(200.dp)
                                .padding(horizontal = 16.dp),
                            color = MaterialTheme.colors.surface.copy(alpha = 0.7f),
                            thickness = 1.dp
                        )
                        Row(
                            modifier = Modifier.wrapContentSize(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            TextButton(
                                onClick = { onClick("done") },
                                //modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = "Mark as Done",
                                    style = MaterialTheme.typography.body1,
                                    color = MaterialTheme.colors.surface
                                )
                            }
                        }
                    }
                }
            }
        //}
    }
}