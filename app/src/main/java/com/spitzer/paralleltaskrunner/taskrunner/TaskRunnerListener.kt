package com.spitzer.paralleltaskrunner.taskrunner

import com.spitzer.paralleltaskrunner.taskrunner.tasks.base.Task

interface TaskRunnerListener {
    fun errorRunningTasks()
    fun uncompleteRunningTasks(uncompleteTasks: ArrayList<Task>)
    fun successRunningTasks()
}
