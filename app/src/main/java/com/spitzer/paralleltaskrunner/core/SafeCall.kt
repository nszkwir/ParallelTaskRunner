package com.spitzer.paralleltaskrunner.core

import retrofit2.HttpException
import retrofit2.Response

internal suspend fun <T> safeCall(call: suspend () -> Response<T>): ResponseData<T?> {
    return try {
        val response = call.invoke()
        if (response.isSuccessful) {
            response.body()?.let { ResponseData.Success(response.body()) }
                ?: run { ResponseData.Error(Exception(response.message()), response.code()) }
        } else {
            ResponseData.Error(HttpException(response))
        }
    } catch (e: Exception) {
        ResponseData.Error(e)
    }
}