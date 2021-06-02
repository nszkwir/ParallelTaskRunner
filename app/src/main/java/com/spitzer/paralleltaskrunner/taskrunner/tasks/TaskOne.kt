package com.spitzer.paralleltaskrunner.taskrunner.tasks

import android.util.Log
import com.spitzer.paralleltaskrunner.core.ApiClient
import com.spitzer.paralleltaskrunner.core.ResultData
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

    lateinit var deferredCatFact: Deferred<ResultData<CatFact?>>
    lateinit var deferredStringNumber: Deferred<ResultData<String?>>

    override suspend fun run() {
        coroutineScope {
            deferredCatFact = async { repository.getCatFact() }
            Log.i("TASK", "TaskOne run getCatFact")
            deferredStringNumber = async { repository.getRandomStringNumber() }
            Log.i("TASK", "TaskOne run getRandomStringNumber")
        }
    }

    override suspend fun awaitAndSave() {
        state = try {
            val catFact = deferredCatFact.await()
            val stringNumber = deferredStringNumber.await()
            if (catFact is ResultData.Success && catFact.data != null &&
                stringNumber is ResultData.Success && stringNumber.data != null
            ) {
                // We save/process data
                saveData()
                Log.i("TASK", "TaskOne awaitAndSave OK")
                TaskState.DONE
            } else {
                Log.i("TASK", "TaskOne awaitAndSave UNCOMPLETE")
                TaskState.UNCOMPLETE
            }

        } catch (e: Exception) {
            Log.i("TASK", "TaskOne awaitAndSave ERROR", e)
            TaskState.ERROR
        }
    }

    private fun saveData() = Unit

    companion object {
        fun createTaskOne(): TaskOne {
            val repository = TaskOneRepository(
                ApiClient("https://cat-fact.herokuapp.com").createService(CatsFactsService::class.java)
            )
            Log.i("TASK", "TaskOne createTaskOne OK")
            return TaskOne(repository)
        }
    }
}