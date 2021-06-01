package com.spitzer.paralleltaskrunner.taskrunner.repositories

import com.spitzer.paralleltaskrunner.taskrunner.data.DataOne
import com.spitzer.paralleltaskrunner.core.ResponseData

interface ITaskOneRepository {
    suspend fun getDataOne(): ResponseData<DataOne?>
    suspend fun getStringOne(): ResponseData<String?>
}