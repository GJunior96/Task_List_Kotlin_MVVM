package com.example.tasklist.feature_task.domain.util

sealed class TaskOrder(val orderType: OrderType) {
    class Description(orderType: OrderType): TaskOrder(orderType)
    class Hour(orderType: OrderType): TaskOrder(orderType)
    class State(orderType: OrderType): TaskOrder(orderType)

    fun copy(orderType: OrderType): TaskOrder {
        return  when(this) {
            is Description -> Description(orderType)
            is Hour -> Hour(orderType)
            is State -> State(orderType)
        }
    }
}
