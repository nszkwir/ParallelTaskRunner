package com.spitzer.paralleltaskrunner.taskrunner.tasks

import android.util.Log
import com.spitzer.paralleltaskrunner.core.ApiClient
import com.spitzer.paralleltaskrunner.core.ResultData
import com.spitzer.paralleltaskrunner.core.runThenAwaitAndSave
import com.spitzer.paralleltaskrunner.taskrunner.data.DogFact
import com.spitzer.paralleltaskrunner.taskrunner.repositories.TaskTwoRepository
import com.spitzer.paralleltaskrunner.taskrunner.services.DogsFactsService
import com.spitzer.paralleltaskrunner.taskrunner.tasks.base.AbstractBaseTask
import com.spitzer.paralleltaskrunner.taskrunner.tasks.base.Task
import com.spitzer.paralleltaskrunner.taskrunner.utils.TaskState
import kotlinx.coroutines.coroutineScope
import kotlin.random.Random

class TaskTwo(
    private val repository: TaskTwoRepository
) : AbstractBaseTask(), Task {

    override suspend fun run() {
        var dogState = TaskState.TODO
        var numberState = TaskState.TODO
        coroutineScope {

            dogState = runThenAwaitAndSave(
                { repository.getDogFacts(Random.nextInt(1, 3)) },
                { catFact: ResultData<ArrayList<DogFact>?> -> saveDogFact(catFact) }
            )

            numberState = runThenAwaitAndSave(
                { repository.getRandomNumber() },
                { number: ResultData<Int?> -> saveNumber(number) }
            )

        }

        val overallState =
            if (dogState == TaskState.DONE && numberState == TaskState.DONE)
                TaskState.DONE
            else
                TaskState.UNCOMPLETE

        Log.i("TASK", "TaskTwo dogState $dogState")
        Log.i("TASK", "TaskTwo numberState $numberState")
        Log.i("TASK", "TaskTwo overallState $overallState")

        setState(overallState)
    }

    private suspend fun saveDogFact(dogFact: ResultData<ArrayList<DogFact>?>): TaskState {
        return coroutineScope {
            if (dogFact is ResultData.Success && dogFact.data != null) {
                saveDogData()
                TaskState.DONE
            } else {
                TaskState.UNCOMPLETE
            }
        }
    }

    private suspend fun saveNumber(number: ResultData<Int?>): TaskState {
        return coroutineScope {
            if (number is ResultData.Success && number.data != null) {
                saveNumberData()
                TaskState.DONE
            } else {
                TaskState.UNCOMPLETE
            }
        }
    }

    private fun saveDogData() = Unit
    private fun saveNumberData() = Unit

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