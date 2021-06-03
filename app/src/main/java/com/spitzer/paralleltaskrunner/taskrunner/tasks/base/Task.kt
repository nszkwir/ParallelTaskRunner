package com.spitzer.paralleltaskrunner.taskrunner.tasks.base

import com.spitzer.paralleltaskrunner.taskrunner.utils.TaskState

interface Task {
    suspend fun run()
    fun getState(): TaskState
    fun setState(state: TaskState)
}
