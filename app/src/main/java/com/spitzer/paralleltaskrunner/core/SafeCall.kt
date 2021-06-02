package com.spitzer.paralleltaskrunner.core

import retrofit2.HttpException
import retrofit2.Response

internal suspend fun <T> safeCall(call: suspend () -> Response<T>): ResultData<T?> {
    return try {
        val response = call.invoke()
        if (response.isSuccessful) {
            response.body()?.let { ResultData.Success(response.body()) }
                ?: run { ResultData.Error(Exception(response.message()), response.code()) }
        } else {
            ResultData.Error(HttpException(response))
        }
    } catch (e: Exception) {
        ResultData.Error(e)
    }
}

internal suspend fun <T> localSafeCall(call: suspend () -> T): ResultData<T?> {
    return try {
        val data = call.invoke()
        data?.let { ResultData.Success(data) }
            ?: run {  ResultData.Error(Exception("Null data"), -999)}
    } catch (e: Exception) {
        ResultData.Error(e)
    }
}