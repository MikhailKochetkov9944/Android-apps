package com.example.m14_retrofit.ui.main

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


import retrofit2.http.GET

interface SearchImageApi {
    @GET("api/")
    fun getUser(): Call<User>
    companion object {
        val BASE_URL = "https://randomuser.me/"

        fun create(): SearchImageApi{
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(SearchImageApi::class.java)
        }
    }
}
