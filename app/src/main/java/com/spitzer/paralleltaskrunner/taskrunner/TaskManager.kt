package com.spitzer.paralleltaskrunner.taskrunner

import com.spitzer.paralleltaskrunner.taskrunner.utils.TaskHelper

class TaskManager {
    private var runner: TaskRunner = TaskRunner()

    fun runCatTasks(listener: TaskRunnerListener) {
        listener.let {
            runner.setListener(listener)
            runner.setTasks(TaskHelper.getCatTasks())
            runner.runTasks()
        }
    }

    fun runDogTasks(listener: TaskRunnerListener) {
        listener.let {
            runner.setListener(listener)
            runner.setTasks(TaskHelper.getDogTasks())
            runner.runTasks()
        }
    }

    fun runCatDogTasks(listener: TaskRunnerListener) {
        listener.let {
            runner.setListener(listener)
            runner.setTasks(TaskHelper.getCatDogTasks())
            runner.runTasks()
        }
    }

}