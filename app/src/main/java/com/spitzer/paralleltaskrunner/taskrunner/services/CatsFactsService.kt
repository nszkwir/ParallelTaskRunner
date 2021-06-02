package com.spitzer.paralleltaskrunner.taskrunner.services

import com.spitzer.paralleltaskrunner.taskrunner.data.CatFact
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface CatsFactsService {
    //not needed @Authenticated
    @GET("/facts/random")
    @Headers("Content-Type: application/json; charset=UTF-8")
    suspend fun getCatFact() : Response<CatFact>
}