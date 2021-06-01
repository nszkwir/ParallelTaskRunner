package com.spitzer.paralleltaskrunner.taskrunner.tasks

import com.spitzer.paralleltaskrunner.taskrunner.data.DataOne
import com.spitzer.paralleltaskrunner.taskrunner.repositories.TaskOneRepository
import com.spitzer.paralleltaskrunner.taskrunner.tasks.base.AbstractBaseTask
import com.spitzer.paralleltaskrunner.core.ResponseData
import com.spitzer.paralleltaskrunner.taskrunner.utils.TaskState
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class TaskOne(
    private val repository: TaskOneRepository
) : AbstractBaseTask() {

    lateinit var deferredDataOne: Deferred<ResponseData<DataOne?>>

    override suspend fun run() {
        coroutineScope {
            deferredDataOne = async { repository.getDataOne() }
        }
    }

    override suspend fun awaitAndSave() {
        try {
            val dataOne = deferredDataOne.await()
            if (dataOne is ResponseData.Success && dataOne.data != null) {
                // We save/process data
                state = TaskState.DONE
            } else {
                state = TaskState.UNCOMPLETE
            }

        } catch (e: Exception) {
            state = TaskState.UNCOMPLETE
        }
    }
}