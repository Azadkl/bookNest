package com.example.booknest.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val client = OkHttpClient.Builder()
    .connectTimeout(30, TimeUnit.SECONDS)  // Bağlantı zaman aşımı
    .readTimeout(30, TimeUnit.SECONDS)     // Okuma zaman aşımı
    .writeTimeout(30, TimeUnit.SECONDS)    // Yazma zaman aşımı
    .build()
val retrofit = Retrofit.Builder()
    .baseUrl("http://10.0.2.2:8080/")  // HTTPS yerine HTTP kullanmak
    .client(client)
    .addConverterFactory(GsonConverterFactory.create())
    .build()


val api = retrofit.create(BookNestApi::class.java)
val apiSignUp = retrofit.create(SignUpApi::class.java)


