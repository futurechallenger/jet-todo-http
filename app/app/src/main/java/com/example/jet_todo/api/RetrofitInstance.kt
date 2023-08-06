package com.example.jet_todo.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "http://192.168.31.182:8000"

    // TODO: Logging only when in debug
    private val loggingInterceptor = HttpLoggingInterceptor()
    private val httpClient = OkHttpClient().newBuilder().addInterceptor(loggingInterceptor).build()

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val todoApi: TodoApi by lazy {
        retrofit.create(TodoApi::class.java)
    }
}