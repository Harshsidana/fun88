package com.fun88.testapplication.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Retrofit {
    companion object {

        private val retrofit by lazy {
            val interceptor = HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient
                .Builder().addInterceptor(interceptor)
                .build()

            Retrofit
                .Builder()
                .baseUrl(" https://howtodoandroid.com/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        val retrofitInstance by lazy {
            retrofit.create(Api::class.java)
        }


    }
}