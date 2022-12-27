package com.example.m14_retrofit.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    var user: User? = null
    fun getUserData() {
        val apiInterface = SearchImageApi.create().getUser()
        apiInterface.enqueue(object: Callback<User> {
            override fun onResponse(
                call: Call<User>?,
                response: Response<User>?
            ) {
                if(response?.body() != null) {
                    user = response?.body() ?: return
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e("RetrofitTest", "getUserImage failure", t)
            }
        })
    }
}