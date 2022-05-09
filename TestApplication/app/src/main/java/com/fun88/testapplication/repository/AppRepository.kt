package com.fun88.testapplication.repository

import com.fun88.testapplication.network.Retrofit

class AppRepository {
    suspend fun getValues() = Retrofit.retrofitInstance.getValues()
}