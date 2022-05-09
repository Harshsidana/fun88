package com.fun88.testapplication.network

import com.fun88.testapplication.model.PicsResponse
import retrofit2.Response
import retrofit2.http.GET

interface Api {
    @GET("movielist.json")
    suspend fun getValues(): Response<PicsResponse>
}