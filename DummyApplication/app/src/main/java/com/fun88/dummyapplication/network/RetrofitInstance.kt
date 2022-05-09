package com.fun88.dummyapplication.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {

    companion object{

        private val retrofitLogin by lazy {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()
            Retrofit.Builder()
                .baseUrl("BASE_URL_2")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }


        val result by lazy {
            retrofitLogin.create(API::class.java)
        }
    }
}