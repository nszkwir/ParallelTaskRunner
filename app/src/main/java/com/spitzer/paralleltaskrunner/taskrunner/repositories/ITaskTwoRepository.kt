package com.spitzer.paralleltaskrunner.taskrunner.repositories

import com.spitzer.paralleltaskrunner.core.ResultData
import com.spitzer.paralleltaskrunner.taskrunner.data.DogFact

interface ITaskTwoRepository {
    suspend fun getDogFacts(amount: Int): ResultData<ArrayList<DogFact>?>
    suspend fun getRandomNumber(delayInMillis: Long): ResultData<Int?>
}