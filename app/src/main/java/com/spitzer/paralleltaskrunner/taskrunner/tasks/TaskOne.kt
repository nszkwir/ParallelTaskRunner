package com.spitzer.paralleltaskrunner.taskrunner.tasks

import android.util.Log
import com.spitzer.paralleltaskrunner.core.ApiClient
import com.spitzer.paralleltaskrunner.core.ResultData
import com.spitzer.paralleltaskrunner.core.runThenAwaitAndSave
import com.spitzer.paralleltaskrunner.taskrunner.data.CatFact
import com.spitzer.paralleltaskrunner.taskrunner.repositories.TaskOneRepository
import com.spitzer.paralleltaskrunner.taskrunner.services.CatsFactsService
import com.spitzer.paralleltaskrunner.taskrunner.tasks.base.AbstractBaseTask
import com.spitzer.paralleltaskrunner.taskrunner.tasks.base.Task
import com.spitzer.paralleltaskrunner.taskrunner.utils.TaskState
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

class TaskOne(
    private val repository: TaskOneRepository
) : AbstractBaseTask(), Task {

    lateinit var catResult: Deferred<ResultData<CatFact?>>
    lateinit var stringNumberResult: Deferred<ResultData<String?>>

    override suspend fun runAwaitAndSave() {
        var catState = TaskState.TODO
        var stringNumberState = TaskState.TODO
        coroutineScope {

            catState = runThenAwaitAndSave(
                { repository.getCatFact() },
                { catFact: ResultData<CatFact?> -> saveCatFact(catFact) }
            )

            stringNumberState = runThenAwaitAndSave(
                { repository.getRandomStringNumber() },
                { stringNumber: ResultData<String?> -> saveStringNumber(stringNumber) }
            )

        }

        val overallState =
            if (catState == TaskState.DONE && stringNumberState == TaskState.DONE)
                TaskState.DONE
            else
                TaskState.UNCOMPLETE

        Log.i("TASK", "TaskOne catState $catState")
        Log.i("TASK", "TaskOne stringNumberState $stringNumberState")
        Log.i("TASK", "TaskOne overallState $overallState")

        setState(overallState)
    }

    override suspend fun run() {
        coroutineScope {
            catResult = async { repository.getCatFact() }
            stringNumberResult = async { repository.getRandomStringNumber() }
        }
    }

    override suspend fun awaitAndSave() {

        val catState = saveCatFact(catResult.await())
        val stringNumberState = saveStringNumber(stringNumberResult.await())

        val overallState =
            if (catState == TaskState.DONE && stringNumberState == TaskState.DONE)
                TaskState.DONE
            else
                TaskState.UNCOMPLETE

        Log.i("TASK", "TaskOne catState $catState")
        Log.i("TASK", "TaskOne stringNumberState $stringNumberState")
        Log.i("TASK", "TaskOne overallState $overallState")

        setState(overallState)
    }

    private suspend fun saveCatFact(catFact: ResultData<CatFact?>): TaskState {
        return coroutineScope {
            if (catFact is ResultData.Success && catFact.data != null) {
                saveCatData()
                TaskState.DONE
            } else {
                TaskState.UNCOMPLETE
            }
        }
    }

    private suspend fun saveStringNumber(stringNumber: ResultData<String?>): TaskState {
        return coroutineScope {
            if (stringNumber is ResultData.Success && stringNumber.data != null) {
                saveStringNumberData()
                TaskState.DONE
            } else {
                TaskState.UNCOMPLETE
            }
        }
    }

    private fun saveCatData() = Unit
    private fun saveStringNumberData() = Unit

    companion object {
        fun createTaskOne(): TaskOne {
            val repository = TaskOneRepository(
                ApiClient("https://cat-fact.herokuapp.com/").createService(CatsFactsService::class.java)
            )
            Log.i("TASK", "TaskOne createTaskOne OK")
            return TaskOne(repository)
        }
    }
}