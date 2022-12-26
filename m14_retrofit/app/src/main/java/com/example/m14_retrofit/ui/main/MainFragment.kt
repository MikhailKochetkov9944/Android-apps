package com.example.m14_retrofit.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import coil.load
import com.bumptech.glide.Glide
import com.example.m14_retrofit.R
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.concurrent.thread

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageView = requireActivity().findViewById<ImageView>(R.id.imageView)
        val textView = requireActivity().findViewById<TextView>(R.id.text1)
        val button = requireActivity().findViewById<Button>(R.id.button)


        button.setOnClickListener {
            val apiInterface = SearchImageApi.create().getUser()
            apiInterface.enqueue(object: Callback<User> {
                override fun onResponse(
                    call: Call<User>?,
                    response: Response<User>?
                ) {
                    if(response?.body() != null) {
                        val user = response?.body() ?: return
                        textView.text = user.results.toString()
//                        Glide.with(imageView.context)
//                            .load(user)
//                            .into(imageView)
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })
        }



//        runBlocking {
//            launch(Dispatchers.IO) {
//                val requestAPI = retrofit.getUser()
//                //println(requestAPI)
//                textView.text = requestAPI.toString()
//        val imageAdapter = Gson().getAdapter(User::class.java)
//        val imageModel: User = imageAdapter.fromJson(requestAPI)
//        textView.text = imageModel.toString()
//        val deserializedString = imageAdapter.toJson(imageModel)
//        textView.text = deserializedString

//            }
//        }
//        thread {
//            val response = RetrofitServices.searchImageApi.getUserImageList().execute()
//            imageView.load(response.body()?.first()?.url)
//        }

//        RetrofitServices.searchImageApi.getUserImageList()
//            .enqueue(object: Callback<List<UserImageModel>> {
//                override fun onResponse(
//                    call: Call<List<UserImageModel>>,
//                    response: Response<List<UserImageModel>>
//                ) {
//                    if(response.isSuccessful) {
//                        val user = response.body()?.first() ?: return
//                        imageView.load(user.url)
//                    }
//                }
//                override fun onFailure(call: Call<List<UserImageModel>>, throwable: Throwable) {
//                    Log.e("RetrofitTest", "getUserImage failure", throwable)
//                }
//            })
    }

}