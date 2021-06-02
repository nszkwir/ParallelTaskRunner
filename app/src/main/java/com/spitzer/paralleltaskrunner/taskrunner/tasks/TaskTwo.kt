package com.spitzer.paralleltaskrunner.taskrunner.tasks

import android.util.Log
import com.spitzer.paralleltaskrunner.core.ApiClient
import com.spitzer.paralleltaskrunner.core.ResultData
import com.spitzer.paralleltaskrunner.taskrunner.data.DogFact
import com.spitzer.paralleltaskrunner.taskrunner.repositories.TaskTwoRepository
import com.spitzer.paralleltaskrunner.taskrunner.services.DogsFactsService
import com.spitzer.paralleltaskrunner.taskrunner.tasks.base.AbstractBaseTask
import com.spitzer.paralleltaskrunner.taskrunner.tasks.base.Task
import com.spitzer.paralleltaskrunner.taskrunner.utils.TaskState
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class TaskTwo(
    private val repository: TaskTwoRepository
) : AbstractBaseTask(), Task {

    lateinit var deferredDogFactList: Deferred<ResultData<ArrayList<DogFact>?>>
    lateinit var deferredNumber: Deferred<ResultData<Int?>>

    override suspend fun run() {
        coroutineScope {
            deferredDogFactList = async { repository.getDogFacts(2) }
            Log.i("TASK", "TaskTwo run getDogFacts")
            deferredNumber = async { repository.getRandomNumber() }
            Log.i("TASK", "TaskTwo run getRandomNumber")
        }
    }

    override suspend fun awaitAndSave() {
        state = try {
            val dogFactList = deferredDogFactList.await()
            val number = deferredNumber.await()
            if (dogFactList is ResultData.Success && dogFactList.data != null &&
                number is ResultData.Success && number.data != null
            ) {
                // We save/process data
                saveData()
                Log.i("TASK", "TaskTwo awaitAndSave OK")
                TaskState.DONE
            } else {
                Log.i("TASK", "TaskTwo awaitAndSave UNCOMPLETE")
                TaskState.UNCOMPLETE
            }

        } catch (e: Exception) {
            Log.i("TASK", "TaskTwo awaitAndSave ERROR", e)
            TaskState.ERROR
        }
    }

    private fun saveData() = Unit

    companion object {
        fun createTaskTwo(): TaskTwo {
            val repository = TaskTwoRepository(
                ApiClient("https://dog-facts-api.herokuapp.com/api/v1/resources/").createService(
                    DogsFactsService::class.java
                )
            )
            Log.i("TASK", "TaskTwo createTaskTwo OK")
            return TaskTwo(repository)
        }
    }
}