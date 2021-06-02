package com.spitzer.paralleltaskrunner.taskrunner.utils

import com.spitzer.paralleltaskrunner.core.ApiClient
import com.spitzer.paralleltaskrunner.taskrunner.repositories.TaskOneRepository
import com.spitzer.paralleltaskrunner.taskrunner.repositories.TaskTwoRepository
import com.spitzer.paralleltaskrunner.taskrunner.services.CatsFactsService
import com.spitzer.paralleltaskrunner.taskrunner.services.DogsFactsService
import com.spitzer.paralleltaskrunner.taskrunner.tasks.TaskOne
import com.spitzer.paralleltaskrunner.taskrunner.tasks.TaskTwo
import com.spitzer.paralleltaskrunner.taskrunner.tasks.base.Task

// This class should not exist
// In a well defined environment, tasks my be injected in some way
object TaskHelper {

    fun getCatTasks(): ArrayList<Task> {
        val arrayOfTasks = arrayListOf<Task>()
        val taskOne = TaskOne.createTaskOne()
        arrayOfTasks.add(taskOne)
        return arrayOfTasks
    }

    fun getDogTasks(): ArrayList<Task> {
        val arrayOfTasks = arrayListOf<Task>()
        val taskTwo = TaskTwo.createTaskTwo()
        arrayOfTasks.add(taskTwo)
        return arrayOfTasks
    }

    fun getCatDogTasks(): ArrayList<Task> {
        val arrayOfTasks = arrayListOf<Task>()
        val taskOne = TaskOne.createTaskOne()
        val taskTwo = TaskTwo.createTaskTwo()
        arrayOfTasks.add(taskOne)
        arrayOfTasks.add(taskTwo)
        return arrayOfTasks
    }
}