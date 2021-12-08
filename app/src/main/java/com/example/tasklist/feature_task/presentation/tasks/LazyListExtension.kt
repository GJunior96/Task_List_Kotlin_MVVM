package com.example.tasklist.feature_task.presentation.tasks

import androidx.compose.foundation.lazy.LazyListLayoutInfo

fun LazyListLayoutInfo.normalizedItemPosition(key: Any): Float =
    visibleItemsInfo
        .firstOrNull { it.key == key }
        ?.let {
            val center = (viewportEndOffset + viewportStartOffset - it.size) / 2F
            (it.offset.toFloat() - center) / center
        }
        ?: 0F

fun LazyListLayoutInfo.setItemClicked(key: WeekDay): Boolean {
    return !key.clicked.value
}
