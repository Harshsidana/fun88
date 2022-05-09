package com.fun88.dummyapplication.network

import com.hadi.retrofitmvvm.model.PicsResponse
import retrofit2.Response
import retrofit2.http.GET

interface API {

    @GET("")
    suspend fun getValues(): Response<PicsResponse>
}