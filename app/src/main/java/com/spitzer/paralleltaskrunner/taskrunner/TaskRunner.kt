package com.spitzer.paralleltaskrunner.taskrunner

import android.util.Log
import com.spitzer.paralleltaskrunner.taskrunner.tasks.base.Task
import com.spitzer.paralleltaskrunner.taskrunner.utils.TaskState
import kotlinx.coroutines.*

class TaskRunner(
    private val coroutineScope: CoroutineScope = CoroutineScope(Job() + Dispatchers.Default)
) {
    private lateinit var listener: TaskRunnerListener
    private lateinit var tasksToDo: ArrayList<Task>

    init {
        tasksToDo = arrayListOf()
    }

    fun runTasks() {
        executeTasks(tasksToDo)
    }

    fun setListener(pListener: TaskRunnerListener) {
        listener = pListener
    }

    fun setTasks(pTasks: ArrayList<Task>) {
        tasksToDo = pTasks
    }

    private fun executeTasks(tasks: ArrayList<Task>) {
        Log.i("TASK", "TaskRunner executeTasks")
        coroutineScope.launch {
            try {
                supervisorScope {
                    tasks.forEach { it.run() }
                    tasks.forEach { it.awaitAndSave() }
                    withContext(Dispatchers.Main) {
                        val uncompleteTasks = getUncompleteTasks(tasks)
                        if (uncompleteTasks.isNotEmpty()) {
                            Log.i("TASK", "TaskRunner executeTasks UNCOMPLETE")
                            listener.uncompleteRunningTasks(uncompleteTasks)
                        } else {
                            Log.i("TASK", "TaskRunner executeTasks SUCCESS")
                            listener.successRunningTasks()
                        }
                    }
                }
            } catch (e: Exception) {
                Log.i("TaskRunner", "executeTasks ERROR", e)
                tasks.forEach { it.setState(TaskState.ERROR) }
                listener.errorRunningTasks()
            }
        }
    }

    private fun getUncompleteTasks(tasks: ArrayList<Task>) =
        tasks.filter { it.getState() != TaskState.DONE } as ArrayList<Task>
}