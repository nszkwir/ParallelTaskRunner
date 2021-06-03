package com.spitzer.paralleltaskrunner.core

import com.spitzer.paralleltaskrunner.taskrunner.utils.TaskState
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

suspend fun <T> runThenAwaitAndSave(
    runAsync: suspend () -> T,
    awaitAndSave: suspend (T) -> TaskState
): TaskState {
    return try {
        coroutineScope {
            val result = async { runAsync.invoke() }
            awaitAndSave(result.await())
        }
    } catch (e: Exception) {
        TaskState.EXCEPTION
    }
}
