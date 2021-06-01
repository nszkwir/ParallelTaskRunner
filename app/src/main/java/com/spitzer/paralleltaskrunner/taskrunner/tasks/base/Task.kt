package com.spitzer.paralleltaskrunner.taskrunner.tasks.base

import com.spitzer.paralleltaskrunner.taskrunner.utils.TaskState

interface Task {
    suspend fun run()
    suspend fun awaitAndSave()
    fun getState(): TaskState
    fun setState(state: TaskState)
}
