package com.example.projetdm2

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("characters")
    suspend fun getCharacters(): List<Character>

//    @GET("character/{id}")
//    suspend fun getOneCharacter(
//        @Path("id") id: String
//    ): Response<Character>
}