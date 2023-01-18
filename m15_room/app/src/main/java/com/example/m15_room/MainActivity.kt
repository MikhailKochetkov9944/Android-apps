package com.example.m15_room

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.m15_room.databinding.ActivityMainBinding
import com.example.m15_room.ui.main.App
import com.example.m15_room.ui.main.MainFragment
import com.example.m15_room.ui.main.MainViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.map

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

//        val regex = "[a-zA-Z]*[-]*".toRegex()
//        binding.editText.addTextChangedListener(object: TextWatcher {
//            override fun beforeTextChanged(s: CharSequence?,start: Int,count: Int,after: Int) {}
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
//
//            override fun afterTextChanged(s: Editable?) {
//
//            }
//        }
//        )

        binding.addBtn.setOnClickListener {
            if(viewModel.allWords.value.isEmpty()) {
                viewModel.onAddBtn((binding.editText.text.toString()))
                Log.d("DTCR", "omCreate: empty")
            } else {
                for((index, value) in viewModel.allWords.value.withIndex()) {
                    if(viewModel.allWords.value.elementAt(index).word == binding.editText.text.toString()) {
                        viewModel.onUpdateBtn(viewModel.allWords.value.elementAt(index).word)
                    } else {
                        if (viewModel.allWords.value.indexOf(value) >= viewModel.allWords.value.size-1) {
                            viewModel.onAddBtn((binding.editText.text.toString()))
                            Log.d("DTCR", "currentIndex: ${viewModel.allWords.value.indexOf(value)}")
                            Log.d("DTCR", "currentSize: ${viewModel.allWords.value.size-1}")
                       }
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
                    binding.textView.text = words.map { "Word - ${it.word}, count- ${it.count}" }
                        .joinToString(
                        separator = "\r\n"
                    )
                    Log.d("DTCR", "onCreate: view ${viewModel.allWords.value}")
                }
        }

        }
    }
