package com.example.tasklist.feature_task.domain.util

sealed class TaskOrder(val orderType: OrderType) {
    class Description(orderType: OrderType): TaskOrder(orderType)
    class Hour(orderType: OrderType): TaskOrder(orderType)
    class State(orderType: OrderType): TaskOrder(orderType)
}
