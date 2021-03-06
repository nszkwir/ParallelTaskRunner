package com.spitzer.paralleltaskrunner.taskrunner.repositories

import com.spitzer.paralleltaskrunner.core.ResultData
import com.spitzer.paralleltaskrunner.core.localSafeCall
import com.spitzer.paralleltaskrunner.core.safeCall
import com.spitzer.paralleltaskrunner.taskrunner.data.DogFact
import com.spitzer.paralleltaskrunner.taskrunner.services.DogsFactsService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlin.random.Random

class TaskTwoRepository(
    private val service: DogsFactsService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ITaskTwoRepository {
    override suspend fun getDogFacts(amount: Int): ResultData<ArrayList<DogFact>?> {
        return withContext(ioDispatcher) {
            return@withContext safeCall("TaskTwo run getDogFacts") {
                service.getDogFacts(amount)
            }
        }
    }

    override suspend fun getRandomNumber(delayInMillis: Long): ResultData<Int?> {
        return withContext(ioDispatcher) {
            delay(delayInMillis)
            return@withContext localSafeCall("TaskTwo run getRandomNumber") {
                Random.nextInt(0, 299)
            }
        }
    }
}