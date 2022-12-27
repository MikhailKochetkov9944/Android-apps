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
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import coil.load
import com.bumptech.glide.Glide
import com.example.m14_retrofit.R
import com.example.m14_retrofit.databinding.FragmentMainBinding
import com.google.gson.Gson
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.concurrent.thread

class MainFragment : Fragment() {
    private val viewModel: MainViewModel by viewModels()
    var scope = CoroutineScope(Job() + Dispatchers.Main)
    var job: Job? = null
    private lateinit var binding: FragmentMainBinding

    companion object {
        fun newInstance() = MainFragment()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageView = requireActivity().findViewById<ImageView>(R.id.imageView)
        //val textView = requireActivity().findViewById<TextView>(R.id.textFirstName)
        val button = requireActivity().findViewById<Button>(R.id.button)

        fun viewData() {
            Glide.with(imageView.context)
                .load(viewModel.user?.results!![0].picture.thumbnail)
                .into(imageView)
            binding.textFirstName.text = "First Name: ${viewModel.user?.results!![0].name.first}"
            binding.textLastName.text = "Last Name: ${viewModel.user?.results!![0].name.last}"
            binding.textEmail.text = "Email: ${viewModel.user?.results!![0].email}"
        }

        viewModel.getUserData()
        job = scope.launch {
            delay(3000)
            viewData()
        }

        button.setOnClickListener {
            viewModel.getUserData()
            job?.cancel()
            job = scope.launch {
                viewData()
            }
        }
    }

}