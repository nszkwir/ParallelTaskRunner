package com.spitzer.paralleltaskrunner.core

import java.io.IOException
import java.util.concurrent.TimeoutException

sealed class ResultData<out T> {
    data class Success<out T>(val data: T) : ResultData<T>()
    data class Error(val failure: Exception, val code: Int = DEFAULT_ERROR_CODE) :
        ResultData<Nothing>()

    fun isNetworkError(): Boolean {
        return this is Error && (failure is IOException || failure is TimeoutException)
    }

    fun getErrorCode(): Int {
        return if (this is Error) code else -1
    }

    companion object {
        const val DEFAULT_ERROR_CODE = 400
    }
}