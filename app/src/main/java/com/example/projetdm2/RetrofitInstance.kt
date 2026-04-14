package com.example.projetdm2

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    val gson = GsonBuilder()
        .registerTypeAdapter(Character::class.java, Deserializer())
        .create()

    private
    const val BASE_URL = "https://api.sampleapis.com/avatar/"
    val api: ApiService by lazy{
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        retrofit.create(ApiService::class.java)
    }
}