package com.spitzer.paralleltaskrunner.taskrunner.repositories

import com.spitzer.paralleltaskrunner.taskrunner.data.CatFact
import com.spitzer.paralleltaskrunner.core.ResultData

interface ITaskOneRepository {
    suspend fun getCatFact(): ResultData<CatFact?>
    suspend fun getRandomStringNumber(): ResultData<String?>
}
