package com.example.m15_room

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.m15_room.databinding.ActivityMainBinding
import com.example.m15_room.ui.main.App
import com.example.m15_room.ui.main.MainFragment
import com.example.m15_room.ui.main.MainViewModel
import kotlinx.coroutines.flow.collect

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels{
        object: ViewModelProvider.Factory {
            override fun <T: ViewModel> create(modelClass: Class<T>): T {
                val wordDao = (application as App).db.wordDao()
                return MainViewModel(wordDao) as T
            }
        }
    }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate( savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.addBtn.setOnClickListener {
            if(viewModel.allWords.value.isEmpty()) {
                viewModel.onAddBtn((binding.editText.text.toString()))
                Log.d("DTCR", "omCreate: empty")
            } else {
                for(i in viewModel.allWords.value.indices) {
                    if(viewModel.allWords.value.elementAt(i).word == binding.editText.text.toString()) {
                        viewModel.onUpdateBtn(viewModel.allWords.value.elementAt(i).word)
                    } else {
                        viewModel.onAddBtn((binding.editText.text.toString()))
                    }
                }
            }
        }
        binding.deleteBtn.setOnClickListener {
            viewModel.onDeleteBtn()
        }

        lifecycleScope.launchWhenCreated {
            viewModel.allWords
                .collect { words ->
                    binding.textView.text = words
                        .joinToString(
                        separator = "\r\n"
                    )
                    Log.d("DTCR", "onCreate: view ${viewModel.allWords.value}")
                }
        }




//        binding.addBtn.setOnClickListener { viewModel.onAddBtn() }
//        binding.updateBtn.setOnClickListener { viewModel.onUpdateBtn() }
//        binding.deleteBtn.setOnClickListener { viewModel.onDeleteBtn() }
//
//        //val userDao = (application as App).db.userDao()
//        lifecycleScope.launchWhenCreated {
//            viewModel.allUsers
//                .collect { users ->
//                    binding.textView.text = users.joinToString(
//                        separator = "\r\n"
//                    )
//                }
//        }

        }
    }
