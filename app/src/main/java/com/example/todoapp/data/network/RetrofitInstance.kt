package com.example.todoapp.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    // Change BASE_URL to your backend server URL, e.g., "http://192.168.1.5:3000/"
    private const val BASE_URL = "http://10.0.2.2:3000/" // '10.0.2.2' is localhost for emulator

    val api: TodoApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TodoApiService::class.java)
    }
}
