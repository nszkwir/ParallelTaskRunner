package com.spitzer.paralleltaskrunner.taskrunner.tasks

import android.util.Log
import com.spitzer.paralleltaskrunner.core.ApiClient
import com.spitzer.paralleltaskrunner.core.ResultData
import com.spitzer.paralleltaskrunner.core.runThenAwaitAndSave
import com.spitzer.paralleltaskrunner.taskrunner.data.CatFact
import com.spitzer.paralleltaskrunner.taskrunner.data.DogFact
import com.spitzer.paralleltaskrunner.taskrunner.repositories.TaskTwoRepository
import com.spitzer.paralleltaskrunner.taskrunner.services.DogsFactsService
import com.spitzer.paralleltaskrunner.taskrunner.tasks.base.AbstractBaseTask
import com.spitzer.paralleltaskrunner.taskrunner.tasks.base.Task
import com.spitzer.paralleltaskrunner.taskrunner.utils.TaskState
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlin.random.Random

class TaskTwo(
    private val repository: TaskTwoRepository
) : AbstractBaseTask(), Task {

    lateinit var dogResult: Deferred<ResultData<ArrayList<DogFact>?>>
    lateinit var secondDogResult: Deferred<ResultData<ArrayList<DogFact>?>>
    lateinit var numberResult: Deferred<ResultData<Int?>>
    lateinit var secondNumberResult: Deferred<ResultData<Int?>>


    override suspend fun runAwaitAndSave() {
        var dogState = TaskState.TODO
        var secondDogState = TaskState.TODO
        var numberState = TaskState.TODO
        var secondNumberState = TaskState.TODO
        coroutineScope {

            dogState = runThenAwaitAndSave(
                { repository.getDogFacts(Random.nextInt(1, 3)) },
                { fact: ResultData<ArrayList<DogFact>?> -> saveDogFact(fact) }
            )

            secondDogState = runThenAwaitAndSave(
                { repository.getDogFacts(Random.nextInt(1, 3)) },
                { fact: ResultData<ArrayList<DogFact>?> -> saveSecondDogFact(fact) }
            )

            numberState = runThenAwaitAndSave(
                { repository.getRandomNumber(2000L) },
                { number: ResultData<Int?> -> saveNumber(number) }
            )

            secondNumberState = runThenAwaitAndSave(
                { repository.getRandomNumber(5L) },
                { number: ResultData<Int?> -> saveSecondNumber(number) }
            )

        }

        val overallState =
            if (dogState == TaskState.DONE && numberState == TaskState.DONE &&
                secondDogState == TaskState.DONE && secondNumberState == TaskState.DONE)
                TaskState.DONE
            else
                TaskState.UNCOMPLETE

        Log.i("TASK", "TaskTwo dogState $dogState")
        Log.i("TASK", "TaskTwo numberState $numberState")
        Log.i("TASK", "TaskTwo secondDogState $secondDogState")
        Log.i("TASK", "TaskTwo secondNumberState $secondNumberState")
        Log.i("TASK", "TaskTwo overallState $overallState")

        setState(overallState)
    }

    override suspend fun run() {
        coroutineScope {
            dogResult = async { repository.getDogFacts(Random.nextInt(1, 3)) }
            secondDogResult = async { repository.getDogFacts(Random.nextInt(1, 3)) }
            numberResult = async { repository.getRandomNumber(2000L) }
            secondNumberResult = async { repository.getRandomNumber(5L) }
        }
    }

    override suspend fun awaitAndSave() {
        val dogState = saveDogFact(dogResult.await())
        val secondDogState = saveSecondDogFact(secondDogResult.await())
        val numberState = saveNumber(numberResult.await())
        val secondNumberState = saveSecondNumber(secondNumberResult.await())

        val overallState =
            if (dogState == TaskState.DONE && numberState == TaskState.DONE &&
                secondDogState == TaskState.DONE && secondNumberState == TaskState.DONE)
                TaskState.DONE
            else
                TaskState.UNCOMPLETE

        Log.i("TASK", "TaskTwo dogState $dogState")
        Log.i("TASK", "TaskTwo numberState $numberState")
        Log.i("TASK", "TaskTwo secondDogState $secondDogState")
        Log.i("TASK", "TaskTwo secondNumberState $secondNumberState")
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

    private suspend fun saveSecondDogFact(dogFact: ResultData<ArrayList<DogFact>?>): TaskState {
        return coroutineScope {
            if (dogFact is ResultData.Success && dogFact.data != null) {
                saveDogData()
                TaskState.DONE
            } else {
                TaskState.UNCOMPLETE
            }
        }
    }

    private suspend fun saveSecondNumber(number: ResultData<Int?>): TaskState {
        return coroutineScope {
            if (number is ResultData.Success && number.data != null) {
                saveNumberData()
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