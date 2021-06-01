package com.spitzer.paralleltaskrunner.taskrunner.repositories

import com.spitzer.paralleltaskrunner.taskrunner.data.DataOne
import com.spitzer.paralleltaskrunner.taskrunner.services.TasksServices
import com.spitzer.paralleltaskrunner.core.ResponseData
import com.spitzer.paralleltaskrunner.core.safeCall
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TaskOneRepository(
    private val service: TasksServices,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ITaskOneRepository {
    override suspend fun getDataOne(): ResponseData<DataOne?> {
        return withContext(ioDispatcher) {
            return@withContext safeCall {
                service.getDataOne()
            }
        }
    }

    override suspend fun getStringOne(): ResponseData<String?> {
        return withContext(ioDispatcher) {
            return@withContext safeCall {
                service.getStringOne()
            }
        }
    }
}