package com.example.m11_data_storage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.m11_data_storage.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repository = Repository()
        binding.textView.text = repository.getText(this)

        binding.saveButton.setOnClickListener {
            val inputText = binding.editText.text
            repository.saveText(inputText.toString(), this)
            binding.textView.text = repository.getText(this)
        }

        binding.clearButton.setOnClickListener {
            repository.clearText()
            if(repository.clearText() == null) {
                binding.textView.text = " "
            }
            binding.editText.text.clear()
        }


    }
}