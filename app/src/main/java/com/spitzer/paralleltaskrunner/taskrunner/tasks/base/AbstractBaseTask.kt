package com.spitzer.paralleltaskrunner.taskrunner.tasks.base

import com.spitzer.paralleltaskrunner.taskrunner.utils.TaskState
import kotlinx.coroutines.Deferred

abstract class AbstractBaseTask {
    var state: TaskState = TaskState.TODO

    abstract suspend fun run()
    abstract suspend fun awaitAndSave()
}
