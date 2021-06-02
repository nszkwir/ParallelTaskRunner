package com.spitzer.paralleltaskrunner.taskrunner.services

import com.spitzer.paralleltaskrunner.taskrunner.data.DogFact
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface DogsFactsService {
    @GET("dogs")
    //@Headers("Content-Type: application/json; charset=UTF-8")
    suspend fun getDogFacts(@Query("number") amount: Int): Response<ArrayList<DogFact>>
}