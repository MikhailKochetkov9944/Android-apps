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





//private const val BASE_URL = "https://randomuser.me"
//object RetrofitServices {
//    private val retrofit = Retrofit.Builder()
//        .baseUrl(BASE_URL)
//        .addConverterFactory(MoshiConverterFactory.create())
//        .build()
//    val searchImageApi: SearchImageApi = retrofit.create(
//        SearchImageApi::class.java
//    )
//
//}
//interface SearchImageApi {
//    @GET("/api")
//    fun getUserImageList(): List<User>
//}

//private const val BASE_USER_URL = "https://randomuser.me"
//
//interface SearchUserAPI {
//    @GET("/api/")
//    suspend fun getUser() : List<User>
//}
//
//val retrofit: SearchUserAPI = Retrofit.Builder()
//    .baseUrl(BASE_USER_URL)
//    .addConverterFactory(MoshiConverterFactory.create())
//    .build()
//    .create(SearchUserAPI::class.java)