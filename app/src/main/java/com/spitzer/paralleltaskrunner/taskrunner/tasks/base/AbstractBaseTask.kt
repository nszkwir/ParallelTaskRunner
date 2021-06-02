package com.spitzer.paralleltaskrunner.taskrunner.tasks.base

import com.spitzer.paralleltaskrunner.taskrunner.utils.TaskState

abstract class AbstractBaseTask: Task {
    internal var state: TaskState = TaskState.TODO
    override fun setState(state: TaskState) {
        this.state = state
    }

    override fun getState() = state
}
