package com.fun88.dummyapplication.repository

import com.fun88.dummyapplication.network.RetrofitInstance

class AppRepository {

    suspend fun getData() = RetrofitInstance.result.getValues()

}