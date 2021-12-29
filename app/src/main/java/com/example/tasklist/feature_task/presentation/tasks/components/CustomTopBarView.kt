package com.example.tasklist.feature_task.presentation.tasks.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tasklist.R
import com.example.tasklist.feature_task.presentation.util.Screen

@Composable
fun CustomTopBarView(navController: NavController, title: String) {
    val expanded = remember { mutableStateOf(false) }

    TopAppBar(
        backgroundColor = MaterialTheme.colors.background,
        elevation = 0.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(Modifier.wrapContentHeight()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Column(modifier = Modifier.wrapContentSize()) {
                    CompositionLocalProvider(
                        LocalContentAlpha provides ContentAlpha.high,
                    ) {
                        IconButton(
                            onClick = { navController.navigateUp() },
                            enabled = true
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_back_arrow),
                                tint = MaterialTheme.colors.onBackground,
                                contentDescription = stringResource(R.string.back_icon)
                            )
                        }
                    }
                }
                Column(Modifier.wrapContentSize()) {
                    ProvideTextStyle(value = MaterialTheme.typography.h1) {
                        CompositionLocalProvider(
                            LocalContentAlpha provides ContentAlpha.high,
                        ) {
                            Text(
                                modifier = Modifier,
                                textAlign = TextAlign.Center,
                                maxLines = 1,
                                color = MaterialTheme.colors.onBackground,
                                text = title
                            )
                        }
                    }
                }
                Box(modifier = Modifier.wrapContentSize(Alignment.TopEnd)) {
                    CompositionLocalProvider(
                        LocalContentAlpha provides ContentAlpha.high,
                    ) {
                        IconButton(
                            onClick = { expanded.value = true },
                        ) {
                            Icon(
                                imageVector = Icons.Filled.MoreVert,
                                tint = MaterialTheme.colors.onBackground,
                                contentDescription = stringResource(R.string.more_options)
                            )
                        }
                        MaterialTheme(shapes = MaterialTheme.shapes.copy(
                            medium = MaterialTheme.shapes.large
                        )) {
                            DropdownMenu(
                                expanded = expanded.value,
                                onDismissRequest = { expanded.value = false },
                                modifier = Modifier
                                    .width(185.dp)
                                    .background(MaterialTheme.colors.onSurface)
                            ) {
                                DropdownMenuItem(onClick = {
                                    expanded.value = false
                                }) {
                                    TextButton(onClick = {
                                        navController.navigate(Screen.SettingsScreen.route)
                                    }) {
                                        Text(
                                            modifier = Modifier.fillMaxWidth(),
                                            text = stringResource(R.string.settings),
                                            style = MaterialTheme.typography.h3,
                                            color = MaterialTheme.colors.surface,
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                }
                                Divider(
                                    modifier = Modifier.padding(horizontal = 30.dp),
                                    color = MaterialTheme.colors.surface,
                                    thickness = 0.6.dp
                                )
                                DropdownMenuItem(onClick = {
                                    expanded.value = false
                                }) {
                                    Text(
                                        modifier = Modifier.fillMaxWidth(),
                                        text = stringResource(R.string.help),
                                        style = MaterialTheme.typography.h3,
                                        color = MaterialTheme.colors.surface,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}