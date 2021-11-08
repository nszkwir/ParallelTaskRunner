package com.spitzer.paralleltaskrunner.taskrunner.repositories

import com.spitzer.paralleltaskrunner.core.ResultData
import com.spitzer.paralleltaskrunner.core.localSafeCall
import com.spitzer.paralleltaskrunner.core.safeCall
import com.spitzer.paralleltaskrunner.taskrunner.data.CatFact
import com.spitzer.paralleltaskrunner.taskrunner.services.CatsFactsService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlin.random.Random

class TaskOneRepository(
    private val service: CatsFactsService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ITaskOneRepository {
    override suspend fun getCatFact(): ResultData<CatFact?> {
        return withContext(ioDispatcher) {
            delay(5000)
            return@withContext safeCall("TaskOne run getCatFact") {
                service.getCatFact()
            }
        }
    }

    override suspend fun getRandomStringNumber(): ResultData<String?> {
        return withContext(ioDispatcher) {
            return@withContext localSafeCall("TaskOne run getRandomStringNumber") {
                Random.nextInt(999, 9999).toString()
            }
        }
    }
}